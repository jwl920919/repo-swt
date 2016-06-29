package com.shinwootns.ipm.collector.service.syslog;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.TimeUtils;

public class SyslogTask implements Runnable {
	
	final static String queueName = "ipm.syslog";
	
	private Logger _logger = null;

	private SyslogEntity _syslog;
	
	public SyslogTask(Logger logger, SyslogEntity syslog) {
		this._logger = logger;
		this._syslog = syslog;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {

		if (this._syslog == null)
			return;
		
		try
		{
			JSONObject syslogObj = new JSONObject();
			
			syslogObj.put("host_ip", _syslog.getHost()); 
			syslogObj.put("facility", _syslog.getFacility());
			syslogObj.put("severity", _syslog.getSeverity());
			syslogObj.put("recv_time", _syslog.getRecvTime());
			syslogObj.put("message", _syslog.getData());

			// Send to rabbitmq
			//sender.sendToRabbitmq(syslogObj.toJSONString());
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
	}

}
