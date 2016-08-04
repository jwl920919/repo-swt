package com.shinwootns.ipm.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.redis.RedisManager.RedisPoolStatus;
import com.shinwootns.ipm.service.cluster.ClusterManager;
import com.shinwootns.ipm.service.redis.RedisHandler;

public class SchedulerWorker implements Runnable {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);
	
	@Override
	public void run() {
		
		ClusterManager.getInstance().updateMember();
		ClusterManager.getInstance().checkMaster();
		
		_logger.info("SchedulerWorker... start.");
		
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
		
		// wait termination
		while(!Thread.currentThread().isInterrupted()) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		// shutdown scheduler service
		schedulerService.shutdown();
		
		_logger.info("SchedulerWorker... end.");
	}
	
	// 3 Seconds
	public void run3SecCycle() {
		ClusterManager.getInstance().updateMember();
	}
	
	// 10 Seconds
	public void run10SecCycle() {
		ClusterManager.getInstance().checkMaster();
	}
	
	// 30 Seconds
	public void run30SecCycle() {
		displayStatus();
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
