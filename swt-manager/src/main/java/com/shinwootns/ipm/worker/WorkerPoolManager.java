package com.shinwootns.ipm.worker;

import org.apache.log4j.Logger;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;

public class WorkerPoolManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private static final int WORKER_MIN_COUNT = 32;
	private static final int WORKER_MAX_COUNT = 32;
	private static final int WORKER_LIMIT = 32;
	
	private static final int SYSLOG_WORKER_COUNT = 3;
	private static final int EVENT_WORKER_COUNT = 2;
	private static final int DEVICE_COLLECT_WORKER_COUNT = 1;
	
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
			
			// Start Syslog Worker
			for(int i=1; i<=SYSLOG_WORKER_COUNT; i++)
			{
				_workerPool.addTask(new SyslogWorker(i));
			}
			
			// Start Event Worker
			for(int i=1; i<=EVENT_WORKER_COUNT; i++)
			{
				_workerPool.addTask(new EventWorker(i));
			}
			
			// Start Collect Device Worker
			for(int i=1; i<=DEVICE_COLLECT_WORKER_COUNT; i++)
			{
				_workerPool.addTask(new DeviceCollectWorker(i));
			}
			
		} else {
			_logger.fatal("[FATAL] Failed to create syslog-analyzer worker pool !!!");
			return;
		}
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
