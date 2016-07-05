package com.shinwootns.ipm.service.rabbitmq;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.ipm.service.BaseWorker;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.ApplicationProperties;
import com.shinwootns.ipm.data.SharedData;

public class RabbitmqWorker extends BaseWorker {
	
	private final Log _logger = LogFactory.getLog(getClass());

	private final static String queueName = "ipm.syslog";
	private MQManager manager = new MQManager();
	private WorkQueueClient client = null;
	private ApplicationProperties appProperties;
	
	Queue<String> buffer = new ConcurrentLinkedQueue<String>();
	
	private int _index = 0;
	
	public RabbitmqWorker(int index) {
		this._index = _index;
	}

	@Override
	public void run() {

		this.appProperties = SpringBeanProvider.getInstance().getApplicationProperties();
		
		if (appProperties == null)
		{
			_logger.error("ApplicationProperties appProperties is null");
			return;
		}
		
		
		open();
		
		try {
			
			while(this.isStopFlag() == false)
			{
				ArrayList<String> listResult = client.ReceiveData(queueName, true, 500, 10, "UTF-8");

				// Push to buffer
				if (listResult != null && listResult.size() > 0) {
					buffer.addAll(listResult);
					listResult.clear();
				}
				listResult = null;

				// Pop data in buffer
				while(buffer.isEmpty() == false)
				{
					try
					{
						String data = buffer.remove();
						
						if (data == null)
							continue;
								
						// Json Parse
						JSONObject jObj = JsonUtils.parseJSONObject(data);
						
						// Put Queue
						if ( SharedData.getInstance().syslogRecvQueue.add(jObj) )
						{
							// Process OK.
							_logger.info(data + "==> OK.");
							
						}
						else
						{
							_logger.info(data + "==> Failed... Try retry.");
							
							// If processing fails, move back to buffer.
							buffer.add(data);
						}
					}
					catch(Exception ex) {
						_logger.error(ex.getMessage(), ex);
					}
				}
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		close();
	}
	
	public boolean open() {
		try
		{
			if (manager.Connect(
					appProperties.getRabbitmqHost(), 
					appProperties.getRabbitmqPort(), 
					appProperties.getRabbitmqUsername(), 
					appProperties.getRabbitmqPassword(),
					appProperties.getRabbitmqVhost())
				)
			{
				// Create Client
				this.client = (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
				
				// Declare Queue
				//this.client.DeclareQueue_WorkQueueMode(queueName);
				
				return true;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public void close() {
		try
		{
			if ( client != null )
				client.CloseChannel();
			
			manager.Close();
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		finally {
			client = null;
		}
	}
	
	public boolean checkConnection() {
		try
		{
			if ( client != null )
				return client.checkConnection();
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
}