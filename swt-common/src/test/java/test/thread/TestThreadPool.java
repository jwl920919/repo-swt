package test.thread;

import java.util.Date;
import java.util.Scanner;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*

import com.shinwootns.common.stp.SmartThreadPool;

public class TestThreadPool {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public static void main() {
		
		System.out.println("Start.");
		
		TestThread thread = new TestThread();
		thread.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		thread.AddTask(20);
		
		try {
			
			for(int i=0; i<30; i++)
			{
				thread.DisplayStatus();
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Test end.");
		
		thread.stop();
		
		thread.shutdownAndWait();
		
		System.out.println("Terminated.");
	}
}

*/