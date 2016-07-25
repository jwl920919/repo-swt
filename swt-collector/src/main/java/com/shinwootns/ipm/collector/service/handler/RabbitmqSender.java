package com.shinwootns.ipm.collector.service.handler;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.google.gson.JsonObject;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;

public class RabbitmqSender {
	
	public static void SendData(JsonObject jobj, Logger _logger) {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		RabbitTemplate rabbitTemplate = SpringBeanProvider.getInstance().getRabbitTemplate();
		
		if (appProperty == null || rabbitTemplate == null)
			return;
		
		try
		{
			// Debug - insert_syslog_enable
			if (appProperty != null && appProperty.insert_syslog_enable == false)
				return;
			
			// Send
			if (rabbitTemplate != null)
			{
				rabbitTemplate.convertAndSend("ipm.syslog", jobj.getAsJsonObject());
			}
			else {
				_logger.error("rabbitTemplate is null.");
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
	}
}
