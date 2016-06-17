package com.shinwootns.common.mq;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.shinwootns.common.utils.LogUtils;

public class CustomClient extends BaseClient {
	
	//region Constructor
	public CustomClient(Logger logger, Channel channel)
	{
		super(logger, channel);
	}
	//endregion
	
	//region Declare Exchange
	public boolean DeclareExchange(String exchangeName, String exchangeType, boolean durable)
	{
		if (_channel == null)
			return false;
		
		try
		{
			// durable : exchanges survive broker restart.
			// autoDelete : exchanges is deleted when all queues have finished using it.
			
			// Declare Exchange
			Exchange.DeclareOk result = _channel.exchangeDeclare(exchangeName, exchangeType, durable);
			
			return true;
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return false;
	}
	//endregion
	
	//region Declare Queue
	public boolean DeclareQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete)
	{
		if (_channel == null)
			return false;
		
		try
		{
			// durable : the queue survive broker restart.
			// exclusive : used by only one connection and the queue will be deleted when that connection closes.
			// autoDelete : queue is deleted when last consumer unsubscribes.
			
			DeclareOk result = _channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
			
			return true;
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return false;
	}
	//endregion
	
	//region Bind Queue
	public boolean BindQueue(String exchangeName, String queueName, String bindingKey)
	{
		if (_channel == null)
			return false;
		
		try
		{
			_channel.queueBind(queueName, exchangeName, bindingKey);
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return false;
	}
	//endregion
	
	//region Send Data
	public boolean SendData(String exchangeName, String routingKey, byte[] bytes)
	{
		if (_channel == null)
			return false;
		
		try
		{
			_channel.basicPublish(exchangeName, routingKey, null, bytes);
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
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
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return listData;
	}
	//endregion
}
