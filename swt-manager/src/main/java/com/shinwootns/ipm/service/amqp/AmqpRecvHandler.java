package com.shinwootns.ipm.service.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.ipm.data.SharedData;

public class AmqpRecvHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public void receiveMessage(String message) {
		
		try
		{
			if (message == null)
				return;
					
			// Parse
			JsonObject jObj = JsonUtils.parseJsonObject(message);
			
			// Put Queue
			if ( SharedData.getInstance().syslogQueue.put(jObj) )
			{
				// Process OK.
				_logger.info(message + "==> OK.");
			}
			else
			{
				_logger.warn(message + "==> failed");
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
}
