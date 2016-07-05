package test.mq;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import com.shinwootns.common.stp.SmartThreadPool;

public class TestRabbitMQ {
	
	private final Logger _logger = Logger.getLogger(this.getClass());

	private static ConcurrentLinkedQueue<testBaseTask> listTask = new ConcurrentLinkedQueue<testBaseTask>();
	
	private static SmartThreadPool pool = new SmartThreadPool();
	
	public static void main() throws InterruptedException {
		
		try
		{
			BasicConfigurator.configure();
			
			Scanner sc = new Scanner(System.in);
			
			while(true)
			{
				System.out.println("\n[Command List] : single, workqueue, publish, routing, topics, stop, exit\n");
				System.out.print(">>");
		
				String command = sc.nextLine().toLowerCase();
	
				if (command.toLowerCase().equals("single")
						|| command.toLowerCase().equals("workqueue")
						|| command.toLowerCase().equals("publish")
						|| command.toLowerCase().equals("routing")
						|| command.toLowerCase().equals("topics")
						)
				{
					StartThread(command);
				}
				else if (command.toLowerCase().equals("stop"))
				{
					StopThread();
				}
				else if (command.toLowerCase().equals("exit"))
				{
					StopThread();
					break;
				}
			}
			
			sc.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
			
		System.out.println("Terminated.");
	}
	
	private static void StartThread(String type)
	{
		StopThread();
		
		pool.createPool(10, 10, 0);
		
		if (type.equals("single")) {
			
			testBaseTask producer = new SingleTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
			
			testBaseTask consumer = new SingleTask("consumer", 1);
			consumer.setStopFlag(false);
			listTask.add(consumer);
			pool.addTask(consumer);
		}
		else if (type.equals("workqueue")) {
			
			testBaseTask producer = new WorkqueueTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
		
			for(int i=0; i<3; i++)
			{
				testBaseTask consumer = new WorkqueueTask("consumer", i+1);
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		else if (type.equals("publish")) {
			
			testBaseTask producer = new PublishTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
		
			for(int i=0; i<3; i++)
			{
				testBaseTask consumer = new PublishTask("consumer", i+1);
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		else if (type.equals("routing")) {
			
			testBaseTask producer = new RoutingTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
		

			String keyList = "";
			for(int i=0; i<3; i++)
			{
				if (i==0) 
					keyList = "FATAL";
				else if (i==1) 
					keyList = "ERROR";
				else if (i==2) 
					keyList = "FATAL,ERROR,WARN,INFO,DEBUG";
				
				testBaseTask consumer = new RoutingTask("consumer", i+1, keyList.split(",") );
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		else if (type.equals("topics")) {
		
			testBaseTask producer = new TopicsTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
		

			String keyList = "";
			for(int i=0; i<3; i++)
			{
				if (i==0) 
					keyList = "FATAL.*";
				else if (i==1) 
					keyList = "#.log";
				else if (i==2) 
					keyList = "#.data,#.sample";
				
				testBaseTask consumer = new TopicsTask("consumer", i+1, keyList.split(",") );
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		
		System.out.println("Threads.... Start.");
	}
	
	private static void StopThread()
	{
		if (listTask.size() > 0)
		{
			for(testBaseTask task : listTask) {
				task.setStopFlag(true);
			}
			
			pool.shutdownAndWait();
			listTask.clear();
			
			System.out.println("Threads.... Stop.");
		}
	}
}
