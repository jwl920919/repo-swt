package com.shinwootns.ipm.collector.worker.persist;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.service.handler.RabbitmqSender;
import com.shinwootns.ipm.collector.worker.BaseWorker;
import com.shinwootns.ipm.collector.worker.WorkerManager;

public class SyslogWorker extends BaseWorker {

	private Logger _logger = null;
	private int _index = 0;
	
	public SyslogWorker(int index, Logger logger) {
		this._index = index;
		this._logger = logger;
	}
	
	private boolean isSkipInDebugMode() {
		// get ApplicationProperty
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return true;
		
		// debug_insert_event_enable
		if (appProperty.enable_recv_syslog == false)
			return true;
		
		return false;
	}

	@Override
	public void run() {
		
		if (isSkipInDebugMode())
			return;
		
		if ( _logger != null)
			_logger.info(String.format("Syslog Producer#%d... start.", this._index));
		
		List<SyslogEntity> listSyslog = WorkerManager.getInstance().popSyslogList(1000, 500);

		while(isStopFlag())
		{
			listSyslog = WorkerManager.getInstance().popSyslogList(1000, 500);
			
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
					
					JsonObject jobj = new JsonObject();
					jobj.addProperty("host", syslog.getHost());
					jobj.addProperty("facility", syslog.getFacility());
					jobj.addProperty("severity", syslog.getSeverity());
					jobj.addProperty("recv_time", syslog.getRecvTime());
					jobj.addProperty("message", rawData);
					
					RabbitmqSender.SendData(jobj, _logger);
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
			_logger.info(String.format("Syslog Producer#%d... end.", this._index));
	}
}
