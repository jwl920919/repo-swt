package com.shinwootns.ipm.insight.worker;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.data.entity.NmapScanIP;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.config.ApplicationProperty;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.nmap.NmapScanner;

public class ClientScanWorker implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());

	//Task Count
	private static final int TASK_COUNT = 10;
		
	// Task Pool
	private SmartThreadPool _taskPool = new SmartThreadPool();

	private ScheduledExecutorService schedulerService = Executors.newScheduledThreadPool(1);
	
	@Override
	public void run() {
		
		_logger.info("ClientScanWorker... start.");
		
		// Create Task Pool
		_taskPool.createPool(TASK_COUNT, TASK_COUNT, TASK_COUNT);
		
		
		// Collect System & Interface
		schedulerService.scheduleWithFixedDelay(
				new Runnable() {
					@Override
					public void run() {
						collectStart();
					}
				}
				,0 ,60 ,TimeUnit.SECONDS
		);
		
		// wait termination
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
		
		_logger.info("ClientScanWorker... end.");
	}

	//region Collect Start
	private void collectStart() {
	
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return;
		
		DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
		if (dhcpMapper == null)
			return;
		
		_logger.info("Client Scan... Start.");
		
		
		List<NmapScanIP> listIP;
		
		if (appProperty.debugEnable && appProperty.test_collect_nmap) {
			// For Debugging
			listIP = dhcpMapper.selectNmapScanIP(SharedData.getInstance().getSiteID(), null);
		}
		else {
			// By Site - Insight
			listIP = dhcpMapper.selectNmapScanIP(SharedData.getInstance().getSiteID(), SystemUtils.getHostName());
		}
		
		for(NmapScanIP ip : listIP) {
			
			try {
				
				while ( _taskPool.addTask( new NmapScanner(ip) ) == false ) {
					Thread.sleep(1000);
					continue;
				}
				
				//_logger.info("Put Scan: "+ip.getIpaddr());
				
			} catch (InterruptedException e) {
				break;
			}
		}
		
		listIP.clear();
		
		_logger.info("Client Scan... End.");
	}
	//endregion
}
