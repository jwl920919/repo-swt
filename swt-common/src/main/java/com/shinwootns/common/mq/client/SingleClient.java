package com.shinwootns.common.mq.client;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class SingleClient extends BaseClient {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	//region Constructor
	public SingleClient(Channel channel)
	{
		super(channel);
	}
	//endregion
	
	// Single Mode
    //==========================================================
    //   하나의 P와 하나의 C가 연결 (1:1)
    //
    //                   [P] -----[Q]----- [C]
    //
	
	//region DecalreQueue - Single Mode
	public boolean DelcareQueue_SingleMode(String queueName)
	{
		boolean durable = false;	// queue survive broker restart
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
			
			return true;
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
