package test.mq;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.shinwootns.common.stp.SmartThreadPool;

public class TestRabbitMQ {
	
	private static final Logger logger = Logger.getLogger(TestRabbitMQ.class);

	private static ConcurrentLinkedQueue<BaseTask> listTask = new ConcurrentLinkedQueue<BaseTask>();
	
	private static SmartThreadPool pool = new SmartThreadPool(logger);
	
	public static void main(String[] args) throws InterruptedException {
		
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
		
		System.out.println("Terminated.");
	}
	
	private static void StartThread(String type)
	{
		StopThread();
		
		pool.createPool(10, 10, 0);
		
		if (type.equals("single")) {
			
			BaseTask producer = new SingleTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
			
			BaseTask consumer = new SingleTask("consumer", 1);
			consumer.setStopFlag(false);
			listTask.add(consumer);
			pool.addTask(consumer);
		}
		else if (type.equals("workqueue")) {
			
			BaseTask producer = new WorkqueueTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
		
			for(int i=0; i<3; i++)
			{
				BaseTask consumer = new WorkqueueTask("consumer", i+1);
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		else if (type.equals("publish")) {
			
			BaseTask producer = new PublishTask("producer", 1);
			producer.setStopFlag(false);
			listTask.add(producer);
			pool.addTask(producer);
		
			for(int i=0; i<3; i++)
			{
				BaseTask consumer = new PublishTask("consumer", i+1);
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		else if (type.equals("routing")) {
			
			BaseTask producer = new RoutingTask("producer", 1);
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
				
				BaseTask consumer = new RoutingTask("consumer", i+1, keyList.split(",") );
				consumer.setStopFlag(false);
				listTask.add(consumer);
				pool.addTask(consumer);
			}
		}
		else if (type.equals("topics")) {
		
			BaseTask producer = new TopicsTask("producer", 1);
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
				
				BaseTask consumer = new TopicsTask("consumer", i+1, keyList.split(",") );
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
			for(BaseTask task : listTask) {
				task.setStopFlag(true);
			}
			
			pool.shutdownAndWait();
			listTask.clear();
			
			System.out.println("Threads.... Stop.");
		}
	}
}
