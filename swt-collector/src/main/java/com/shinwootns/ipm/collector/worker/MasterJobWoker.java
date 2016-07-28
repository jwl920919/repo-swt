package com.shinwootns.ipm.collector.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.data.status.DhcpCounter;
import com.shinwootns.data.status.DhcpDeviceStatus;
import com.shinwootns.data.status.DhcpVrrpStatus;
import com.shinwootns.data.status.DnsCounter;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.cluster.ClusterManager;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;

public class MasterJobWoker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);

	//region [FUNC] run
	@Override
	public void run() {
		
		_logger.info("MasterJobWoker... start.");
		
		// FixedDelay 10 seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						updateDhcpStatus();
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
	//endregion
	
	//region [FUNC] Update Dhcp Device
	public void updateDhcpStatus() {
		
		if ( ClusterManager.getInstance().isMaster() == false)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;
		
		RedisClient client = RedisHandler.getInstance().getRedisClient();
		if(client == null)
			return;

		// DHCP Handler
		DhcpHandler handler = new DhcpHandler(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity());
		
		updateDhcpDeviceStatus(handler, client);
		updateDhcpVrrpStatus(handler, client);
		updateDhcpCount(handler, client);
		updateDnsCount(handler, client);
		
		client.close();
	}
	//endregion

	//region [FUNC] Update Dhcp Device Status
	private void updateDhcpDeviceStatus(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			DhcpDeviceStatus dhcpStatus = handler.getDeviceStatus();
			
			if (dhcpStatus != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dhcpStatus);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DEVICE)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);
				
				_logger.info("updateDhcpDeviceStatus()... OK");
			}
			
		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion

	//region [FUNC] Update DHCP VRRP Status
	private void updateDhcpVrrpStatus(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {
			
			DhcpVrrpStatus vrrpStatus = handler.getVRRPStatus();

			if (vrrpStatus != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(vrrpStatus);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_VRRP)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);

				_logger.info("updateDhcpVrrpStatus()... OK");
			}

		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region [FUNC] Update DHCP Count
	private void updateDhcpCount(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			DhcpCounter dhcpCounter = handler.getDhcpCounter();
			
			if (dhcpCounter != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dhcpCounter);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DHCP_COUNTER)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);

				_logger.info("updateDhcpCount()... OK");
			}

		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
	
	//region [FUNC] Update DNS Count
	private void updateDnsCount(DhcpHandler handler, RedisClient client) {

		if (handler == null || client == null)
			return;
		
		if (SharedData.getInstance().getSiteID() <= 0)
			return;
		
		try {

			DnsCounter dnsCounter = handler.getDnsCounter();
			
			if (dnsCounter != null) {
				
				// Serialize to Json
				String json = JsonUtils.serialize(dnsCounter);
				
				// Set value
				client.set((new StringBuilder())
						.append(RedisKeys.KEY_STATUS_DNS_COUNTER)
						.append(":").append(SharedData.getInstance().getSiteID()).toString()
						, json
				);

				_logger.info("updateDnsCount()... OK");
			}

		} catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
	//endregion
}
