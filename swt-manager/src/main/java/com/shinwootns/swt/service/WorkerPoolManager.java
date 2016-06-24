package com.shinwootns.swt.service;

import org.apache.log4j.Logger;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.swt.service.syslog.SyslogTask;

public class WorkerPoolManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private static final int WORKER_MIN_COUNT = 16;
	private static final int WORKER_MAX_COUNT = 64;
	private static final int WORKER_LIMIT = 128;
	
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
	
	// Start
	public synchronized void start() {

		_logger.info("ServiceManager... start");

		if (_workerPool.createPool(WORKER_MIN_COUNT, WORKER_MAX_COUNT, WORKER_LIMIT)) {
			_logger.info("Creating syslog-analyzer worker pool ");
		} else {
			_logger.fatal("[FATAL] Failed to create syslog-analyzer worker pool !!!");
			return;
		}
	}
	
	// Add Syslog Task
	public synchronized void addSyslogTask(SyslogEntity syslog) {
		_workerPool.addTask(new SyslogTask(_logger, syslog));
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
