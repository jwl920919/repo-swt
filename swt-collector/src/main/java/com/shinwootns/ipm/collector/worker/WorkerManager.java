package com.shinwootns.ipm.collector.worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.worker.persist.SchedulerWorker;
import com.shinwootns.ipm.collector.worker.persist.SyslogWorker;

public class WorkerManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private static final int SCHEDULER_WORKER_COUNT = 1;
	private static final int SYSLOG_WORKER_COUNT = 3;
	
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

	// Worker Pool
	private SmartThreadPool _workerPool = new SmartThreadPool();
	
	
	// Start
	public synchronized void start() {

		_logger.info("ServiceManager... start");
		
		// Worker Pool
		int totalCount = SCHEDULER_WORKER_COUNT + SYSLOG_WORKER_COUNT;
				
		if (_workerPool.createPool(totalCount, totalCount, totalCount)) {
			
			// Scheduler Worker
			_workerPool.addTask(new SchedulerWorker());
			
			/*
			// Start Producer Worker
			for(int i=1; i<=SYSLOG_WORKER_COUNT; i++)
			{
				_workerPool.addTask(new SyslogWorker(i, _logger));
			}
			*/
			
		} else {
			_logger.fatal("[FATAL] Failed to create syslog-analyzer worker pool !!!");
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
	public synchronized PoolStatus GetWorkPoolStatus() {
		return _workerPool.getPoolStatus();
	}
	
	public synchronized PoolStatus GetTaskPoolStatus() {
		return _taskPool.getPoolStatus();
	}
	
	public synchronized void stop()
	{
		_workerPool.shutdownAndWait();
		
		_taskPool.shutdownAndWait();
		
		_logger.info("ServiceManager....... stop");
	}
	
	public void AddTask(BaseWorker task) {
		_taskPool.addTask(task);
	}
}
