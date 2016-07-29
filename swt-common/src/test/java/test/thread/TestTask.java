package test.thread;

/*
import java.util.Random;

public class TestTask implements Runnable {

	private int index = 0;
	private int sleepTime = 0;
	
	public TestTask(int index)
	{
		this.index = index;
		
		this.sleepTime = (new Random()).nextInt(100) + 1;
	}
	
	public void run() {
		
		System.out.println( String.format("[Task#%-2d] Started.", index));		
		
		try
		{
			Thread.sleep(100 * this.sleepTime);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println( String.format("[Task#%-2d] Stopped. (ThreadID:%3d, SleepTime:%5dms)", index, Thread.currentThread().getId(), sleepTime*100));
	}
	
	public int getSleepTime()
	{
		return this.sleepTime;
	}
}
*/