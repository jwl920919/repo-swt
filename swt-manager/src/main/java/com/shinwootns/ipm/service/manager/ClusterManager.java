package com.shinwootns.ipm.service.manager;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.service.handler.RedisHandler;

public class ClusterManager {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static String KEY_CLUSTER_MEMBER = "cluster:ipm:member";
	private final static String KEY_CLUSTER_MASTER = "cluster:ipm:master";
	
	
	private final static String KEY_CLUSTER_JOB = "cluster:ipm:job";
	
	private final static int EXPIRE_TIME_MEMBER = 10;
	//private final static int EXPIRE_TIME_MASTER = 10;
	
	// Singleton
	private static ClusterManager _instance = null;
	private ClusterManager() {}
	public static synchronized ClusterManager getInstance() {

		if (_instance == null) {
			_instance = new ClusterManager();
		}
		return _instance;
	}
	
	private boolean isMasterNode = false;
	
	public void updateClusterMember() {
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if ( appProperty == null )
			return;
		
		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if (redis == null)
			return;
		
		try
		{
			// rank
			String clusterMode = appProperty.clusterMode;
			double rank;
			if (clusterMode.toLowerCase().equals("master"))
				rank = 1;
			else if (clusterMode.toLowerCase().equals("slave"))
				rank = 10000;
			else
				rank = 20000;
			
			rank += appProperty.clusterSalveIndex;
			
			
			// update member
			String key = (new StringBuilder()).append(KEY_CLUSTER_MEMBER).append(":").append(SystemUtils.getHostName()).toString();
			
			//redis.set(key, rank.toString());
			
			//redisTemplate.opsForValue().set(key, rank);
			
			//redisTemplate.expire(key, EXPIRE_TIME_MEMBER, TimeUnit.SECONDS);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	
	public void checkClusterMaster() {
		/*
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		RedisTemplate redisTemplate = SpringBeanProvider.getInstance().getRedisTemplate();
		if ( appProperty == null || redisTemplate == null )
			return;
		
		// Get Master Info
		Set<String> keys = redisTemplate.keys(KEY_CLUSTER_MEMBER+":*");
		
		for(String key : keys) {
			System.out.println(key);
		}*/
	}
	
	/*
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
				Set<String> setMember = redisTemplate.opsForZSet().range(KEY_CLUSTER_MEMBER, 0, 0); // Top 1 by rank.
				
				if (setMember.size() == 1)
				{
					masterName = setMember.iterator().next();
					
					if (masterName != null && masterName.isEmpty() == false)
					{
						if (masterName.equals(hostName)) {
							
							// Set Master
							redisTemplate.opsForValue().set(KEY_CLUSTER_MASTER, masterName);
							//redisTemplate.expire(KEY_CLUSTER_MASTER, EXPIRE_TIME_MASTER, TimeUnit.SECONDS);
							
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
	*/
	
	public boolean isClusterMaster() {

		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if ( appProperty == null)
			return false;
		
		//RedisTemplate redisTemplate = SpringBeanProvider.getInstance().getRedisTemplate();
		
		try
		{
		
			String hostName = SystemUtils.getHostName();
	
			/*
			String masterName = (String)redisTemplate.opsForValue().get(KEY_CLUSTER_MASTER);
	
			if (masterName != null) {
				if (masterName.equals(hostName))
					return true;
				else
					return false;
			}*/
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	
	public void setMasterNode(boolean isMasterNode) {
		
		// If changed cluster mode.
		if (this.isMasterNode != isMasterNode) {
			
			_logger.info( String.format("Changed Cluster Mode --- %s !!!", (isMasterNode)? "MASTER":"SLAVE"));
			
			this.isMasterNode = isMasterNode;
			
			
			if (this.isMasterNode) {
				// master
				// TO-DO
			}
			else {
				// slave
				// TO-DO
			}
				
		}
	}
}
