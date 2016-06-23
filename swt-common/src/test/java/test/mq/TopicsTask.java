package test.mq;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.RoutingClient;
import com.shinwootns.common.mq.TopicsClient;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.utils.TimeUtils;

public class TopicsTask extends BaseTask {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private String type = "";
	private int index = 0;
	String[] listRoutingKey = null;
	
	public TopicsTask(String type, int index)
	{
		this.type = type;
		this.index = index;
	}

	public TopicsTask(String type, int index, String[] routingKey)
	{
		this.type = type;
		this.index = index;
		this.listRoutingKey = routingKey;
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
			MQManager manager = new MQManager();
			
			//String uri = "amqp://admin:shinwoo123!@192.168.1.81:5672/";
			//if (manager.Connect(uri))
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				System.out.println("Connection OK.");
	
				TopicsClient client = (TopicsClient)manager.createMQClient(MQClientType.Topics);
			
				// Exchange
				String exchangeName = "TEST_TOPICS";
				client.DeclareExchange(exchangeName, false);
				
				while(this.isStopFlag() == false)
				{
					String rountingKey = getRandomRoutingKey();
					String data = rountingKey+":"+TimeUtils.currentTimeString();
					
					System.out.println(String.format("<< Producer : %s", data));
					
					Thread.sleep(1);
					
					client.SendData(exchangeName, rountingKey, data.getBytes());
					
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
		if (this.listRoutingKey == null)
			return;
		
		
		String keyList = "";
		for (String key : this.listRoutingKey)
		{
			if (keyList.length() > 0)
				keyList += ",";
			
			keyList += key;
		}
		
		System.out.println(String.format("Consumer#%d start.", index));
		
		try {
			
			MQManager manager = new MQManager();
			
			//String uri = "amqp://admin:shinwoo123!@192.168.1.81:5672/";
			//if (manager.Connect(uri))
			
			if (manager.Connect("192.168.1.81", 5672, "admin", "shinwoo123!", "/"))
			{
				System.out.println("Connection OK.");
	
				TopicsClient client = (TopicsClient)manager.createMQClient(MQClientType.Topics);

				// Exchange
				String exchangeName = "TEST_TOPICS";
				client.DeclareQueue_TopicsMode(exchangeName, this.listRoutingKey);
				
				while(this.isStopFlag() == false)
				{
					ArrayList<String> listResult = client.ReceiveData(true, 300, 10, "UTF-8");
					
					if (listResult != null)
					{
						for (String data : listResult)
						{
							System.out.println(String.format(">> Consumer#%d [%-30s] : %s", index, keyList ,data));
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
	
	private String getRandomRoutingKey()
	{
		int level = (int)(Math.random() * 10) % 5;
		int type = (int)(Math.random() * 10) % 3;
		
		String sKey = "";
		
		if (level == 0)
			sKey += Level.FATAL.toString();
		else if (level == 2)
			sKey +=Level.ERROR.toString();
		else if (level == 4)
			sKey +=Level.WARN.toString();
		else if (level == 3)
			sKey +=Level.INFO.toString();
		else
			sKey +=Level.DEBUG.toString();
		
		sKey += ".";
		
		if (type == 0)
			sKey += "data";
		else if (type == 1)
			sKey += "log";
		else if (type == 2)
			sKey += "sample";
		
		return sKey;
	}
}
