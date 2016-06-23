package com.shinwootns.swt.service;

import org.apache.log4j.Logger;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.stp.PoolStatus;
import com.shinwootns.common.stp.SmartThreadPool;

public class SyslogAnalyzer {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private static final int WORKER_MIN_COUNT = 4;
	private static final int WORKER_MAX_COUNT = 8;
	private static final int WORKER_LIMIT = 32;
	
	// Singleton
	private static SyslogAnalyzer _instance = null;
	private SyslogAnalyzer() {}
	public static synchronized SyslogAnalyzer getInstance() {

		if (_instance == null) {
			_instance = new SyslogAnalyzer();
		}
		return _instance;
	}

	// Worker Pool
	private SmartThreadPool _workerPool = new SmartThreadPool();
	
	// Start
	public void start() {

		_logger.info("SyslogAnalyzer... start");

		if (_workerPool.createPool(WORKER_MIN_COUNT, WORKER_MAX_COUNT, WORKER_LIMIT)) {
			_logger.info("Creating syslog-analyzer worker pool ");
		} else {
			_logger.fatal("[FATAL] Failed to create syslog-analyzer worker pool !!!");
			return;
		}
	}
	
	public void addTask(SyslogEntity syslog) {
		_workerPool.addTask(new SyslogTask(syslog));
	}
	
	public PoolStatus GetPoolStatus() {
		return _workerPool.getPoolStatus();
	}
	
	public void stop()
	{
		_workerPool.shutdownAndWait();
		
		_logger.info("SyslogAnalyzer....... stop");
	}
}
