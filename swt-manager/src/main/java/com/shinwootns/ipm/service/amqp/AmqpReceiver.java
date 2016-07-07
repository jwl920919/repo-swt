package com.shinwootns.ipm.service.amqp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.ipm.data.SharedData;

public class AmqpReceiver {
	
	private final Log _logger = LogFactory.getLog(getClass());
	
	public void receiveMessage(String message) {
		
		try
		{
			if (message == null)
				return;
					
			// Json Parse
			JSONObject jObj = JsonUtils.parseJSONObject(message);
			
			// Put Queue
			if ( SharedData.getInstance().syslogRecvQueue.add(jObj) )
			{
				// Process OK.
				_logger.info(message + "==> OK.");
				
			}
			else
			{
				_logger.info(message + "==> Failed... Try retry.");
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
}
