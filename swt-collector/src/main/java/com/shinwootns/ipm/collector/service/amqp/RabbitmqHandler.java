package com.shinwootns.ipm.collector.service.amqp;

import org.apache.log4j.Logger;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.BaseClient;
import com.shinwootns.common.mq.client.CustomClient;
import com.shinwootns.common.mq.client.PublishClient;
import com.shinwootns.common.mq.client.RoutingClient;
import com.shinwootns.common.mq.client.SingleClient;
import com.shinwootns.common.mq.client.TopicsClient;
import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;

public class RabbitmqHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private MQManager manager = new MQManager();
	
	// Singleton
	private static RabbitmqHandler _instance = null;
	private RabbitmqHandler() {}
	public static synchronized RabbitmqHandler getInstance() {

		if (_instance == null) {
			_instance = new RabbitmqHandler();
		}
		return _instance;
	}
	
	// Connect
	public boolean connect() throws Exception
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		boolean result =  manager.Connect(
				appProperty.rabbitmqHost, 
				appProperty.rabbitmqPort, 
				appProperty.rabbitmqUsername, 
				CryptoUtils.Decode_AES128(appProperty.rabbitmqPassword), 
				appProperty.rabbitmqVHost);
		
		if (result)
			_logger.info((new StringBuilder()).append("Succeed connect rabbitmq... amqp:\\").append(appProperty.rabbitmqHost).append(":").append(appProperty.rabbitmqPort));
		else
			_logger.info((new StringBuilder()).append("Failed connect rabbitmq... amqp:\\").append(appProperty.rabbitmqHost).append(":").append(appProperty.rabbitmqPort));
		
		return result;
	}
	
	// Close
	public void close()
	{
		manager.Close();
	}
	
	
	// Get Client
	public SingleClient createSingleClient()
	{
		return (SingleClient)manager.createMQClient(MQClientType.Single);
	}
	
	public WorkQueueClient createWorkQueueClient()
	{
		return (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
	}
	
	public PublishClient createPublishClient()
	{
		return (PublishClient)manager.createMQClient(MQClientType.Publish);
	}
	
	public TopicsClient createTopicsClient()
	{
		return (TopicsClient)manager.createMQClient(MQClientType.Topics);
	}
	
	public RoutingClient createRoutingClient()
	{
		return (RoutingClient)manager.createMQClient(MQClientType.Routing);
	}
	
	public CustomClient createCustomClient()
	{
		return (CustomClient)manager.createMQClient(MQClientType.Custom);
	}
}
