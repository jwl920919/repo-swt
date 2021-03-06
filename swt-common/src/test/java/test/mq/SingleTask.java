package test.mq;

/*
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.*;
import com.shinwootns.common.utils.TimeUtils;

public class SingleTask extends testBaseTask {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private String type = "";
	private int index = 0;
	
	public SingleTask(String type, int index)
	{
		this.type = type;
		this.index = index;
	}
	
	@Override
	public void run()
	{
		if (type.toLowerCase().equals("producer"))
		{
			runProducer();
		}
		else if (type.toLowerCase().equals("consumer"))
		{
			runConsumer();
		}
	}
	
	private void runProducer()
	{
		System.out.println(String.format("Producer#%d start.", index));
		
		try {
			MQManager manager = new MQManager();
			
			//String uri = "amqp://admin:shinwoo123!@192.168.1.81:5672/";
			//if (manager.Connect(uri))
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				System.out.println("Connection OK.");
	
				SingleClient client = (SingleClient)manager.createMQClient(MQClientType.Single);
			
				String queueName = "TEST_SINGLE";
				
				client.DelcareQueue_SingleMode(queueName);
				
				while(this.isStopFlag() == false)
				{
					String data = TimeUtils.currentTimeString();
					
					System.out.println(String.format("<< Producer : %s", data));
					
					Thread.sleep(10);
					
					client.SendData(queueName, data.getBytes());
					
					Thread.sleep(1000);
				}
				
				client.CloseChannel();
			}
			
			manager.Close();
		}
		catch(InterruptedException ex) { 
		}
		
		System.out.println(String.format("Producer#%d end.", index));
	}
	
	private void runConsumer()
	{
		System.out.println(String.format("Consumer#%d start.", index));
		
		try {
			
			MQManager manager = new MQManager();
			
			//String uri = "amqp://admin:shinwoo123!@192.168.1.81:5672/";
			//if (manager.Connect(uri))
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				System.out.println("Connection OK.");
	
				SingleClient client = (SingleClient)manager.createMQClient(MQClientType.Single);
				
				String queueName = "TEST_SINGLE";
			
				boolean autoDeleteQueue = false;
				client.DelcareQueue_SingleMode(queueName);
				
				while(this.isStopFlag() == false)
				{
					ArrayList<String> listResult = client.ReceiveData(queueName, true, 500, 10, "UTF-8");
					
					if (listResult != null)
					{
						for (String data : listResult)
						{
							System.out.println(String.format(">> Consumer#%d : %s", index, data));
						}
						
						listResult.clear();
						listResult = null;
					}
				}
				
				client.CloseChannel();
			}
			
			manager.Close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println(String.format("Consumer#%d end.", index));
	}
}
*/