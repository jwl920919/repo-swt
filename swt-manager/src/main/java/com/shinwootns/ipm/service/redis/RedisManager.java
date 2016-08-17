package com.shinwootns.ipm.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.redis.RedisHandler;
import com.shinwootns.common.redis.RedisHandler.RedisPoolStatus;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;

import redis.clients.jedis.Jedis;

public class RedisManager {
	
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	//RedisManager 
	RedisHandler rm = null;
	
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
	
	//region [FUNC] connect
	public boolean connect() 
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		synchronized(this)
		{
			try {
				if (rm != null) {
					rm.close();
				}
			} catch(Exception ex) {
			} finally {
				rm = null;
			}
			
			try
			{
				rm = new RedisHandler(
						appProperty.redisHost
						, appProperty.redisPort
						, CryptoUtils.Decode_AES128(appProperty.redisPassword)
						, 0);
				
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
		}

		return false;
	}
	//endregion
	
	//region [FUNC] get RedisClient
	public Jedis getRedisClient() {
		
		Jedis redis = null;
		
		synchronized(this)
		{
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
		
		return redis;
	}
	//endregion
	
	//region [FUNC] get PoolStatus
	public RedisPoolStatus getPoolStatus() {
		
		synchronized(this)
		{
			return rm.getPoolStatus();
		}
	}
	//endregion
}
