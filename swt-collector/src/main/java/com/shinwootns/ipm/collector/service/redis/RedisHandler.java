package com.shinwootns.ipm.collector.service.redis;

import org.apache.log4j.Logger;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.cache.RedisManager;
import com.shinwootns.common.cache.RedisManager.RedisPoolStatus;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;

public class RedisHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	RedisManager rm = new RedisManager();
	
	// Singleton
	private static RedisHandler _instance = null;
	private RedisHandler() {}
	public static synchronized RedisHandler getInstance() {

		if (_instance == null) {
			_instance = new RedisHandler();
		}
		return _instance;
	}

	
	public boolean connect() 
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		boolean result = false;
		try
		{
			result = rm.connect(
					appProperty.redisHost, 
					appProperty.redisPort, 
					CryptoUtils.Decode_AES128(appProperty.redisPassword), 0);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
			result = false;
		}
		
		if ( result == false)
		{
			System.out.println("Redis connection failed.");
			return false;
		}

		return true;
	}
	
	public RedisClient getRedisClient() {
		
		RedisClient redis = rm.createRedisClient();
		
		if (redis == null) {
			return null;
		}
		else if (redis.isConnect() == false) {
			redis.close();
			return null;
		}
		
		return redis;
	}
	
	public RedisPoolStatus getPoolStatus() {
		return rm.getPoolStatus();
	}
}
