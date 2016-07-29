package com.shinwootns.ipm.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.cache.RedisManager;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;

public class RedisHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private RedisManager rm = new RedisManager();
	
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
	public boolean connect() throws Exception 
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		if (rm.connect(
				appProperty.redisHost, 
				appProperty.redisPort, 
				CryptoUtils.Decode_AES128(appProperty.redisPassword), 
				0) == false)
		{
			System.out.println("Redis connection failed.");
			return false;
		}

		return true;
	}
	//endregion
	
	//region [FUNC] Get RedisClient
	public RedisClient getRedisClient() {
		
		RedisClient redis = rm.createRedisClient();
		
		return redis;
	}
	//endregion
}
