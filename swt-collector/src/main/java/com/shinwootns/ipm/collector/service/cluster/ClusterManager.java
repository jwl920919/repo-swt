package com.shinwootns.ipm.collector.service.cluster;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.CollectionUtils;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;

public class ClusterManager {

	private final Logger _logger = LoggerFactory.getLogger(getClass());

	private final static String KEY_CLUSTER_MEMBER = "cluster:insight:member";
	private final static String KEY_CLUSTER_MASTER = "cluster:insight:master";
	private final static String KEY_CLUSTER_JOB = "cluster:insight:job";

	private final static int EXPIRE_TIME_MEMBER = 10;
	// private final static int EXPIRE_TIME_MASTER = 10;

	// Singleton
	private static ClusterManager _instance = null;

	private ClusterManager() {
	}

	public static synchronized ClusterManager getInstance() {

		if (_instance == null) {
			_instance = new ClusterManager();
		}
		return _instance;
	}

	private boolean isMasterNode = false;

	public void updateClusterMember() {
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return;

		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if (redis == null)
			return;

		try {
			// rank
			String clusterMode = appProperty.clusterMode;
			int rank = 0;
			if (clusterMode.toLowerCase().equals("master"))
				rank = 1;
			else if (clusterMode.toLowerCase().equals("slave"))
				rank = 10000;
			else
				rank = 20000;

			rank += appProperty.clusterSalveIndex;

			// update member
			String key = (new StringBuilder()).append(KEY_CLUSTER_MEMBER).append(":").append(SystemUtils.getHostName())
					.toString();

			redis.set(key, String.format("%d", rank));

			redis.expire(key, EXPIRE_TIME_MEMBER);
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		redis.close();
	}

	public void checkClusterMaster() {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if ( appProperty == null ) 
			return;
		 
		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if (redis == null)
			return;
		
		try
		{
		
			// Get Keys
			HashSet<String> keys = redis.keys(KEY_CLUSTER_MEMBER+":*");
			
			// Get Values
			HashMap<String, Integer> mapMember = new HashMap<String, Integer>();
			for(String key : keys) {
				mapMember.put(key, Integer.parseInt(redis.get(key)));
			}
			
			// Sort by valuse
			LinkedHashMap<String, Integer> sortMap = CollectionUtils.sortMapByValue(mapMember);
			//System.out.println(sortMap);
			
			Iterator<Entry<String, Integer>> iter = sortMap.entrySet().iterator();
			
			if (iter != null && iter.hasNext()) {
				
				Entry<String, Integer> entry = iter.next();
				
				// Top 1
				String masterKey = entry.getKey();
				String hostName = SystemUtils.getHostName();
	
				// check Master
				int index = masterKey.lastIndexOf(":");
				
				if (index > 0 && masterKey.length() > index+1 ) {
					
					String masterName = masterKey.substring(index+1);
					
					// is Master node
					if (masterName.isEmpty() == false && masterName.equals(hostName)) {
						
						// Set Master
						ClusterManager.getInstance().setMasterNode(true);
						 
						redis.set(KEY_CLUSTER_MASTER, hostName);
						 
					} else { 
						// Set Slave
						ClusterManager.getInstance().setMasterNode(false); 
					}
				}	
			}
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
	}
	
	public String getMasterName() {

		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if (redis == null)
			return "";

		try {

			String masterName = redis.get(KEY_CLUSTER_MASTER);
			
			
			
			return (masterName != null) ? masterName : "";

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}finally {
			redis.close();
		}

		return "";
	}

	public boolean isClusterMaster() {

		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		RedisClient redis = RedisHandler.getInstance().getRedisClient();
		if (redis == null)
			return false;

		try {

			String masterName = redis.get(KEY_CLUSTER_MASTER);
			
			if(masterName != null && masterName.equals(SystemUtils.getHostName()))
				return true;

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}

		return false;
	}

	public void setMasterNode(boolean isMasterNode) {

		// If changed cluster mode.
		if (this.isMasterNode != isMasterNode) {

			_logger.info(String.format("Cluster mode changed. MODE = %s", (isMasterNode) ? "MASTER" : "SLAVE"));

			this.isMasterNode = isMasterNode;

			if (this.isMasterNode) {
				// master
				// TO-DO
			} else {
				// slave
				// TO-DO
			}

		}
	}
}
