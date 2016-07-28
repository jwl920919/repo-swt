package com.shinwootns.ipm.worker;

import org.apache.log4j.Logger;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.ipm.worker.persist.DeviceCollectWorker;
import com.shinwootns.ipm.worker.persist.EventWorker;
import com.shinwootns.ipm.worker.persist.SchedulerWorker;
import com.shinwootns.ipm.worker.persist.SyslogWorker;

public class WorkerManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	// Worker Count
	private static final int SCHEDULER_WORKER_COUNT = 1;
	private static final int SYSLOG_WORKER_COUNT = 3;
	private static final int EVENT_WORKER_COUNT = 2;
	private static final int DEVICE_COLLECT_WORKER_COUNT = 1;
	
	// Task Count
	private static final int TASK_MIN_COUNT = 32;
	private static final int TASK_MAX_COUNT = 32;
	private static final int TASK_LIMIT_COUNT = 32;

	private SmartThreadPool _workerPool = new SmartThreadPool();
	private SmartThreadPool _taskPool = new SmartThreadPool();
	
	//region Singleton
	private static WorkerManager _instance = null;
	private WorkerManager() {}
	public static synchronized WorkerManager getInstance() {

		if (_instance == null) {
			_instance = new WorkerManager();
		}
		return _instance;
	}
	//endregion
	
	//region [FUNC] start / stop
	public synchronized void start() {

		_logger.info("ThreadManager... start");
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------------------------------------\n");
		sb.append(String.format("%-30s = %d\n", "SCHEDULER_WORKER_COUNT", SCHEDULER_WORKER_COUNT));
		sb.append(String.format("%-30s = %d\n", "SYSLOG_WORKER_COUNT", SYSLOG_WORKER_COUNT));
		sb.append(String.format("%-30s = %d\n", "EVENT_WORKER_COUNT", EVENT_WORKER_COUNT));
		sb.append(String.format("%-30s = %d\n", "DEVICE_COLLECT_WORKER_COUNT", DEVICE_COLLECT_WORKER_COUNT));
		sb.append("------------------------------------------------------------\n");
		sb.append(String.format("%-20s = %d\n", "TASK_MIN_COUNT", TASK_MIN_COUNT));
		sb.append(String.format("%-20s = %d\n", "TASK_MAX_COUNT", TASK_MAX_COUNT));
		sb.append(String.format("%-20s = %d\n", "TASK_LIMIT_COUNT", TASK_LIMIT_COUNT));
		sb.append("------------------------------------------------------------");
		
		_logger.info(sb.toString());
		
		// Worker Pool
		int totalCount = SCHEDULER_WORKER_COUNT + SYSLOG_WORKER_COUNT + EVENT_WORKER_COUNT + DEVICE_COLLECT_WORKER_COUNT;
		
		if (_workerPool.createPool(totalCount, totalCount, totalCount)) {

			_logger.info("Create worker pool... ok");
			
			// Scheduler Worker
			_workerPool.addTask(new SchedulerWorker());
			
			// Syslog Worker
			for(int i=1; i<=SYSLOG_WORKER_COUNT; i++)
				_workerPool.addTask(new SyslogWorker(i));
			
			// Event Worker
			for(int i=1; i<=EVENT_WORKER_COUNT; i++)
				_workerPool.addTask(new EventWorker(i));
			
			// Device CollectWorker
			//for(int i=1; i<=DEVICE_COLLECT_WORKER_COUNT; i++)
			//	_workerPool.addTask(new DeviceCollectWorker(i));
			
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
	
	public synchronized void stop()
	{
		_workerPool.shutdownAndWait();
		
		_taskPool.shutdownAndWait();
		
		_logger.info("ServiceManager....... stop");
	}
	//endregion
	
	//region [FUNC] Pool Status
	public synchronized PoolStatus GetWorkPoolStatus() {
		return _workerPool.getPoolStatus();
	}
	
	public synchronized PoolStatus GetTaskPoolStatus() {
		return _taskPool.getPoolStatus();
	}
	//endregion
	
	//region [FUNC] Add Task
	public void AddTask(BaseWorker task) {
		_taskPool.addTask(task);
	}
	//endregion
}
