package com.shinwootns.ipm.collector.service;

import org.apache.log4j.Logger;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.WorkQueueClient;

public class AmqpManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	final static String queueName = "ipm.syslog";

	private MQManager manager = new MQManager();
	
	// Singleton
	private static AmqpManager _instance = null;
	private AmqpManager() {}
	public static synchronized AmqpManager getInstance() {

		if (_instance == null) {
			_instance = new AmqpManager();
		}
		return _instance;
	}
	
	public boolean Connect() {
		
		try {
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				
				WorkQueueClient client = (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
				
				client.DeclareQueue_WorkQueueMode(queueName);
				
				return true;
				
				/*
				System.out.println("Connection OK.");
	
				WorkQueueClient client = (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
			
				String queueName = "TEST_WORKQUEUE";
				
				client.DeclareQueue_WorkQueueMode(queueName);
				
				while(this.isStopFlag() == false)
				{
					String data = TimeUtils.currentTimeString();
					
					System.out.println(String.format("<< Producer : %s", data));
					
					Thread.sleep(10);
					
					client.SendData(queueName, data.getBytes());
					
					Thread.sleep(1000);
				}
				
				client.CloseChannel();
				*/
			}

		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public void Close() {
		try
		{
			manager.Close();
		}
		catch(Exception ex) {
		}
	}
	
	public void sendToRabbitmq(String data) {

		//this.rabbitTemplate.convertAndSend("", "syslog", data);
	}
}
