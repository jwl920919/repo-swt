package com.shinwootns.ipm.collector.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.LogUtils;
import com.shinwootns.ipm.collector.AppContextProvider;

public class SyslogProducer implements Runnable {

	private Logger _logger = null;
	private int _index = 0;
	
	public SyslogProducer(int index, Logger logger) {
		this._index = index;
		this._logger = logger;
	}

	@Override
	public void run() {
		
		if ( _logger != null)
			_logger.info(String.format("Syslog Producer#%d... start.", this._index));
		
		List<SyslogEntity> listSyslog = WorkerPoolManager.getInstance().popSyslogList(1000, 500);

		while(true)
		{
			listSyslog = WorkerPoolManager.getInstance().popSyslogList(1000, 500);
			
			if (listSyslog != null && listSyslog.size() > 0)
			{
				int count = listSyslog.size();
				
				for(SyslogEntity syslog : listSyslog)
				{
					if (syslog == null)
						continue;
					
					String rawData = syslog.getData();
	
					// Remove Carriage-Return & Line-Feed
					rawData = rawData.replaceAll("\\r\\n|\\r|\\n", " ");
					
					// Trim
					rawData = rawData.trim();
					
					JSONObject jobj = new JSONObject();
					jobj.put("host", syslog.getHost());
					jobj.put("facility", syslog.getFacility());
					jobj.put("severity", syslog.getSeverity());
					jobj.put("recv_time", syslog.getRecvTime());
					jobj.put("message", rawData);
					
					RabbitMQHandler.SendData(jobj, _logger);
				}
				
				listSyslog.clear();
				
				//_logger.debug( String.format("Syslog Producer#%d... process end:%d", this._index, count));
			}
			else
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					break;
				}
			}
		}
		
		if ( this._logger != null)
		{
			_logger.info(String.format("Syslog Producer#%d... end.", this._index));
		}
	}
}
