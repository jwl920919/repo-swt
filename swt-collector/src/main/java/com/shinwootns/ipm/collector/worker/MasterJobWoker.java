package com.shinwootns.ipm.collector.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.status.DhcpStatus;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.cluster.ClusterManager;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;

public class MasterJobWoker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public final static String KEY_DEIVCE_DHCP_STATUS = "status:device:dhcp";
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);

	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		// UpdateDhcpStatus
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						updateDhcpStatusInfo();
					}
				}
				,0 ,10 ,TimeUnit.SECONDS
		);
		
		// wait
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		schedulerService.shutdown();
		
		_logger.info("MasterJobWoker... end.");
	}

	//region [FUNC] Update Dhcp Status
	private void updateDhcpStatusInfo() {
		
		if ( ClusterManager.getInstance().isMaster() == false)
			return;
		
		if (SharedData.getInstance().site_info == null)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		RedisClient client = RedisHandler.getInstance().getRedisClient();
		if(client == null)
			return;

		try {
			
			// DHCP Handler
			DhcpHandler handler = new DhcpHandler(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity());
			
			DhcpStatus dhcpStatus = handler.getHWStatus();
			
			if (dhcpStatus != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dhcpStatus);
				
				// Set value
				client.set((new StringBuilder())
						.append(KEY_DEIVCE_DHCP_STATUS).append(":")
						.append(SharedData.getInstance().site_info.getSiteId()).toString()
						, json
				);
				
				_logger.info("updateDhcpStatusInfo()... OK");
			}
			
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}finally {
			client.close();
		}
	}
	//endregion
}
