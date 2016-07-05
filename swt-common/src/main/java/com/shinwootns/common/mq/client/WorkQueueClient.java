package com.shinwootns.common.mq.client;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.shinwootns.common.utils.LogUtils;

public class WorkQueueClient extends BaseClient {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	//region Constructor
	public WorkQueueClient(Channel channel)
	{
		super(channel);
		
		super.setPrefetchCount(1);
	}
	//endregion
	
	// WorkQueue Mode (RoundRobin)
    //==========================================================
    //   큐에서 순서대로 메시지를 읽어서 C1, C2, 순서대로 벌갈아 수행. (1:n)
    //   C1과 C2가 다른 메시지를 받지만, 하는 일은 같음.
    //
    //                   [P] ---------[Q1]-- [C1]
    //                            |
    //                            +---[Q2]-- [C2]
    //

	//region DeclareQueue
	public boolean DeclareQueue_WorkQueueMode(String queueName)
	{
		boolean durable = true;	// queue survive broker restart
		boolean exclusive = false;	// used by only one connection and the queue will be deleted when that connection closes
		boolean autoDelete = false;	// queue is deleted when all queues have finished using it
		
		try
		{
			DeclareOk result = _channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
			
			return true;
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region Send Data
	public boolean SendData(String queueName, byte[] bytes)
	{
		if (_channel == null)
			return false;
		
		try
		{
			_channel.basicPublish("", queueName, null, bytes);
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region Receive Data
	public ArrayList<String> ReceiveData(String queueName, boolean autoAck, int timeout_ms, int popCount, String charset )
	{
		if (_channel == null)
			return null;
		
		if (_consumer == null)
			_consumer = new QueueingConsumer(_channel);
		
		ArrayList<String> listData = new ArrayList<String>();
		
		try
		{
			_channel.basicConsume(queueName, autoAck, _consumer);
			
			long startTime = System.currentTimeMillis();
			long estimatedTime = startTime;
			
			Delivery delivery;
			
			while(true)
			{
				// Get Data
				delivery = _consumer.nextDelivery(timeout_ms);
				
				if (delivery != null)
				{
					// To String
					String message = new String(delivery.getBody(), charset);
					
					// Add to list 
					listData.add(message);
					
					if (autoAck == false)
					{
						// Send Ack
						boolean multiple = false;
						_channel.basicAck(delivery.getEnvelope().getDeliveryTag(), multiple);
					}
				}
				else
				{
					Thread.sleep(100);
				}
				
				estimatedTime = System.currentTimeMillis() - startTime;
				
				// Check Pop Count
				if (popCount > 0 && listData.size() >= popCount)
					break;
				// Check Timeout
				else if (timeout_ms < estimatedTime)
					break;
			}
			
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return listData;
	}
	//endregion
}
