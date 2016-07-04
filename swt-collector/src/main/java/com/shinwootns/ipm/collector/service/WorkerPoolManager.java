package com.shinwootns.ipm.collector.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.collector.service.syslog.SyslogProducer;

public class WorkerPoolManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private static final int SYSLOG_WORKER_COUNT = 3;
	
	// Singleton
	private static WorkerPoolManager _instance = null;
	private WorkerPoolManager() {}
	public static synchronized WorkerPoolManager getInstance() {

		if (_instance == null) {
			_instance = new WorkerPoolManager();
		}
		return _instance;
	}

	// Worker Pool
	private SmartThreadPool _workerPool = new SmartThreadPool();
	
	
	// Syslog Queue
	public java.util.Queue<SyslogEntity> syslogQueue = new ConcurrentLinkedQueue<SyslogEntity>();
	
	
	// Start
	public synchronized void start() {

		_logger.info("ServiceManager... start");

		if (_workerPool.createPool(SYSLOG_WORKER_COUNT, SYSLOG_WORKER_COUNT, SYSLOG_WORKER_COUNT)) {
			
			// Start Producer Worker
			for(int i=1; i<=SYSLOG_WORKER_COUNT; i++)
			{
				_workerPool.addTask(new SyslogProducer(i, _logger));
			}
			
		} else {
			_logger.fatal("[FATAL] Failed to create syslog-analyzer worker pool !!!");
			return;
		}
	}
	
	// Add Syslog Task
	public boolean addSyslogData(SyslogEntity syslog) {
		
		boolean bResult = false;
		
		while(bResult == false)
		{
			bResult = syslogQueue.add(syslog);
			
			if (bResult)
				break;
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		
		return bResult;
	}
	
	public List<SyslogEntity> popSyslogList(int popCount, int timeout) {
		
		List<SyslogEntity> resultList = new ArrayList<SyslogEntity>();
		
		if (popCount < 1)
			popCount = 1000;
		
		int count=0;
		
		long startTime = TimeUtils.currentTimeMilis();
		
		while(count < popCount )
		{
			SyslogEntity syslog = syslogQueue.poll();
			
			if (syslog != null)
			{
				count++;
				resultList.add(syslog);
			}
			else {
				
				if ( TimeUtils.currentTimeMilis() - startTime > timeout )
					break;
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
		
		return resultList;
	}
	
	// Pool Status
	public synchronized PoolStatus GetPoolStatus() {
		return _workerPool.getPoolStatus();
	}
	
	public synchronized void stop()
	{
		_workerPool.shutdownAndWait();
		
		_logger.info("ServiceManager....... stop");
	}
}
