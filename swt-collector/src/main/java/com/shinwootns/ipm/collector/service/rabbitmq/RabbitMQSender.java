package com.shinwootns.ipm.collector.service.rabbitmq;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinwootns.ipm.collector.ApplicationProperty;
import com.shinwootns.ipm.collector.SpringBeanProvider;

public class RabbitMQSender {
	
	public static void SendData(JSONObject jobj, Logger _logger) {
		
		RabbitTemplate rabbitTemplate = SpringBeanProvider.getInstance().getRabbitTemplate();
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperties();
		
		try
		{
			// Debug - insert_syslog_enable
			if (appProperty != null && appProperty.isInsert_syslog_enable() == false)
				return;
			
			// Send
			if (rabbitTemplate != null)
			{
				rabbitTemplate.convertAndSend("ipm.syslog", jobj.toJSONString());
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
