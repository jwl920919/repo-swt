package com.shinwootns.ipm.service.rabbitmq;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.ipm.data.SharedData;

public class MQReceiver {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	public void receiveSyslog(String data) {
		
		try {
			// Json Parse
			JSONObject jObj = JsonUtils.parseJSONObject(data);
			
			// Put Queue
			SharedData.getInstance().syslogRecvQueue.add(jObj);
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
	}
	
}
