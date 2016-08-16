package com.shinwootns.ipm.insight.service.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.data.key.QueueNames;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.config.ApplicationProperty;

public class RabbitMQHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private MQManager manager = null;;
	
	private WorkQueueClient _client = null;
	
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
					
					_logger.info((new StringBuilder())
							.append("Succeed connect rabbitmq... amqp:\\").append(appProperty.rabbitmqHost).append(":").append(appProperty.rabbitmqPort)
							.toString());
					
					return true;
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
	
	//region createClient
	private WorkQueueClient createClient() {
		
		synchronized(this)
		{
			if (manager == null)
				return null;
			
			// Create Client
			return (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
		}
	}
	//endregion
	
	//region SendEvent
	public boolean SendEvent(byte[] bytes) {
		
		if (bytes == null)
			return false;
		
		if (this._client == null) {
			this._client = createClient();
			
			if (this._client == null)
				return false;
			
			// Delcare Queue
			this._client.DeclareQueue_WorkQueueMode(QueueNames.EVENT_QUEUE_NAME);
		}
		
		synchronized(this)
		{
			if ( this._client == null )
				return false;
			
			try {

				return this._client.SendData(QueueNames.EVENT_QUEUE_NAME, bytes);
				
			} catch (IOException e) {
				_logger.error(e.getMessage(), e);
				
				if (this._client != null && this._client.checkConnection() == false) {
					this._client.CloseChannel();
					this._client = null;
				}
					
			}
		}
		
		return false;
	}
	//endregion
}