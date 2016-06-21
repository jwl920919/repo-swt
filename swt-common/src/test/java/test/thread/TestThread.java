package test.thread;

import java.util.Date;

import org.junit.Test;

import com.shinwootns.common.stp.SmartThreadPool;

public class TestThread extends Thread {

	public SmartThreadPool pool = new SmartThreadPool(null);

	int taskIndex = 0;
	
	/*
	protected void finalize() {
		shutdownAndWait();
	}*/

	@Override
	public void run() {

		System.out.println("TestThread - Started.");

		if (pool.createPool(4, 8, 100)) {
			System.out.println("Create threadpool.");
		} else {
			System.out.println("Failed to create pool.");
			return;
		}

		while (this.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("TestThread - Stopped.");
	}

	public void AddTask(int count) {
		
		for (int i = 0; i < count; i++) {
			try {
				
				int index = ++taskIndex;
				
				while(true)
				{
					boolean result =  pool.addTask(new TestTask(index));
					if (result)
						break;
					
					Thread.sleep(1000);
					
					System.out.println( String.format("Add task #%d - Delayed.", index));
				}
				
				System.out.println( String.format("Add task #%d.", index));
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void DisplayStatus() {
		int activeCount = pool.getActiveCount();
		int poolSize = pool.getPoolSize();
		int taskQueueSize = pool.getTaskQueueSize();
		int completedCount = pool.getCompletedTaskCount();

		System.out.println("ACTIVE:" + activeCount + " POOL-SIZE:" + poolSize + " TASK_QUEUE:" + taskQueueSize + " COMPLETED:" + completedCount);
	}
	
	public void shutdownAndWait()
	{
		pool.shutdownAndWait();
		
		System.out.println("ShutdownAndWait.");
	}
}
