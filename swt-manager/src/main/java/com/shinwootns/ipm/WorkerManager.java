package com.shinwootns.ipm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.ipm.worker.EventWorker;
import com.shinwootns.ipm.worker.MasterJobWoker;
import com.shinwootns.ipm.worker.SchedulerWorker;

public class WorkerManager {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	// Worker Count
	private static final int EVENT_WORKER_COUNT = 2;
	
	// Task Count
	private static final int TASK_MIN_COUNT = 32;
	private static final int TASK_MAX_COUNT = 32;
	private static final int TASK_LIMIT_COUNT = 32;
	
	Thread _scheduler = null;									// Scheduler Thread
	Thread _masterJobThread = null;								// Master Job Thread
	Thread[] _eventWorker = new Thread[EVENT_WORKER_COUNT];		// Event Thread

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
	public void start() {

		synchronized(this)
		{
			_logger.info("WorkerManager... start");
			
			// Start Scheduler
			if (_scheduler == null) {
				_scheduler = new Thread(new SchedulerWorker());
				_scheduler.setName("SchedulerWorker");
				_scheduler.start();
			}
			
			// Start EventWorker
			for(int i=0; i<EVENT_WORKER_COUNT; i++) {
				if (_eventWorker[i] == null) {
					_eventWorker[i] = new Thread(new EventWorker(i, _logger));
					_eventWorker[i].setName((new StringBuilder()).append("_eventWorker#").append(i).toString());
					_eventWorker[i].start();
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
	
	public void stop()
	{
		synchronized(this) 
		{
			if (_masterJobThread != null) {
				try {
					_masterJobThread.interrupt();
					_masterJobThread.join();
					
				} catch (InterruptedException e) {
				} finally {
					_masterJobThread = null;
				}
			}
			
			try {
				if (_scheduler != null) {
					_scheduler.interrupt();
					_scheduler.join();
				}
			} catch (InterruptedException e) {
			}finally {
				_scheduler = null;
			}

			// Stop EventWorker
			for(int i=0; i<EVENT_WORKER_COUNT; i++) {
				try {
					if (_eventWorker[i] != null) {
						_eventWorker[i].interrupt();
						_eventWorker[i].join();
					}
				} catch (InterruptedException e) {
				} finally {
					_eventWorker[i] = null;
				}
			}

			_taskPool.shutdownAndWait();
			
			_logger.info("WorkerManager....... stop");
		}
	}
	//endregion
	
	//region [FUNC] Pool Status
	public PoolStatus GetTaskPoolStatus() {
		return _taskPool.getPoolStatus();
	}
	//endregion
	
	//region [FUNC] Add Task
	public void AddTask(Runnable task) {
		_taskPool.addTask(task);
	}
	//endregion
	
	//region [FUNC] start / stop MasterJobWorker
	public void startMasterJobWorker() {
		
		synchronized(this) 
		{
			if (_masterJobThread == null) {

				_logger.info("Call start MasterJobWorker");
				
				_masterJobThread = new Thread(new MasterJobWoker());
				_masterJobThread.setName("MasterJobWoker");
				_masterJobThread.start();
			}
		}
	}
	
	public void stopMasterJobWorker() {
		
		synchronized(this) 
		{
			if (_masterJobThread != null) {
				
				_logger.info("Call stop MasterJobWorker");
				
				try {
					
					_masterJobThread.interrupt();
					_masterJobThread.join();
					
				} catch (InterruptedException e) {
				} finally {
					_masterJobThread = null;
				}
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

	//region [FUNC] Terminate application
	public void TerminateApplication() {
		
		WorkerManager.getInstance().stop();
		ConfigurableApplicationContext appContext = (ConfigurableApplicationContext)SpringBeanProvider.getInstance().getApplicationContext();
		appContext.close();
		
		_logger.info("======================= Terminate Application ========================");
	}
	//endregion
}
