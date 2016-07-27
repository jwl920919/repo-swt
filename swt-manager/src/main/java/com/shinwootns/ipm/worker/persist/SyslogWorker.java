package com.shinwootns.ipm.worker.persist;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.EventData;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.worker.BaseWorker;

public class SyslogWorker extends BaseWorker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	private int _index = 0;
	
	public SyslogWorker(int index) {
		this._index = _index;
	}
	
	private boolean isSkipInDebugMode() {
		// get ApplicationProperty
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null)
			return true;
		
		// debug_insert_event_enable
		if (appProperty.enableRecvSyslog == false)
			return true;
		
		return false;
	}
	
	@Override
	public void run() {
		
		if (isSkipInDebugMode())
			return;

		_logger.info(String.format("SyslogWorker#%d... start.", this._index));
		
		List<JsonObject> listSyslog = null;
		
		while(true)
		{
			listSyslog = SharedData.getInstance().syslogQueue.pop(500, 100);
			if (listSyslog == null)
				continue;
			
			for(JsonObject jObj : listSyslog)
			{
				if (jObj == null) 
					continue;
				
				try {
					
					//_logger.info(jObj.toJSONString());
					
					// Parsing
					parseSyslog(jObj);
					
					// Insert Event Queue
					insertEventQueue(jObj);
					
				}					
				catch (Exception ex) {
					_logger.error(ex.getMessage(), ex);
				}
			}
			
			listSyslog.clear();
			listSyslog = null;
		}
		
		//if ( this._logger != null)
		//	_logger.info(String.format("Syslog Consumer#%d... end.", this._index));
		
		/*
		// Process Infoblox DHCP
		try
		{
			String rawData = _syslog.getData();

			// Remove Carriage-Return & Line-Feed
			rawData = rawData.replaceAll("\\r\\n|\\r|\\n", " ");
			
			// Trim
			rawData = rawData.trim();
			
			JSonObject jResult = SyslogParser.processSyslog(rawData);
			
			// Check Result
	        if (jResult != null && jResult.containsKey("result") && jResult.get("result") == Boolean.TRUE)
	        {
	        	System.out.println(String.format("%s", jResult.toJSONString()));
	        	
	        	return;
	        }
	        else
	        {
	        	//System.out.println(String.format("[Unknown] %s", rawData));
	        }

		}
		catch(Exception ex)
		{
			_logger.error(ex.getMessage(), ex);
		}
		*/
	}
	
	private void parseSyslog(JsonObject jObj) {
		/*
		String rawData = jObj.getData();

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
		*/

	}
	
	private void insertEventQueue(JsonObject jObj) {
		
		EventData eventLog = new EventData(); 

		eventLog.setHostIp(JsonUtils.getValueToString(jObj, "host", ""));
		eventLog.setDeviceId((int)JsonUtils.getValueToNumber(jObj, "device_id", 0));
		eventLog.setEventType("syslog");
		eventLog.setSeverity((int)JsonUtils.getValueToNumber(jObj, "severity", 6));
		eventLog.setMessage(JsonUtils.getValueToString(jObj, "message", ""));
		eventLog.setCollectTime(JsonUtils.getValueToTimestamp(jObj, "recv_time", 0 ));
		
		// Put Event Queue
		SharedData.getInstance().eventQueue.put(eventLog);
	}
}
