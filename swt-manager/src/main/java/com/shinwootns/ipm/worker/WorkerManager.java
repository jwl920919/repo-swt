package com.shinwootns.ipm.worker;

import org.apache.log4j.Logger;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.ipm.worker.persist.DeviceCollectWorker;
import com.shinwootns.ipm.worker.persist.EventWorker;
import com.shinwootns.ipm.worker.persist.SyslogWorker;

public class WorkerManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	// Worker
	private static final int SYSLOG_WORKER_COUNT = 3;
	private static final int EVENT_WORKER_COUNT = 2;
	private static final int DEVICE_COLLECT_WORKER_COUNT = 1;
	
	private SmartThreadPool _workerPool = new SmartThreadPool();
	
	
	// Task
	private static final int TASK_MIN_COUNT = 32;
	private static final int TASK_MAX_COUNT = 32;
	private static final int TASK_LIMIT_COUNT = 32;
	
	private SmartThreadPool _taskPool = new SmartThreadPool();
	
	
	// Singleton
	private static WorkerManager _instance = null;
	private WorkerManager() {}
	public static synchronized WorkerManager getInstance() {

		if (_instance == null) {
			_instance = new WorkerManager();
		}
		return _instance;
	}

	
	// Start
	public synchronized void start() {

		_logger.info("ThreadManager... start");
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n========================================================================\n");
		sb.append(String.format("%-30s = %d\n", "SYSLOG_WORKER_COUNT", SYSLOG_WORKER_COUNT));
		sb.append(String.format("%-30s = %d\n", "EVENT_WORKER_COUNT", EVENT_WORKER_COUNT));
		sb.append(String.format("%-30s = %d\n", "DEVICE_COLLECT_WORKER_COUNT", DEVICE_COLLECT_WORKER_COUNT));
		sb.append("========================================================================\n");
		sb.append(String.format("%-20s = %d\n", "TASK_MIN_COUNT", TASK_MIN_COUNT));
		sb.append(String.format("%-20s = %d\n", "TASK_MAX_COUNT", TASK_MAX_COUNT));
		sb.append(String.format("%-20s = %d\n", "TASK_LIMIT_COUNT", TASK_LIMIT_COUNT));
		sb.append("========================================================================\n");
		
		_logger.info(sb.toString());
		
		// Worker Pool
		int totalCount = SYSLOG_WORKER_COUNT + EVENT_WORKER_COUNT + DEVICE_COLLECT_WORKER_COUNT;
		
		if (_workerPool.createPool(totalCount, totalCount, totalCount)) {

			_logger.info("Create worker pool... ok");
			
			// SyslogWorker
			for(int i=1; i<=SYSLOG_WORKER_COUNT; i++)
				_workerPool.addTask(new SyslogWorker(i));
			
			// EventWorker
			for(int i=1; i<=EVENT_WORKER_COUNT; i++)
				_workerPool.addTask(new EventWorker(i));
			
			// DeviceCollectWorker
			for(int i=1; i<=DEVICE_COLLECT_WORKER_COUNT; i++)
				_workerPool.addTask(new DeviceCollectWorker(i));
			
		} else {
			_logger.fatal("[FATAL] Failed to create worker-pool !!!");
			return;
		}
		
		
		// Task Pool
		if (_taskPool.createPool(TASK_MIN_COUNT, TASK_MAX_COUNT, TASK_LIMIT_COUNT)) {
			_logger.info("Create task pool... ok");
		}
		else {
			_logger.fatal("[FATAL] Failed to create task-pool !!!");
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
	
	public void AddTask(BaseWorker task) {
		_taskPool.addTask(task);
	}
}
