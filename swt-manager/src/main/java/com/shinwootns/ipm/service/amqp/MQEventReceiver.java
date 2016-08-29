package com.shinwootns.ipm.service.amqp;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.EventData;
import com.shinwootns.data.key.QueueNames;
import com.shinwootns.ipm.data.SharedData;

public class MQEventReceiver implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private RabbitMQHandler _handler = null;
	
	public MQEventReceiver(RabbitMQHandler handler) {
		this._handler = handler;
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run() {
		
		WorkQueueClient client = null;
		
		while(!Thread.currentThread().isInterrupted()) {

			try
			{
				if (client == null) {
					client = this._handler.createClient();
					client.DeclareQueue_WorkQueueMode(QueueNames.EVENT_QUEUE_NAME);
				}
				
				if (client != null) {
					ArrayList<String> listResult = client.ReceiveData(QueueNames.EVENT_QUEUE_NAME, true, 500, 10, "UTF-8");
					
					if (listResult != null)
					{
						for (String data : listResult)
						{
							//System.out.println(String.format(">> Event : %s", data));
							EventData event = (EventData)JsonUtils.deserialize(data, EventData.class);
							
							SharedData.getInstance().eventQueue.put(event);
						}
						
						listResult.clear();
						listResult = null;
					}
				}
				else {
					Thread.sleep(500);
				}
				
			} catch(InterruptedException ex) {
				break;
			} catch (Exception e) {
				
				if (client != null) {
					client.CloseChannel();
					client = null;
				}
			}
		}
		
		if (client != null) {
			client.CloseChannel();
			client = null;
		}
	}
}
