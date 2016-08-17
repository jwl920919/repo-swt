package com.shinwootns.ipm.insight.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.redis.RedisHandler;
import com.shinwootns.common.redis.RedisHandler.RedisPoolStatus;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.config.ApplicationProperty;

import redis.clients.jedis.Jedis;

public class RedisManager {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	//RedisManager 
	private RedisHandler rm = null;
	
	private String _host;
	private int _port;
	private String _passowrd;
	private int _dbnum;
	
	//region Singleton
	private static RedisManager _instance = null;
	private RedisManager() {}
	public static synchronized RedisManager getInstance() {

		if (_instance == null) {
			_instance = new RedisManager();
		}
		return _instance;
	}
	//endregion
	
	//region [public] connect
	public boolean connect() 
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		_host = appProperty.redisHost;
		_port =  appProperty.redisPort;
		_dbnum = 0;
		
		try {
			_passowrd = CryptoUtils.Decode_AES128(appProperty.redisPassword);
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			return false;
		}
		
		synchronized(this)
		{
			_close();
			_connect();
		}

		return false;
	}
	//endregion
	
	//region [public] close
	public void close() {
		synchronized(this)
		{
			_close();
		}
	}
	//endregion
	
	//region [private] _connect / _close
	private boolean _connect() {
		
		try
		{
			rm = new RedisHandler( _host, _port, _passowrd, 0);
			
			if ( rm.connect() ) {
				_logger.info("Redis connection... OK");
				return true;
			}
			else {
				_logger.info("Redis connection... Failed");
				return false;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		_close();
		
		return false;
	}
	
	private void _close() {
		try {
			if (rm != null)
				rm.close();
		} catch(Exception ex) {
		} finally {
			rm = null;
		}
	}
	//endregion
	
	//region [public] Get RedisClient
	public Jedis getRedisClient() {
		
		Jedis redis = null;
		
		synchronized(this)
		{
			if (rm == null)
				_connect();
			
			if (rm != null) {
				redis = rm.createRedisClient();
				
				if (redis == null) {
					return null;
				}
				else if (redis.isConnected() == false || redis.ping() == null) {
					
					redis.close();
					redis = null;
					
					rm.reconnect();
					
					redis = rm.createRedisClient();
					
					return redis;
				}
			}
		}
		
		return redis;
	}
	//endregion
	
	//region [public] Get PoolStatus
	public RedisPoolStatus getPoolStatus() {
		
		synchronized(this)
		{
			return rm.getPoolStatus();
		}
	}
	//endregion
}
