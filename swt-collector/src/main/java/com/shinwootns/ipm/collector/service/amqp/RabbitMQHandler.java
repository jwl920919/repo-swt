package com.shinwootns.ipm.collector.service.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.data.key.QueueNames;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;

public class RabbitMQHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private MQManager manager = null;;
	
	private WorkQueueClient client = null;
	
	//region Singleton
	private static RabbitMQHandler _instance = null;
	private RabbitMQHandler() {}
	public static synchronized RabbitMQHandler getInstance() {

		if (_instance == null) {
			_instance = new RabbitMQHandler();
		}
		return _instance;
	}
	//endregion
	
	//region [FUNC] connect / close
	public boolean connect()
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		close();

		synchronized(this)
		{
			try
			{
				if (manager == null) {
					manager = new MQManager(appProperty.rabbitmqHost, 
							appProperty.rabbitmqPort, 
							appProperty.rabbitmqUsername, 
							CryptoUtils.Decode_AES128(appProperty.rabbitmqPassword), 
							appProperty.rabbitmqVHost,
							5000);
				}
			
				if ( manager.Connect() ) {
				
					// Create Client
					if (client == null)
						client = (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
						
					if (client != null) {
						
						// Delcare Queue
						client.DeclareQueue_WorkQueueMode(QueueNames.EVENT_QUEUE_NAME);
					
						// Check Connection
						if ( client.checkConnection()) {
						
							_logger.info((new StringBuilder())
									.append("Succeed connect rabbitmq... amqp:\\").append(appProperty.rabbitmqHost).append(":").append(appProperty.rabbitmqPort)
									.toString());
							
							return true;
						}
						else {
							client.CloseChannel();
							client = null;
						}
					}
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		_logger.info((new StringBuilder())
				.append("Failed connect rabbitmq... amqp:\\").append(appProperty.rabbitmqHost).append(":").append(appProperty.rabbitmqPort)
				.toString()
				);
		
		return false;
	}
	
	public void close()
	{
		synchronized(this)
		{
			if (manager != null) {
				manager.Close();
				manager = null;
			}
		}
	}
	//endregion
	
	//region SendEvent
	public boolean SendEvent(byte[] bytes) {
		
		if (client == null || bytes == null)
			return false;
		
		synchronized(this)
		{
			try {
				
				return client.SendData(QueueNames.EVENT_QUEUE_NAME, bytes);
				
			} catch (IOException e) {
				_logger.error(e.getMessage(), e);
				
				if (client.checkConnection() == false) {
					client.CloseChannel();
					client = null;
				}
					
			}
		}
		
		return false;
	}
	//endregion
}
