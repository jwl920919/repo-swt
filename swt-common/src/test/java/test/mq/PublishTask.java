package test.mq;

import java.util.ArrayList;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.WorkQueueClient;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.PublishClient;
import com.shinwootns.common.utils.TimeUtils;

public class PublishTask extends BaseTask {
	
	private String type = "";
	private int index = 0;

	public PublishTask(String type, int index)
	{
		this.type = type;
		this.index = index;
	}

	@Override
	public void run() {
		if (type.toLowerCase().equals("producer")) {
			runProducer();
		} else if (type.toLowerCase().equals("consumer")) {
			runConsumer();
		}
	}
	
	private void runProducer()
	{
		System.out.println(String.format("Producer#%d start.", index));
		
		try {
			MQManager manager = new MQManager(null);
			
			//String uri = "amqp://admin:shinwoo123!@192.168.1.81:5672/";
			//if (manager.Connect(uri))
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				System.out.println("Connection OK.");
	
				PublishClient client = (PublishClient)manager.createMQClient(MQClientType.Publish);
			
				// Exchange
				String exchangeName = "TEST_PUBLISH";
				client.DeclareExchange(exchangeName, false);
				
				while(this.isStopFlag() == false)
				{
					String data = TimeUtils.currentTimeString();
					
					System.out.println(String.format("<< Producer : %s", data));
					
					Thread.sleep(5);
					
					client.SendData(exchangeName, data.getBytes());
					
					Thread.sleep(2000);
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
			
			MQManager manager = new MQManager(null);
			
			//String uri = "amqp://admin:shinwoo123!@192.168.1.81:5672/";
			//if (manager.Connect(uri))
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				System.out.println("Connection OK.");
	
				PublishClient client = (PublishClient)manager.createMQClient(MQClientType.Publish);

				// Exchange
				String exchangeName = "TEST_PUBLISH";
				client.DeclareQueue_PublishMode(exchangeName);
				
				while(this.isStopFlag() == false)
				{
					ArrayList<String> listResult = client.ReceiveData(true, 300, 10, "UTF-8");
					
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
