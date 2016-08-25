package com.shinwootns.ipm.service.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.mq.MQManager;
import com.shinwootns.common.mq.MQManager.MQClientType;
import com.shinwootns.common.mq.client.WorkQueueClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;

public class RabbitMQHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private MQManager manager = null;;
	
	private Thread receiver = null;
	
	private String _host;
	private int _port;
	private String _userid;
	private String _password;
	private String vHost;
	private int _handshake_timeout = 5000;
	
	//region Singleton
	private static RabbitMQHandler _instance = null;
	private RabbitMQHandler() {}
	public static synchronized RabbitMQHandler getInstance() {

		if (_instance == null) {
			_instance = new RabbitMQHandler();
		}
		return _instance;
	}
	//endregion
	
	//region [public] Connect / Close
	public boolean connect() throws Exception
	{
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return false;
		
		synchronized(this)
		{
			this._host = appProperty.rabbitmqHost; 
			this._port = appProperty.rabbitmqPort; 
			this._userid = appProperty.rabbitmqUsername; 
			this._password = CryptoUtils.Decode_AES128(appProperty.rabbitmqPassword); 
			this.vHost = appProperty.rabbitmqVHost;
			
			_close();
			
			if ( _connect() ) {
				_logger.info(
						(new StringBuilder())
						.append("Successed connect rabbitmq... amqp:\\").append(this._host).append(":").append(this._port)
						.toString()
				);
				return true;
			}
			else {
				_logger.error(
						(new StringBuilder())
						.append("Failed connect rabbitmq... amqp:\\").append(this._host).append(":").append(this._port)
						.toString()
				);
				
				return false;
			}
		}
	}
	
	public void close()
	{
		synchronized(this)
		{
			_close();
		}
	}
	//endregion

	//region [public] Create Client
	public WorkQueueClient createClient() {
		
		if (manager == null)
			return null;
		
		return (WorkQueueClient)manager.createMQClient(MQClientType.WorkQueue);
	}
	//endregion
	
	//region _connect / _close
	private boolean _connect() {
		
		if (manager == null) {
			manager = new MQManager(this._host, this._port, this._userid, this._password, this.vHost, this._handshake_timeout);
		}
	
		if ( manager.Connect() ) {
			
			_logger.info((new StringBuilder())
					.append("Succeed connect rabbitmq... amqp:\\").append(this._host).append(":").append(this._port)
					.toString());
			
			startReceiveEvent();
			
			return true;
		}
		
		return false;
	}
	
	private void _close() {
		
		stopReceiveEvent();
		
		if (manager != null) {
			manager.Close();
			manager = null;
		}
	}
	//endregion
	
	//region Start Receive Event
	private boolean startReceiveEvent() {

		stopReceiveEvent();
		
		if (receiver == null) {
			receiver = new Thread(new MQEventReceiver(this));
			receiver.start();
			return true;
		}
		
		return true;
	}
	//endregion
	
	//region Stop Receive Event
	private void stopReceiveEvent() {
		
		if (receiver != null) {
			try {
				receiver.interrupt();
				receiver.join();
			} catch(Exception ex) {
			} finally {
				receiver = null;
			}
		}
	}
	//endregion
}
