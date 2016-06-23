package com.shinwootns.common.mq;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.LogUtils;

abstract class BaseClient {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
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
