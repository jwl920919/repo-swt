package com.shinwootns.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisHandler {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private JedisPoolConfig _config = new JedisPoolConfig();
	private JedisPool _redisPool = null;
	
	private final int _timeout = 3000;
	private String _host = null, _password = null;
	private int _port = 0, _dbnum = 0;;
	
	public class RedisPoolStatus {
		public int activeCount=0;
		public int idleCount=0;
		public int waitCount=0;
		
		@Override
		public String toString() {
			return (new StringBuilder())
					.append("ActiveCount=").append(activeCount)
					.append(", IdleCount=").append(idleCount)
					.append(", WaitCount=").append(waitCount)
					.toString();
		}
	}

	//region [public] Constructor
	public RedisHandler(String host, int port, String password, int dbnum) {
		
		synchronized(this)
		{
			this._host = host;
			this._port = port;
			this._dbnum = dbnum;
			
			if (password != null && password.isEmpty())
				password = null;
			else
				this._password = password;
		}
	}
	//endregion
	
	//region [public] connect
	public boolean connect() {
		
		synchronized(this)
		{
			if (_connect()) {
				_logger.info("Redis Connect()... OK");
				return true;
			}
		}
		
		_logger.info("Redis Connect()... Failed");
		return false;
	}
	//endregion
	
	//region [public] close
	public void close()
	{
		synchronized(this)
		{
			_close();
		}
	}
	//endregion
	
	//region [public] Reconnect
	public boolean reconnect() {

		_logger.info("Redis try reconnect()...");
		
		synchronized(this)
		{
			_close();
			
			if (_connect()) {
				_logger.info("Redis reconnect()... OK");
				return true;
			}
		}
		
		_logger.info("Redis reconnect()... Failed");
		
		return false;
	}
	//endregion
	
	//region [private] connect / close
	private boolean _connect() {

		_close();
		
		_redisPool = new JedisPool(_config, this._host, this._port, this._timeout, this._password, this._dbnum);

		Jedis redis = null;
		try {

			redis = _redisPool.getResource();
			
			String value = redis.ping();
			
			if (value.toUpperCase().equals("PONG"))
				return true;
			
		} catch(Exception ex) {
			
			_logger.error(ex.getMessage(), ex);
			
		} finally {
			if (redis != null)
				redis.close();
			redis = null;
		}
		
		_close();
		
		return false;
	}
	
	private void _close() {
		try
		{
			if (_redisPool != null)
				_redisPool.close();
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		finally {
			_redisPool = null;
		}
	}
	//endregion

	//region [public] createRedisClient
	public Jedis createRedisClient() {
		
		Jedis redis = null;
		
		synchronized(this) 
		{
			try
			{
				if ( _redisPool == null )
					_connect();
				
				if (_redisPool != null) {
					
					redis = _redisPool.getResource();
					
					if (redis != null && redis.ping() != null) {
						return redis;
					}
				}
			}
			catch(JedisConnectionException ex) {
				_logger.error(ex.getMessage(), ex);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
			
			if (redis != null)
				redis.close();
			
			// Reconnect
			_close();

			if (_connect())
				_logger.info("Redis reconnect()... OK");
			else
				_logger.info("Redis reconnect()... Failed");
		}
		
		return null;
	}
	//endregion
	
	//region [public] getPoolStatus
	public RedisPoolStatus getPoolStatus() {
		
		synchronized(this) 
		{
			try
			{
				RedisPoolStatus status = new RedisPoolStatus();
				status.activeCount = _redisPool.getNumActive();
				status.idleCount = _redisPool.getNumIdle();
				status.waitCount = _redisPool.getNumWaiters();
				
				return status;
			}
			catch(JedisConnectionException ex) {
				_logger.error(ex.getMessage(), ex);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	//endregion
}
