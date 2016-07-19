package com.shinwootns.ipm.service.handler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.service.manager.ClusterManager;

public class RedisHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private final static String KEY_CLUSTER_RANK = "cluster:rank";
	private final static String KEY_CLUSTER_MEMBER = "cluster:member";
	private final static String KEY_CLUSTER_MASTER = "cluster:master";
	
	private final static int EXPIRE_TIME_MEMBER = 10;
	private final static int EXPIRE_TIME_MASTER = 10;
	
	
	// Singleton
	private static RedisHandler _instance = null;
	private RedisHandler() {}
	public static synchronized RedisHandler getInstance() {

		if (_instance == null) {
			_instance = new RedisHandler();
		}
		return _instance;
	}

	public void registClusterRank() {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		StringRedisTemplate redisTemplate = SpringBeanProvider.getInstance().getRedisTemplate();
		if ( appProperty == null || redisTemplate == null )
			return;
		
		try
		{
			String hostName = SystemUtils.getHostName();
			
			// rank
			String clusterMode = appProperty.clusterMode;
			double rank;
			if (clusterMode.toLowerCase().equals("master"))
				rank = 1;
			else if (clusterMode.toLowerCase().equals("slave"))
				rank = 10000;
			else
				rank = 1000000;
			
			rank += appProperty.clusterSalveIndex;
			
			// Set
			redisTemplate.opsForZSet().add(KEY_CLUSTER_RANK, hostName, rank);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	
	public void updateClusterMember() {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		StringRedisTemplate redisTemplate = SpringBeanProvider.getInstance().getRedisTemplate();
		if ( appProperty == null || redisTemplate == null )
			return;
		
		try
		{
			// Update Member
			String hostName = SystemUtils.getHostName();
			
			String key = String.format("%s:%s", KEY_CLUSTER_MEMBER, hostName);
			
			redisTemplate.opsForValue().set(key, hostName);
			
			redisTemplate.expire(key, EXPIRE_TIME_MEMBER, TimeUnit.SECONDS);
			
			
			// Get Master Info
			String masterName = (String)redisTemplate.opsForValue().get(KEY_CLUSTER_MASTER);
			
			// Master is none
			if (masterName == null || masterName.isEmpty()) {
				
				// Lookup master candidate
				Set<String> setMember = redisTemplate.opsForZSet().range(KEY_CLUSTER_RANK, 0, 0); // Top 1 by rank.
				
				if (setMember.size() == 1)
				{
					masterName = setMember.iterator().next();
					
					if (masterName != null && masterName.isEmpty() == false)
					{
						if (masterName.equals(hostName)) {
							
							// Set Master
							redisTemplate.opsForValue().set(KEY_CLUSTER_MASTER, masterName);
							redisTemplate.expire(KEY_CLUSTER_MASTER, EXPIRE_TIME_MASTER, TimeUnit.SECONDS);
							
							// Master node
							ClusterManager.getInstance().setMasterNode(true);
						}
						else {
							// Slave node
							ClusterManager.getInstance().setMasterNode(false);
						}
					}
				}
			}
			else if (masterName.equals(hostName)) {
				// Master node
				ClusterManager.getInstance().setMasterNode(true);
			}
			else {
				// Slave node
				ClusterManager.getInstance().setMasterNode(false);
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	
	public boolean isClusterMaster() {

		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		StringRedisTemplate redisTemplate = SpringBeanProvider.getInstance().getRedisTemplate();
		if ( appProperty == null || redisTemplate == null )
			return false;
		
		try
		{
		
			String hostName = SystemUtils.getHostName();
	
			String masterName = (String)redisTemplate.opsForValue().get(KEY_CLUSTER_MASTER);
	
			if (masterName != null) {
				if (masterName.equals(hostName))
					return true;
				else
					return false;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public void setClusterCandidate() {
		
	}
}