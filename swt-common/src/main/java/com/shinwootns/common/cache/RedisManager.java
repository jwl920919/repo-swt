package com.shinwootns.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisManager {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private JedisPoolConfig _config = null;
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

	public RedisManager(String host, int port, String password, int dbnum) {
		
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
	
	//region [FUNC] connect
	public boolean connect() {
		
		synchronized(this)
		{
			if (_config == null)
				_config = new JedisPoolConfig();
			
			if (_redisPool == null)
			{
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
				
				return false;
			}
		}
		
		return false;
	}
	//endregion
	
	//region [FUNC] close
	public void close()
	{
		synchronized(this) 
		{
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
	}
	//endregion
	
	//region [FUNC] reconnect
	public boolean reconnect() {
		
		_logger.info("Try reconnect()...");
		
		close();
		
		if (connect()) {
			_logger.info("reconnect()... OK");
			return true;
		}
		else {
			_logger.info("reconnect()... failed");
			return false;
		}
	}
	//endregion
	
	//region [FUNC] createRedisClient
	public RedisClient createRedisClient() {
		
		synchronized(this) 
		{
			try
			{
				Jedis redis = _redisPool.getResource();
				
				if (redis == null)
					return null;
				
				return new RedisClient(redis); 
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
	
	//region [FUNC] getPoolStatus
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
