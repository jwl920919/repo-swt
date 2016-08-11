package com.shinwootns.ipm.insight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;
import com.shinwootns.ipm.insight.worker.MasterJobWoker;
import com.shinwootns.ipm.insight.worker.NetworkDeviceCollctor;
import com.shinwootns.ipm.insight.worker.SchedulerWorker;
import com.shinwootns.ipm.insight.worker.SyslogPutter;

public class WorkerManager {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	// Worker Count
	private static final int SYSLOG_PUTTER_COUNT = 2;
	
	// Task Count
	private static final int TASK_MIN_COUNT = 4;
	private static final int TASK_MAX_COUNT = 4;
	private static final int TASK_LIMIT_COUNT = 4;
	
	// Task Pool
	private SmartThreadPool _taskPool = new SmartThreadPool();

	Thread _scheduler = null;									// Scheduler Thread
	Thread _masterJobThread = null;								// Master Job Thread
	Thread _networkDeviceCollector = null;						// Network Device Collctor
	
	Thread[] _syslogWorker = new Thread[SYSLOG_PUTTER_COUNT];	// Syslog Thread
	
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
			
			if (_networkDeviceCollector == null) {
				_networkDeviceCollector = new Thread(new NetworkDeviceCollctor(), "NetworkDeviceCollctor");
				_networkDeviceCollector.start();
			}
			
			// Start Syslog Putter
			for(int i=0; i<SYSLOG_PUTTER_COUNT; i++) {
				if (_syslogWorker[i] == null) {
					_syslogWorker[i] = new Thread(new SyslogPutter(i)
								, (new StringBuilder()).append("SyslogPutter#").append(i).toString());
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
		synchronized(this) 
		{
			if (_networkDeviceCollector != null && _networkDeviceCollector.isAlive()) {
				try {
					_networkDeviceCollector.interrupt();
					_networkDeviceCollector.join();
				} catch(Exception ex) {
				} finally {
					_networkDeviceCollector = null;
				}
				
			}
			
			if (_masterJobThread != null && _masterJobThread.isAlive()) {
				try {
					_masterJobThread.interrupt();
					_masterJobThread.join();
					
				} catch (InterruptedException e) {
				} finally {
					_masterJobThread = null;
				}
			}
			
			// Stop Scheduller
			try {
				if (_scheduler != null && _scheduler.isAlive()) {
					_scheduler.interrupt();
					_scheduler.join();
				}
			} catch (InterruptedException e) {
			}finally {
				_scheduler = null;
			}
			
			// Stop Syslog Worker
			for(int i=0; i<SYSLOG_PUTTER_COUNT; i++) {
				try {
					if (_syslogWorker[i] != null && _syslogWorker[i].isAlive()) {
						_syslogWorker[i].interrupt();
						_syslogWorker[i].join();
					}
					
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
