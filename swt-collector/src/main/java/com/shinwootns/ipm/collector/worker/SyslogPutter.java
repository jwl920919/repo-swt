package com.shinwootns.ipm.collector.worker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.amqp.RabbitmqSender;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;

import redis.clients.jedis.Jedis;

public class SyslogPutter implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	private int _index = 0;
	
	public SyslogPutter(int index) {
		this._index = index;
	}
	
	private boolean isSkipInDebugMode() {
		// get ApplicationProperty
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return true;
		
		// debug_insert_event_enable
		if (appProperty.enable_recv_syslog == false)
			return true;
		
		return false;
	}

	@Override
	public void run() {
		
		if (isSkipInDebugMode())
			return;
		
		if ( _logger != null)
			_logger.info( (new StringBuilder()).append("Syslog Producer#").append(this._index).append("... start.").toString());
		
		List<SyslogEntity> listSyslog = SharedData.getInstance().popSyslogList(1000, 500);
		
		String redisKey = RedisKeys.KEY_DATA_SYSLOG + ":" + SharedData.getInstance().getSiteID();
		
		while(!Thread.currentThread().isInterrupted())
		{
			listSyslog = SharedData.getInstance().popSyslogList(100, 100);
			
			if (listSyslog != null && listSyslog.size() > 0)
			{
				
				// to Redis
				Jedis redis = RedisHandler.getInstance().getRedisClient();
				if (redis == null)
					return;
				
				for(SyslogEntity syslog : listSyslog)
				{
					// Remove Carriage-Return & Line-Feed
					syslog.setData( syslog.getData().replaceAll("\\r\\n|\\r|\\n|\\t", " ").trim() );
					
					// Put to Redis
					redis.zadd(redisKey, syslog.getRecvTime()/1000, JsonUtils.serialize(syslog).toString());
				}
				
				redis.close();
				
				/*
				int count = listSyslog.size();
				
				for(SyslogEntity syslog : listSyslog)
				{
					if (syslog == null)
						continue;
					
					String rawData = syslog.getData();
	
					// Remove Carriage-Return & Line-Feed
					rawData = rawData.replaceAll("\\r\\n|\\r|\\n", " ");
					
					// Trim
					rawData = rawData.trim();
					
					JsonObject jobj = new JsonObject();
					jobj.addProperty("host", syslog.getHost());
					jobj.addProperty("facility", syslog.getFacility());
					jobj.addProperty("severity", syslog.getSeverity());
					jobj.addProperty("recv_time", syslog.getRecvTime());
					jobj.addProperty("message", rawData);
					
					//RabbitmqSender.SendData(jobj, _logger);
				}
				
				listSyslog.clear();
				*/
			}
		}
		
		if ( this._logger != null)
			_logger.info( (new StringBuilder()).append("Syslog Producer#").append(this._index).append("... end.").toString());
	}
}
