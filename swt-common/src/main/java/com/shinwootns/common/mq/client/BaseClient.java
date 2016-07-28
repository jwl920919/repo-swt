package com.shinwootns.common.mq.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public abstract class BaseClient {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	protected Channel _channel = null;
	QueueingConsumer _consumer = null;

	//region Constructor
	public BaseClient(Channel channel) {
		this._channel = channel;
	}
	//endregion
	
	//region Set prefetch count
	public void setPrefetchCount(int count)
	{
		if (this._channel != null)
		{
			try {
				
				this._channel.basicQos(count);
				
			} catch (IOException ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion
	
	//region Set prefetch count
	public boolean checkConnection()
	{
		if (this._channel != null)
		{
			try {
				
				return this._channel.isOpen();
				
			} catch (Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return false;
	}
	//endregion

	//region Close
	public void CloseChannel()
	{
		if (_consumer != null)
			_consumer = null;
		
		try
		{
			if (_channel != null)
				_channel.close();
		}
		catch(Exception ex) {}
		finally {
			_channel = null;
		}
	}
	//endregion
	
}
