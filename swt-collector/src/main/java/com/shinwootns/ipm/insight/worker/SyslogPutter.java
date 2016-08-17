package com.shinwootns.ipm.insight.worker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.config.ApplicationProperty;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.service.redis.RedisManager;

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
		
		List<SyslogEntity> listSyslog = null;
		
		String redisKey = RedisKeys.KEY_DATA_SYSLOG + ":" + SharedData.getInstance().getSiteID();
		
		Jedis redis = null;
		
		while(!Thread.currentThread().isInterrupted())
		{
			try {
				listSyslog = SharedData.getInstance().popSyslogList(100, 100);

				if (listSyslog != null && listSyslog.size() > 0)
				{
					
					// to Redis
					if (redis == null)
						redis = RedisManager.getInstance().getRedisClient();
					
					if (redis == null) {
						Thread.sleep(3000);
						continue;
					}
					
					for(SyslogEntity syslog : listSyslog)
					{
						// Remove Carriage-Return & Line-Feed
						syslog.setData( syslog.getData().replaceAll("\\r\\n|\\r|\\n|\\t", " ").trim() );
						
						// Put to Redis
						redis.zadd(redisKey, syslog.getRecvTime()/1000, JsonUtils.serialize(syslog).toString());
					}
				}
				
			} catch (InterruptedException e) {
				break;
			} catch (Exception ex ) {
				_logger.error(ex.getMessage(), ex);
				
				try {
					if (redis != null && redis.ping() == null) {
						redis.close();
						redis = null;
					}
				}catch(Exception e2) {
					if (redis != null)
						redis.close();
					redis = null;
				}
			}
		}
		
		if (redis != null)
			redis.close();
		redis = null;
		
		if ( this._logger != null)
			_logger.info( (new StringBuilder()).append("Syslog Producer#").append(this._index).append("... end.").toString());
	}
}
