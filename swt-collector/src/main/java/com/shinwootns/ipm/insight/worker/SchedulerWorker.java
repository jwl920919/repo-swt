package com.shinwootns.ipm.insight.worker;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.redis.RedisManager.RedisPoolStatus;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DeviceMapper;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.cluster.ClusterManager;
import com.shinwootns.ipm.insight.service.redis.RedisHandler;

public class SchedulerWorker implements Runnable {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);
	
	@Override
	public void run() {
		
		ClusterManager.getInstance().updateMember();
		ClusterManager.getInstance().checkMaster();
		
		_logger.info("SchedulerWorker... start.");
		
		// Update ClusterMember
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						ClusterManager.getInstance().updateMember();
						ClusterManager.getInstance().checkMaster();
					}
				}
				,0 ,5 ,TimeUnit.SECONDS
		);
		
		// 30 Seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						run30SecCycle();
					}
				}
				,0, 30 , TimeUnit.SECONDS
		);
		
		// 300 Seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						run300SecCycle();
					}
				}
				,0, 300 , TimeUnit.SECONDS
		);
		
		// wait termination
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		// shutdown scheduler
		schedulerService.shutdown();
		
		_logger.info("SchedulerWorker... end.");
	}
	
	// 30 Seconds
	private void run30SecCycle() {
		
		displayStatus();
	}
	
	// 300 Seconds
	private void run300SecCycle() {
		
		DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
		if (deviceMapper != null) {
			SharedData.getInstance().LoadDhcpDevice(deviceMapper);
			SharedData.getInstance().LoadDeviceIP(deviceMapper);
			SharedData.getInstance().LoadSysOID(deviceMapper);
		}
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper != null) {
			SharedData.getInstance().LoadGuestRange(dhcpMapper);
		}
		
	}
	
	//region [FUNC] Display Status
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
	//endregion
}
