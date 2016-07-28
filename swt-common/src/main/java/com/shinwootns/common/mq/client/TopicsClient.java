package com.shinwootns.common.mq.client;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class TopicsClient extends BaseClient {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private String _queueName = null;
	
	public TopicsClient(Channel channel)
	{
		super(channel);
	}

	// Topics Mode
    //==========================================================
    //   X(topic)에 의해 Routing Key가 Pattern Matching되는 큐에 데이터를 전달.
    //  마침표(.)를 구분자로 사용하며, *는  exactly one word, #은 Zero or more words.
	//  참고로 Routing은 Complete Matching, Topics은 Pattern Matching 임.  
    //
    //                        (type=topic)
    //                   [P] ------[X]----[Q1]--- [C1]
    //                              |  *.orange.*
    //                              +-----[Q2]--- [C2]
	//                              |  *.*.rabbit
	//                              +-----[Q2]--- [C2]
	//                               lazy.#
	
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
			Exchange.DeclareOk result = _channel.exchangeDeclare(exchangeName, "topic", durable);
			
			return true;
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region DeclareQueue - Topics Mode
	public boolean DeclareQueue_TopicsMode(String exchangeName, String[] routingKeys)
	{
		try
		{
			// Declare Exchange
			Exchange.DeclareOk exchangeRusult = _channel.exchangeDeclare(exchangeName, "topic");
			
			// Declare Queue
			this._queueName = _channel.queueDeclare().getQueue();
			
			// Bind Exchange & Queue
			for(String rountingKey : routingKeys)
				_channel.queueBind(_queueName, exchangeName, rountingKey);
			
			return true;
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region Get QueueName
	public String getQueueName()
	{
		return this._queueName;
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
