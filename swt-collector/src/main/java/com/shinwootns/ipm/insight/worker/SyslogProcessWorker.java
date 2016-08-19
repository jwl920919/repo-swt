package com.shinwootns.ipm.insight.worker;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.ip.IPAddr;
import com.shinwootns.data.entity.DhcpLog;
import com.shinwootns.data.entity.EventData;
import com.shinwootns.data.key.QueueNames;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.config.ApplicationProperty;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.amqp.RabbitMQHandler;
import com.shinwootns.ipm.insight.service.cluster.ClusterManager;
import com.shinwootns.ipm.insight.service.redis.RedisManager;
import com.shinwootns.ipm.insight.service.syslog.DhcpMessage;
import com.shinwootns.ipm.insight.service.syslog.SyslogHandlerEx;

import redis.clients.jedis.Jedis;

public class SyslogProcessWorker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	private int _index = 0;
	
	private SyslogHandlerEx syslogHandler = new SyslogHandlerEx();
	
	public SyslogProcessWorker(int index) {
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
						
						// [Direct] Process syslog only master
						if ( ClusterManager.getInstance().isMaster() )
						{
							_processSyslog(syslog);
						}

						// [Indirect] Put to Redis
						//else {
							//redis.zadd(redisKey, syslog.getRecvTime()/1000, JsonUtils.serialize(syslog).toString());
						//}
						
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
		if (isInsertDhcpLog == false)
			_sendEventLogMQ(deviceId, syslog);
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
}
