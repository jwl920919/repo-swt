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
	
	private String _host;
	private int _port, _timeout;
	
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

	public RedisManager() {
	}
	
	public boolean connect(String host, int port, String password, int dbnum) {
		
		int timeout = 3000;
		
		if (_config == null)
			_config = new JedisPoolConfig();
		
		if (_redisPool == null)
		{
			if (password != null && password.isEmpty())
				password = null;
			
			_redisPool = new JedisPool(_config, host, port, timeout, password, dbnum);

			try {
			
				Jedis redis = _redisPool.getResource();
				
				String value = redis.ping();
				
				redis.close();
				
				if (value.toUpperCase().equals("PONG"))
					return true;
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		
			
			return false;
		}
		
		return false;
	}
	
	public void close()
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
	
	public RedisClient createRedisClient() {
		
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
		
		return null;
	}
	
	public RedisPoolStatus getPoolStatus() {
		
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
		
		return null;
	}
}
