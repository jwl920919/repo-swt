package com.shinwootns.ipm.insight.service.cluster;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.utils.CollectionUtils;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.WorkerManager;
import com.shinwootns.ipm.insight.config.ApplicationProperty;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DeviceMapper;
import com.shinwootns.ipm.insight.service.redis.RedisManager;

import redis.clients.jedis.Jedis;

public class ClusterManager {

	private final Logger _logger = LoggerFactory.getLogger(getClass());

	// Expire Time
	private final static int EXPIRE_TIME_MEMBER = 10;
	
	// is Master
	private Boolean isMasterNode = null;

	//region Singleton
	private static ClusterManager _instance = null;
	private ClusterManager() {}
	public static synchronized ClusterManager getInstance() {

		if (_instance == null) {
			_instance = new ClusterManager();
		}
		return _instance;
	}
	//endregion

	//region [public] Update Member
	public void updateMember() {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;

		Jedis redis = RedisManager.getInstance().getRedisClient();
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

			rank += appProperty.clusterIndex;

			// Update Member
			_updateMember(redis, rank);
			
			// Update MasterNode
			if (this.isMasterNode != null && this.isMasterNode)
				_updateMasterNode(redis);
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}finally {
			redis.close();
		}
	}
	//endregion

	//region [public] Check Cluster Master
	public void checkMaster() {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if ( appProperty == null ) 
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if (redis == null)
			return;
		
		try
		{
			// Key
			StringBuilder keyPattern = new StringBuilder();
			keyPattern.append(RedisKeys.KEY_INSIGHT_CLUSTER_MEMBER)
				.append(":").append(SharedData.getInstance().getSiteID())
				.append(":*");
			
			// Get Keys
			Set<String> keys = redis.keys(keyPattern.toString());
			
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
						_setMasterNode(true);
						
						// Key
						StringBuilder key = new StringBuilder();
						key.append(RedisKeys.KEY_INSIGHT_CLUSTER_MASTER)
							.append(":").append(SharedData.getInstance().getSiteID());
						
						// Set value
						redis.set(key.toString(), hostName);
						 
					} else { 
						// Set Slave
						_setMasterNode(false); 
					}
				}	
			}
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
	}
	//endregion

	//region [public] is Master
	public boolean isMaster() {
		if (isMasterNode != null && isMasterNode == true)
			return true;
		
		return false;
	}
	//endregion
	
	/*
	//region [private] Get MasterName
	private String getMasterName() {

		Jedis redis = RedisManager.getInstance().getRedisClient();
		if (redis == null)
			return "";
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return "";

		try {

			// Key
			StringBuilder key = new StringBuilder();
			key.append(RedisKeys.KEY_INSIGHT_CLUSTER_MASTER)
				.append(":").append(SharedData.getInstance().getSiteID());
			
			// Get value
			String masterName = redis.get(key.toString());
			
			return (masterName != null) ? masterName : "";

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}finally {
			redis.close();
		}

		return "";
	}
	//endregion
	*/
	
	/*
	//region [FUNC] is Master
	public boolean isMaster() {

		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if (redis == null)
			return false;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return false;

		try {
			
			// Key
			StringBuilder key = new StringBuilder();
			key.append(RedisKeys.KEY_INSIGHT_CLUSTER_MASTER)
				.append(":").append(SharedData.getInstance().getSiteID());

			// Get value
			String masterName = redis.get(key.toString());
			
			if(masterName != null && masterName.equals(SystemUtils.getHostName()))
				return true;

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}

		return false;
	}
	//endregion
	*/
	
	//region [private] _setMasterNode
	private void _setMasterNode(boolean isMasterNode) {

		// If changed cluster mode.
		if (this.isMasterNode == null || this.isMasterNode != isMasterNode) {

			// MASTER
			if (isMasterNode) {
				
				Jedis redis = RedisManager.getInstance().getRedisClient();
				if (redis == null)
					return;
					
				try
				{
					// Wait for other master's termination
					if (_isRunningOtherMasterNode(redis) == false)
					{
						this.isMasterNode = isMasterNode;
						
						_logger.info( (new StringBuilder())
								.append("************************************")
								.append(" Cluster Mode = ")
								.append((isMasterNode) ? "MASTER " : "SLAVE ")
								.append("************************************")
								.toString()
						);
						
						// Update To DB
						DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
						if (deviceMapper != null && SharedData.getInstance().getSiteID() >= 0) {
							deviceMapper.updateInsightMaster(SharedData.getInstance().getSiteID(), SystemUtils.getHostName());
						}
						
						// Start MasterJobWorker
						WorkerManager.getInstance().startMasterJobWorker();
					}
				} catch(Exception ex) {
					_logger.error(ex.getMessage(), ex);
				} finally {
					redis.close();
				}
			}
			// SLAVE
			else {
				
				this.isMasterNode = isMasterNode;
				
				_logger.info( (new StringBuilder())
						.append("************************************")
						.append(" Cluster Mode = ")
						.append((isMasterNode) ? "MASTER " : "SLAVE ")
						.append("************************************")
						.toString()
				);
				
				// Debug Mode - force_start_cluster_master
				ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
				if (appProperty != null && appProperty.debugEnable && appProperty.force_start_cluster_master) {
					// Debug Mode -  force_start_cluster_master
					WorkerManager.getInstance().startMasterJobWorker();
				}
				else {
					// Stop MasterJobWorker
					WorkerManager.getInstance().stopMasterJobWorker();
				}

				Jedis redis = RedisManager.getInstance().getRedisClient();
				if (redis != null) {
					try {
						// Update slave mode
						_removeMasterNode(redis);
					} catch(Exception ex) {
						_logger.error(ex.getMessage(), ex);
					} finally {
						redis.close();
					}
				}
			}

		}
	}
	//endregion
	
	//region [redis] _updateMember
	private void _updateMember(Jedis redis, int rank)
	{
		if (redis != null) {
			// Key
			StringBuilder key = new StringBuilder();
			key.append(RedisKeys.KEY_INSIGHT_CLUSTER_MEMBER)
				.append(":").append(SharedData.getInstance().getSiteID())
				.append(":").append(SystemUtils.getHostName());
	
			// Set Value
			redis.set(key.toString(), String.format("%d", rank));
	
			// Expire Time
			redis.expire(key.toString(), EXPIRE_TIME_MEMBER);
		}
	}
	//endregion
	
	//region [redis] _updateMasterNode
	private void _updateMasterNode(Jedis redis)
	{
		if (redis != null)
		{
			StringBuilder key = new StringBuilder();
			key.append(RedisKeys.KEY_INSIGHT_CLUSTER_MASTER)
				.append(":").append(SharedData.getInstance().getSiteID())
				.append(":").append(SystemUtils.getHostName());
			
			redis.set(key.toString(), SystemUtils.getHostName());
			redis.expire(key.toString(), EXPIRE_TIME_MEMBER);
		}
	}
	//endregion
	
	//region [redis] _isRunningOtherMasterNode
	private boolean _isRunningOtherMasterNode(Jedis redis) {
		
		if (redis != null) {
		
			StringBuilder searchKey = new StringBuilder();
			searchKey.append(RedisKeys.KEY_INSIGHT_CLUSTER_MASTER)
				.append(":").append(SharedData.getInstance().getSiteID())
				.append(":*");
			
			Set<String> setMasterNode = redis.keys(searchKey.toString());
			
			for(String masterNode : setMasterNode) {
				if ( masterNode.equals( SystemUtils.getHostName() ) == false ) {
					return true;
				}
			}
			
			return false;
		}
		
		return true;
	}
	//endregion
	
	//region [redis] _removeMasterNode
	private void _removeMasterNode(Jedis redis)
	{
		if (redis != null) {
			
			StringBuilder key = new StringBuilder();
			key.append(RedisKeys.KEY_INSIGHT_CLUSTER_MASTER)
				.append(":").append(SharedData.getInstance().getSiteID())
				.append(":").append(SystemUtils.getHostName());
			
			redis.del(key.toString());
		}
	}
	//endregion
}
