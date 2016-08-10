package com.shinwootns.ipm.insight.worker;

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
import java.util.Set;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.data.entity.EventData;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.service.amqp.RabbitMQHandler;
import com.shinwootns.ipm.insight.service.redis.RedisHandler;
import com.shinwootns.ipm.insight.service.syslog.DhcpMessage;
import com.shinwootns.ipm.insight.service.syslog.SyslogHandler;

import redis.clients.jedis.Jedis;

public class SyslogPublisher implements Runnable {

	private Jedis _redis = null;
	
	SyslogHandler syslogHandler = new SyslogHandler();
	
	//region [public] run
	@Override
	public void run() {
		
		
		long curTime = TimeUtils.currentTimeSecs();
		long prevTime = curTime;
		
		int start = 2;
		int end = 2;
		
		// Redis Key
		String redisKey = RedisKeys.KEY_DATA_SYSLOG + ":" + SharedData.getInstance().getSiteID();
		
		connectRedis();
		
		// Delete
		if (this._redis != null)
			this._redis.zremrangeByScore(redisKey, -1, curTime-start-5);

		while(!Thread.currentThread().isInterrupted())
		{
			curTime = TimeUtils.currentTimeSecs();
			
			if (prevTime != curTime) {
				prevTime = curTime; 
				
				connectRedis();
				
				Set<String> dataSet = null;
				
				try
				{
					if (this._redis != null) {
						
						// Get Range by Score 
						dataSet = this._redis.zrangeByScore(redisKey, curTime-start, curTime-end);
	
						// Delete read data
						this._redis.zremrangeByScore(redisKey, -1, curTime-end);
					}
				}
				catch(Exception ex) {
					closeRedis();
					connectRedis();
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
					LinkedHashMap<String, SyslogEntity> sortedMap = sortBySyslogRecvTime(mapDupCheck);
					
					for(Entry<String, SyslogEntity> entry : sortedMap.entrySet()) {
						
						// Get Device ID
						Integer deviceId = SharedData.getInstance().getDeviceId( entry.getValue().getHost() );
						
						// DHCP Message Parsing
						DhcpMessage dhcpMsg = syslogHandler.processSyslog(entry.getValue().getData());
						
						if (dhcpMsg != null) {
							// DHCP
							
							// ...

							System.out.println(entry.getValue().toString());
							System.out.println(dhcpMsg.toString());
						}

						SendEventMQ(deviceId, entry.getValue());
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
	
	//region SendEventMQ
	private void SendEventMQ(Integer deviceId, SyslogEntity syslog) {

		//Send Event
		EventData eventData = new EventData();
		eventData.setHostIp(syslog.getHost());
		eventData.setDeviceId(deviceId);
		eventData.setEventType("syslog");
		eventData.setSeverity(syslog.getSeverity());
		eventData.setCollectTime( new Timestamp(syslog.getRecvTime()) );
		
		eventData.setMessage(syslog.getData());
		
		// Send to RabbitMQ
		RabbitMQHandler.getInstance().SendEvent( JsonUtils.serialize(eventData).getBytes() );
	}
	//endregion
	
	//region sortBySyslogRecvTime
	private LinkedHashMap<String, SyslogEntity> sortBySyslogRecvTime(Map<String, SyslogEntity> map) {
		
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
	
	//region Connect Redis
	private void connectRedis() {
		
		if (this._redis == null)
			this._redis = RedisHandler.getInstance().getRedisClient();
	}
	//endregion
	
	//region Close Redis
	private void closeRedis() {
		
		try {
			if (this._redis != null)
				this._redis.close();
		} catch(Exception ex) {
		} finally {
			this._redis = null;;
		}
	}
	//endregion
}
