package com.shinwootns.common.mq;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Exchange;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.shinwootns.common.utils.LogUtils;

public class RoutingClient extends BaseClient {
	
	private String _queueName = null;

	//region Constructor
	public RoutingClient(Logger logger, Channel channel)
	{
		super(logger, channel);
	}
	//endregion

	// Routing Mode
    //==========================================================
    //   X(direct)에 의해 Routing Key가 일치하는 큐에 데이터를 전달.
    //   큐 마다 여러 개의 Key를 지정할 수 있으며, Key가 일치하는 큐에 동일 데이터가 들어감.
	//   주의) Topic하고는 다르게 Key가 정확하게 일치해야만 들어감.
    //
    //                        (type=direct)
    //                   [P] ------[X]----[Q1]--- [C1]
    //                              |  error
    //                              +-----[Q2]--- [C2]
	//                              |  info
	//                              +-----[Q2]--- [C2]
	//                               error,info,warning
	
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
			Exchange.DeclareOk result = _channel.exchangeDeclare(exchangeName, "direct", durable);
			
			return true;
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return false;
	}
	//endregion
	
	//region DeclareQueue - Routing Mode
	public boolean DeclareQueue_RoutingMode(String exchangeName, String[] routingKeys)
	{
		try
		{
			// Declare Exchange
			Exchange.DeclareOk exchangeRusult = _channel.exchangeDeclare(exchangeName, "direct");
			
			// Declare Queue
			this._queueName = _channel.queueDeclare().getQueue();
			
			// Bind Exchange & Queue
			for(String rountingKey : routingKeys)
				_channel.queueBind(_queueName, exchangeName, rountingKey);
			
			return true;
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
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
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
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
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return listData;
	}
	//endregion

}
