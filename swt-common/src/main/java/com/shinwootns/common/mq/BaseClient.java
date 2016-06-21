package com.shinwootns.common.mq;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.shinwootns.common.utils.LogUtils;

abstract class BaseClient {
	
	protected Logger _logger = null;
	protected Channel _channel = null;
	QueueingConsumer _consumer = null;

	//region Constructor
	public BaseClient(Logger logger, Channel channel) {
		this._logger = logger;
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
				LogUtils.WriteLog(_logger, Level.ERROR, ex);
			}
		}
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
