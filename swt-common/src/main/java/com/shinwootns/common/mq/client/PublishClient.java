package com.shinwootns.common.mq.client;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.shinwootns.common.utils.LogUtils;

public class PublishClient extends BaseClient {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private String _queueName = null;
	
	//region Constructor
	public PublishClient(Channel channel)
	{
		super(channel);
	}
	//endregion
	
	// Publish Mode
    //==========================================================
    //   X(Fanout)에 의해, 여러 큐에 모두 동일한 메시지가 삽입
    //   C1, C2가 모두 동일한 메시지를 받지만, 하는 일은 다름
    //
    //                        (type=fanout)
    //                   [P] ------[X]----[Q1]--- [C1]
    //                              |
    //                              +-----[Q2]--- [C2]
    //
	
	//region Declare Exchange
	public boolean DeclareExchange(String exchangeName, boolean durable)
	{
		if (_channel == null)
			return false;
		
		try
		{
			// durable : exchanges survive broker restart.
			// autoDelete : exchanges is deleted when all queues have finished using it.
			
			// Declare Exchange
			Exchange.DeclareOk result = _channel.exchangeDeclare(exchangeName, "fanout", durable);
			
			return true;
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region DeclareQueue - Publish Mode
	public boolean DeclareQueue_PublishMode(String exchangeName)
	{
		try
		{
			//super.DeclareExchange(exchangeName, "fanout", durable)
			
			// Declare Exchange
			Exchange.DeclareOk exchangeRusult = _channel.exchangeDeclare(exchangeName, "fanout");
			
			// Declare Queue
			this._queueName = _channel.queueDeclare().getQueue();
			
			// Bind Exchange & Queue
			_channel.queueBind(_queueName, exchangeName, "");
			
			return true;
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region get QueueName
	public String getQueueName()
	{
		return this._queueName;
	}
	//endregion
	
	//region Send Data 
	public boolean SendData(String exchangeName, byte[] bytes)
	{
		if (_channel == null)
			return false;
		
		try
		{
			_channel.basicPublish(exchangeName, "", null, bytes);
			
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
	public ArrayList<String> ReceiveData(boolean autoAck, int timeout_ms, int popCount, String charset )
	{
		if (_channel == null)
			return null;
		
		if (_consumer == null)
			_consumer = new QueueingConsumer(_channel);
		
		ArrayList<String> listData = new ArrayList<String>();
		
		try
		{
			_channel.basicConsume(this._queueName, autoAck, _consumer);
			
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
