package com.shinwootns.ipm.collector.service;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.ipm.collector.AppContextProvider;

public class SyslogTask implements Runnable {

	private Logger _logger = null;
	
	private SyslogEntity _syslog;
	
	public SyslogTask(Logger logger, SyslogEntity syslog) {
		this._logger = logger;
		this._syslog = syslog;
	}

	@Override
	public void run() {
		
		// Process Infoblox DHCP
		try
		{
			String rawData = _syslog.getData();

			// Remove Carriage-Return & Line-Feed
			rawData = rawData.replaceAll("\\r\\n|\\r|\\n", " ");
			
			// Trim
			rawData = rawData.trim();
			
			JSONObject jobj = new JSONObject();
			jobj.put("host", _syslog.getHost());
			jobj.put("facility", _syslog.getFacility());
			jobj.put("severity", _syslog.getSeverity());
			jobj.put("recv_time", _syslog.getRecvTime());
			jobj.put("message", rawData);
			
			
			ConfigurableApplicationContext context = AppContextProvider.getInstance().getApplicationContext();
			
			if (context != null) {
				RabbitTemplate rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);

				// Send
				if (rabbitTemplate != null)
					rabbitTemplate.convertAndSend("ipm.syslog", jobj.toJSONString());
			}
		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
	}
}
