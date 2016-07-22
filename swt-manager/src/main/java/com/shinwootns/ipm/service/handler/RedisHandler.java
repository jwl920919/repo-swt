package com.shinwootns.ipm.service.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.cache.RedisManager;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.service.manager.ClusterManager;

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

	
	public boolean Connect() 
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		if (rm.connect(appProperty.redisHost, appProperty.redisPort, appProperty.redisPassword, 0) == false)
		{
			System.out.println("Redis connection failed.");
			return false;
		}

		return true;
	}
	
	public RedisClient getRedisClient() {
		
		RedisClient redis = rm.createRedisClient();
		
		return redis;
	}
}
