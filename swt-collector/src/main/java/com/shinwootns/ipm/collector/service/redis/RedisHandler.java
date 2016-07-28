package com.shinwootns.ipm.collector.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.cache.RedisManager;
import com.shinwootns.common.cache.RedisManager.RedisPoolStatus;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;

public class RedisHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	RedisManager rm = new RedisManager();
	
	//region Singleton
	private static RedisHandler _instance = null;
	private RedisHandler() {}
	public static synchronized RedisHandler getInstance() {

		if (_instance == null) {
			_instance = new RedisHandler();
		}
		return _instance;
	}
	//endregion
	
	//region [FUNC] connect
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
	//endregion
	
	//region [FUNC] get RedisClient
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
	//endregion
	
	//region [FUNC] get PoolStatus
	public RedisPoolStatus getPoolStatus() {
		return rm.getPoolStatus();
	}
	//endregion
}
