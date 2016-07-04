package com.shinwootns.ipm.collector.service;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinwootns.ipm.collector.AppContextProvider;

public class RabbitMQHandler {
	
	public static void SendData(JSONObject jobj, Logger _logger) {
		
		ConfigurableApplicationContext context = AppContextProvider.getInstance().getApplicationContext();
		
		try
		{
			if (context != null) {
				RabbitTemplate rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);
	
				// Send
				if (rabbitTemplate != null)
				{
					rabbitTemplate.convertAndSend("ipm.syslog", jobj.toJSONString());
				}
				else {
					_logger.error("rabbitTemplate is null.");
				}
			}
			else {
				_logger.error("getApplicationContext()... failed.");
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
}
