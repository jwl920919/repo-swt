package com.shinwootns.common.stp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SmartThreadPool {

	private final Logger _logger = Logger.getLogger(this.getClass());

	protected int _coreThreadCount = 4;
	protected int _limitThreadCount = 8; // Only when queue is full
	protected int _keepAliveTime = 1000 * 5;

	protected int _maxQueueCount = 0; // 0 = Integer.MAX_VALUE

	protected BlockingQueue<Runnable> _taskQueue = null;
	protected ThreadPoolExecutor _executor = null;

	public boolean createPool(int coreThreadCount, int limitThreadCount, int maxQueueCount) {

		try {
			
			if (maxQueueCount <= 0)
				maxQueueCount = 100;
			
			if (limitThreadCount < 0 || limitThreadCount < coreThreadCount)
			{
				int recommendCount = (Runtime.getRuntime().availableProcessors() * 2) + 2;
				
				limitThreadCount = Math.max(coreThreadCount, recommendCount);
			}

			this._coreThreadCount = coreThreadCount;
			this._limitThreadCount = limitThreadCount;
			this._maxQueueCount = maxQueueCount;

			// Task Queue
			if (_taskQueue == null) {
				_taskQueue = new ArrayBlockingQueue<Runnable>(this._maxQueueCount);
				// taskQueue = new LinkedBlockingQueue<Runnable>();
			}

			// Executor
			if (_executor == null) {
				_executor = new ThreadPoolExecutor(this._coreThreadCount, this._limitThreadCount, _keepAliveTime,
						TimeUnit.MILLISECONDS, _taskQueue);
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		_executor.prestartCoreThread();

		return false;
	}

	public int getActiveCount() {
		if (_executor == null)
			return -1;

		return _executor.getActiveCount();
	}

	public int getPoolSize() {
		if (_executor == null)
			return -1;

		return _executor.getPoolSize();
	}

	public int getTaskQueueSize() {
		if (_taskQueue == null)
			return -1;

		return _taskQueue.size();
	}

	public int getCompletedTaskCount() {
		if (_executor == null)
			return -1;

		return (int) _executor.getCompletedTaskCount();
	}
	
	public PoolStatus getPoolStatus() {

		PoolStatus poolStatus = new PoolStatus();
		poolStatus.setActiveCount(getActiveCount());
		poolStatus.setPoolSize(getPoolSize());
		poolStatus.setTaskQueueSize(getTaskQueueSize());
		poolStatus.setCompletedCount(getCompletedTaskCount());
		
		return poolStatus;
	}

	public synchronized boolean addTask(Runnable task) {

		if (_executor != null) {
			try {
				
				_executor.submit(task);

				return true;
				
			} catch (Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return false;
	}

	public void shutdownAndWait() {
		if (_executor != null) {
			_executor.shutdown();
		}

		if (_taskQueue != null) {
			_taskQueue.clear();
		}
		
		try {

			/*
			if (waitSeconds <= 0)
				waitSeconds = Long.MAX_VALUE;

			executor.awaitTermination(waitSeconds, TimeUnit.SECONDS);
			*/

			if (_executor != null)
				_executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_executor = null;
		_taskQueue = null;
	}
}
