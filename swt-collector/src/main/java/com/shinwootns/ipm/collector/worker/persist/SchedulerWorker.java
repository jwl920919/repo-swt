package com.shinwootns.ipm.collector.worker.persist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.ipm.collector.worker.BaseWorker;

public class SchedulerWorker extends BaseWorker {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final static int SCHEDULER_THREAD_COUNT = 2;
	
	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(SCHEDULER_THREAD_COUNT);
	
	@Override
	public void run() {
		
		_logger.info(String.format("SchedulerWorker... start."));
		
		// 3 Seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						//ClusterManager.getInstance().updateClusterMember();
					}
				}
				,0 ,3 ,TimeUnit.SECONDS
		);
		
		// 10 Seconds
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						//ClusterManager.getInstance().checkClusterMaster();
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
}
