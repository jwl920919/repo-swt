package com.shinwootns.ipm.collector.worker.persist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.cache.RedisManager.RedisPoolStatus;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.cluster.ClusterManager;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.ipm.collector.service.redis.RedisHandler;
import com.shinwootns.ipm.collector.worker.BaseWorker;
import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.status.DhcpStatus;

public class SchedulerWorker extends BaseWorker {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	public final static String KEY_DEIVCE_DHCP_STATUS = "status:device:dhcp";
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);
	
	
	
	@Override
	public void run() {
		
		_logger.info(String.format("SchedulerWorker... start."));
		
		// 3 Seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						run3SecCycle();
					}
				}
				,0 ,3 ,TimeUnit.SECONDS
		);
		
		// 10 Seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						run10SecCycle();
					}
				}
				,0, 10 , TimeUnit.SECONDS
		);
		
		// wait termination
		while(this.isStopFlag() == false) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		// shutdown scheduler service
		schedulerService.shutdown();
	}
	
	// 3 Seconds
	private void run3SecCycle() {
		ClusterManager.getInstance().updateClusterMember();
		
		// ...
	}
	
	// 10 Seconds
	private void run10SecCycle() {
		ClusterManager.getInstance().checkClusterMaster();
		
		updateRedisDhcpStatus();
		
		displayStatus();
	}
	
	
	private void updateRedisDhcpStatus() {
		
		if (SharedData.getInstance().site_info == null)
			return;
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return;

		DhcpHandler handler = new DhcpHandler(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()); 
		DhcpStatus dhcpStatus = handler.getHWStatus();
		
		if (dhcpStatus != null) {
			String json = JsonUtils.serialize(dhcpStatus);
			
			RedisClient client = RedisHandler.getInstance().getRedisClient();
			if(client != null) {
				
				client.set(
						(new StringBuilder()).append(KEY_DEIVCE_DHCP_STATUS).append(":").append(SharedData.getInstance().site_info.getSiteId()).toString()
						, json);
				
				client.close();
			}
		}
	}
	
	private void displayStatus() {
		
		// Redis
		RedisPoolStatus status = RedisHandler.getInstance().getPoolStatus();
		
		if (status != null) {
			_logger.info((new StringBuilder())
					.append("REDIS-STATUS : ").append(status)
					.toString()
			);
		}
	}
}
