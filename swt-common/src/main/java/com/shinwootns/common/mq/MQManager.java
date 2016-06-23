package com.shinwootns.common.mq;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.shinwootns.common.utils.LogUtils;

public class MQManager {

	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private String _host = "127.0.0.1";
	private int _port = 5672; // default : 5672
	private String _userName = "";
	private String _password = "";
	private String _virtualHost = "/";
	private int _handshakeTimeout = 10;

	private ConnectionFactory _factory = null;
	private Connection _connection = null;
	private Channel _channel = null;
	
	public enum MQClientType
	{
		Single, WorkQueue, Publish, Routing, Topics, Custom, RPC
	}
	
	//region Connect
	public boolean Connect(String host, int port, String userName, String password, String virtualHost)
	{
		this._host = host;
		this._port = port;
		this._userName = userName;
		this._password = password;
		this._virtualHost = virtualHost;
		
		Close();
		
		if (_factory == null)
		{
			try
			{
				// Factory
				_factory = new ConnectionFactory();
				_factory.setHost(this._host);
				_factory.setPort(this._port);
				_factory.setUsername(this._userName);
				_factory.setPassword(this._password);
				_factory.setVirtualHost(this._virtualHost);
				
				_factory.setHandshakeTimeout(10000);

				// Connection
				_connection = _factory.newConnection();
				
				if (_connection == null)
				{
					Close();
					return false;
				}
				
				return true;
			}
			catch(Exception ex)
			{
				LogUtils.WriteLog(_logger, Level.ERROR, ex);
			}
		}
		
		return false;
	}
	
	// 
	// URI = "amqp://username:password@hostName:portNumber/virtualHost"
	//
    // ex) amqp://userid:password@192.168.1.10:5672/
	// 
	public boolean Connect(String uri)
	{
		Close();
		
		if (_factory == null)
		{
			try
			{
				_factory = new ConnectionFactory();
				_factory.setUri(uri);
				
				_factory.setHandshakeTimeout(10000);
				
				// Connection
				_connection = _factory.newConnection();
				
				if (_connection == null)
				{
					Close();
					return false;
				}
				
				return true;
			}
			catch(Exception ex)
			{
				LogUtils.WriteLog(_logger, Level.ERROR, ex);
			}
			
		}
		
		return false;
	}
	
	//endregion
	
	//region Create MQClient
	public BaseClient createMQClient(MQClientType clientType)
	{
		if (_factory == null || _connection == null)
			return null;
		
		try
		{
			// Channel
			_channel = _connection.createChannel();
			
			if (_channel == null)
				return null;
			
			// Single
			if (clientType == MQClientType.Single)
				return new SingleClient(_channel);
			// WorkQueue
			else if (clientType == MQClientType.WorkQueue)
				return new WorkQueueClient(_channel);
			// Publish
			else if (clientType == MQClientType.Publish)
				return new PublishClient(_channel);
			// Routing
			else if (clientType == MQClientType.Routing)
				return new RoutingClient(_channel);
			// Topics
			else if (clientType == MQClientType.Topics)
				return new TopicsClient(_channel);
			else
			// Default
				return new CustomClient(_channel);
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
			
		return null;
	}
	//endregion 

	//region Close
	public void Close() {
		
		try
		{
			if (this._channel != null && this._channel.isOpen())
				this._channel.close();
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		finally {
			this._channel = null;
		}
		
		try
		{
			if (this._connection != null && this._connection.isOpen())
				this._connection.close();
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		finally {
			this._connection = null;
		}
	}
	//endregion
}
