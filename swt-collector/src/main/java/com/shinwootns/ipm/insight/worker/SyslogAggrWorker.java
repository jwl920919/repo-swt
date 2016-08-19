package com.shinwootns.ipm.insight.worker;

/*

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.common.utils.ip.IPAddr;
import com.shinwootns.data.entity.DhcpLog;
import com.shinwootns.data.entity.EventData;
import com.shinwootns.data.key.QueueNames;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.amqp.RabbitMQHandler;
import com.shinwootns.ipm.insight.service.redis.RedisManager;
import com.shinwootns.ipm.insight.service.syslog.DhcpMessage;
import com.shinwootns.ipm.insight.service.syslog.SyslogHandlerEx;

import redis.clients.jedis.Jedis;

public class SyslogAggrWorker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private Jedis _redis = null;
	
	private SyslogHandlerEx syslogHandler = new SyslogHandlerEx();
	
	//region [public] run
	@Override
	public void run() {

		long curTime = TimeUtils.currentTimeSecs();
		long prevTime = curTime;
		
		int start = 2;
		int end = 2;
		
		// Redis Key
		String redisKey = RedisKeys.KEY_DATA_SYSLOG + ":" + SharedData.getInstance().getSiteID();
		
		_connectRedis();
		
		// Delete
		if (this._redis != null)
			this._redis.zremrangeByScore(redisKey, -1, curTime-start-5);

		while(!Thread.currentThread().isInterrupted())
		{
			curTime = TimeUtils.currentTimeSecs();
			
			if (prevTime != curTime) {
				prevTime = curTime; 
				
				Set<String> dataSet = null;
				
				try
				{
					_connectRedis();
					
					if (this._redis != null) {
						
						// Get Range by Score 
						dataSet = this._redis.zrangeByScore(redisKey, curTime-start, curTime-end);
	
						// Delete read data
						this._redis.zremrangeByScore(redisKey, -1, curTime-end);
					}
				}
				catch(Exception ex) {
					_closeRedis();
					_connectRedis();
					
					continue;
				}
				
				if (dataSet != null) {
	
					HashMap<String, SyslogEntity> mapDupCheck = new HashMap<String, SyslogEntity>();		// <key, Data> 

					// 1. Remove duplication
					StringBuilder sbKey = new StringBuilder();
					
					for(String data : dataSet) {
						
						SyslogEntity syslog = (SyslogEntity)JsonUtils.deserialize(data, SyslogEntity.class);
						
						// create Key ( Host|Syslog )
						//sbKey.delete(0, sbKey.length());
						sbKey = null;
						sbKey = new StringBuilder();
						sbKey.append(syslog.getHost()).append("|").append(syslog.getData()).toString();
						
						// put Data
						if (mapDupCheck.containsKey(sbKey.toString()) == false)
							mapDupCheck.put(sbKey.toString(), syslog);
					}
					dataSet.clear();
					
					// 2. Sort by time
					LinkedHashMap<String, SyslogEntity> sortedMap = _sortBySyslogRecvTime(mapDupCheck);
					
					for(Entry<String, SyslogEntity> entry : sortedMap.entrySet()) {
						
						// ProcessSyslog
						_processSyslog(entry.getValue());
					}
					mapDupCheck.clear();
					sortedMap.clear();
				}
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	//endregion
	
	//region [private] Process Syslog
	private void _processSyslog(SyslogEntity syslog) {
		
		// Get Device ID
		Integer deviceId = SharedData.getInstance().getDeviceIDByIP( syslog.getHost() );
		
		boolean isInsertDhcpLog = false;
		
		// DHCP Message Parsing
		DhcpMessage dhcpMsg = syslogHandler.processSyslog(syslog);
		
		if (dhcpMsg != null) {
			
			try {
			
				IPAddr ipAddr = new IPAddr(dhcpMsg.getIp());
				
				Boolean isGuestRange = SharedData.getInstance().isGuestRange(ipAddr.getNumberToBigInteger()); 
				
				// Save DHCP log
				isInsertDhcpLog = _saveDhcpLog(deviceId, syslog, dhcpMsg, ipAddr, isGuestRange);
				
				// DHCPACK
				//if (dhcpMsg.getDhcpType().equals("DHCPACK")) {
					// ...
				//}
				
			} catch (UnknownHostException e) {
				_logger.error(e.getMessage(), e);
			}
		}
		

		// Send to Event MQ
		//if (isInsertDhcpLog == false)
			_sendEventLogMQ(deviceId, syslog);
	}
	//endregion
	
	
//	private void increaseSyslogCounter(long recvTime) {
//		Timestamp time = new Timestamp(recvTime);
//		@SuppressWarnings("deprecation")
//		int time10Sec = time.getSeconds() / 10;
//		
//		// Increase Syslog Counter
//		StringBuilder syslogKey = new StringBuilder();
//		syslogKey.append(RedisKeys.KEY_COUNTER_SYSLOG)
//			.append(":").append(SharedData.getInstance().getSiteID())
//			.append(":").append(time10Sec);
//		
//		if (this._redis != null)
//			this._redis.incr(syslogKey.toString());
//	}
//	
//	private void increaseDhcpCounter(long recvTime, String dhcp_type) {
//		Timestamp time = new Timestamp(recvTime);
//		@SuppressWarnings("deprecation")
//		int time10Sec = time.getSeconds() / 10;
//		
//		// Increase DHCP MSG Counter
//		StringBuilder dhcpKey = new StringBuilder();
//		dhcpKey.append(RedisKeys.KEY_COUNTER_DHCP)
//			.append(":").append(SharedData.getInstance().getSiteID())
//			.append(":").append(time10Sec);
//		
//		if (this._redis != null)
//			this._redis.zincrby(dhcpKey.toString(), 1, dhcp_type);
//	}
	
	
	//region [private] Send EventLog
	private void _sendEventLogMQ(Integer deviceId, SyslogEntity syslog) {

		//Send Event
		EventData eventData = new EventData();
		eventData.setHostIp(syslog.getHost());
		eventData.setDeviceId(deviceId);
		eventData.setEventType("syslog");
		eventData.setSeverity(syslog.getSeverity());
		eventData.setCollectTime( new Timestamp(syslog.getRecvTime()) );
		
		eventData.setMessage(syslog.getData());
		
		// Send to RabbitMQ
		RabbitMQHandler.getInstance().SendDataToMQ(QueueNames.EVENT_QUEUE_NAME, JsonUtils.serialize(eventData).getBytes() );
	}
	//endregion
	
	//region [private] Save DHCP Log
	private boolean _saveDhcpLog(Integer deviceId, SyslogEntity syslog, DhcpMessage dhcp, IPAddr ipAddr, Boolean isGuestRange) {

		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return false;

		DhcpLog dhcpLog = new DhcpLog();
		
		try
		{
			// DhcpLog
			dhcpLog.setSiteId(SharedData.getInstance().getSiteID());
			dhcpLog.setDhcpIp(syslog.getHost());
			dhcpLog.setDeviceId(deviceId);
			dhcpLog.setDhcpType(dhcp.getDhcpType());
			dhcpLog.setIsRenew(dhcp.getRenew());
			dhcpLog.setDuration(dhcp.getDuration());
			dhcpLog.setClientIpType(dhcp.getIpType());
			dhcpLog.setClientIpNum(ipAddr.getNumberToBigInteger());
			dhcpLog.setClientIp(dhcp.getIp());
			dhcpLog.setClientMac(dhcp.getMac());
			dhcpLog.setClientHostname(dhcp.getHostname());
			dhcpLog.setCollectTime(new Timestamp(syslog.getRecvTime()));
			dhcpLog.setClientDuid(dhcp.getDuid());
			dhcpLog.setIsGuestRange(isGuestRange);
			
			// Insert to DB
			dhcpMapper.insertDhcpLog(dhcpLog);
			
			return true;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage());
			_logger.error(dhcp.toString());
		}
		
		return false;
	}
	//endregion
	
	//region [private] sortBySyslogRecvTime
	private LinkedHashMap<String, SyslogEntity> _sortBySyslogRecvTime(Map<String, SyslogEntity> map) {
		
	    List<Entry<String, SyslogEntity>> list = new LinkedList<Entry<String, SyslogEntity>>(map.entrySet());
	    Collections.sort(list, new Comparator<Object>() {
	        @SuppressWarnings("unchecked")
	        public int compare(Object o1, Object o2) {
	        	
	        	Long data1 = ((Map.Entry<String, SyslogEntity>) (o1)).getValue().getRecvTime();
	        	Long data2 = ((Map.Entry<String, SyslogEntity>) (o2)).getValue().getRecvTime();
	        	
	            return data1.compareTo(data2);
	        }
	    });

	    LinkedHashMap<String, SyslogEntity> result = new LinkedHashMap<String, SyslogEntity>();
	    for (Iterator<Entry<String, SyslogEntity>> it = list.iterator(); it.hasNext();) {
	        Map.Entry<String, SyslogEntity> entry = (Map.Entry<String, SyslogEntity>) it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }

	    return result;
	}
	//endregion
	
	//region [private] Connect Redis
	private void _connectRedis() {
		
		if (this._redis == null)
			this._redis = RedisManager.getInstance().getRedisClient();
	}
	//endregion
	
	//region [private] Close Redis
	private void _closeRedis() {
		
		try {
			if (this._redis != null)
				this._redis.close();
		} catch(Exception ex) {
		} finally {
			this._redis = null;
		}
	}
	//endregion
}

*/
