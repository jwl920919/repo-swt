package com.shinwootns.ipm.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.ipm.collector.worker.MasterJobWoker;
import com.shinwootns.ipm.collector.worker.SchedulerWorker;
import com.shinwootns.ipm.collector.worker.SyslogWorker;

public class WorkerManager {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	// Worker Count
	private static final int SYSLOG_WORKER_COUNT = 3;
	
	// Task Count
	private static final int TASK_MIN_COUNT = 32;
	private static final int TASK_MAX_COUNT = 32;
	private static final int TASK_LIMIT_COUNT = 32;
	
	// Task Pool
	private SmartThreadPool _taskPool = new SmartThreadPool();

	Thread _scheduler = null;									// Scheduler Thread
	Thread _masterJobThread = null;								// Master Job Thread
	Thread[] _syslogWorker = new Thread[SYSLOG_WORKER_COUNT];	// Syslog Thread
	
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

	//region [FUNC] start
	public void start() {

		synchronized(this) 
		{
			_logger.info("WorkerManager... start");
			
			// Start Scheduler
			if (_scheduler == null) {
				_scheduler = new Thread(new SchedulerWorker(), "SchedulerWorker");
				_scheduler.start();
			}
			
			// Start Syslog Worker
			for(int i=0; i<SYSLOG_WORKER_COUNT; i++) {
				if (_syslogWorker[i] == null) {
					_syslogWorker[i] = new Thread(new SyslogWorker(i, _logger)
								, (new StringBuilder()).append("SyslogWorker#").append(i).toString());
					_syslogWorker[i].start();
				}
			}
			
			// Task Pool
			if (_taskPool.createPool(TASK_MIN_COUNT, TASK_MAX_COUNT, TASK_LIMIT_COUNT)) {
				_logger.info("Create task pool... ok");
			}
			else {
				_logger.error("[FATAL] Failed to create task-pool !!!");
			}
		}
	}
	//endregion
	
	//region [FUNC] stop
	public void stop()
	{
		stopMasterJobWorker();
		
		synchronized(this) 
		{
			// Stop Scheduller
			try {
				if (_scheduler != null)
					_scheduler.join();
			} catch (InterruptedException e) {
			}finally {
				_scheduler = null;
			}
			
			// Stop Syslog Worker
			for(int i=1; i<=SYSLOG_WORKER_COUNT; i++) {
				try {
					if (_syslogWorker[i] != null)
						_syslogWorker[i].join();
					
				} catch (InterruptedException e) {
				} finally {
					_syslogWorker[i] = null;
				}
			}
			
			_taskPool.shutdownAndWait();
			
			_logger.info("ServiceManager....... stop");
		}
	}
	//endregion
	
	//region [FUNC] AddTask
	public void AddTask(Runnable task) {
		_taskPool.addTask(task);
	}
	//endregion
	
	//region [FUNC] GetTaskPoolStatus
	public PoolStatus GetTaskPoolStatus() {
		return _taskPool.getPoolStatus();
	}
	//endregion

	//region [FUNC] start / stop MasterJobWorker
	public void startMasterJobWorker() {
		
		synchronized(this) 
		{
			if (_masterJobThread == null) {

				_logger.info("Call start MasterJobWorker");
				
				_masterJobThread = new Thread(new MasterJobWoker(), "MasterJobWoker");
				_masterJobThread.start();
			}
		}
	}
	
	public void stopMasterJobWorker() {
		
		synchronized(this) 
		{
			if (_masterJobThread != null && _masterJobThread.isInterrupted() == false) {
				
				_logger.info("Call stop MasterJobWorker");
				
				_masterJobThread.interrupt();
				_masterJobThread = null;
			}
		}
	}
	
	public boolean isRunnigMasterJobWorker() {
		
		synchronized(this) 
		{
			if ( _masterJobThread != null && _masterJobThread.isInterrupted() == false)
				return true;
		}
		
		return false;
	}
	//endregion
}
