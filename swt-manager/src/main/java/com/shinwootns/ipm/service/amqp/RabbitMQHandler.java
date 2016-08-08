package com.shinwootns.ipm.service.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;

public class RabbitMQHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private MQManager manager = null;;
	
	private Thread receiver = null;
	
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
	public boolean connect() throws Exception
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		close();

		synchronized(this)
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
				
				startReceiveEvent();
				
				return true;
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
			stopReceiveEvent();
			
			if (manager != null) {
				manager.Close();
				manager = null;
			}
		}
	}
	//endregion
	
	//region [FUNC] Create Client
	public WorkQueueClient createClient() {
		
		if (manager == null)
			return null;
		
		return (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
	}
	//endregion

	//region [FUNC] Start Receive Event
	private boolean startReceiveEvent() {

		stopReceiveEvent();
		
		if (receiver == null) {
			receiver = new Thread(new MQEventReceiver(this));
			receiver.start();
			return true;
		}
		
		return true;
	}
	//endregion
	
	//region [FUNC] Stop Receive Event
	private void stopReceiveEvent() {
		
		if (receiver != null) {
			try {
				receiver.interrupt();
				receiver.join();
			} catch(Exception ex) {
			} finally {
				receiver = null;
			}
		}
	}
	//endregion
}
