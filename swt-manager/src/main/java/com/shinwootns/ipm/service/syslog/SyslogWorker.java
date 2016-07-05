package com.shinwootns.ipm.service.syslog;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.context.ConfigurableApplicationContext;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.entity.EventLogEntity;
import com.shinwootns.ipm.data.mapper.EventLogMapper;
import com.shinwootns.ipm.service.BaseWorker;

public class SyslogWorker extends BaseWorker {

	private final Log _logger = LogFactory.getLog(getClass());
	private int _index = 0;
	
	public SyslogWorker(int index) {
		this._index = _index;
	}
	
	@Override
	public void run() {
		
		/*
		ConfigurableApplicationContext context = ContextProvider.getInstance().getApplicationContext();
		if (context == null) {
			_logger.error("AppContextProvider.getInstance().getApplicationContext().... failed");
			return;
		}
		
		EventLogMapper eventLogMapper = context.getBean("eventLogMapper", EventLogMapper.class);
		*/
		
		EventLogMapper eventLogMapper = SpringBeanProvider.getInstance().getEventLogMapper();

		if (eventLogMapper == null) {
			_logger.error("getBean('eventLogMapper').... failed");
			return;
		}
		
		if ( _logger != null)
			_logger.info(String.format("Syslog Consumer#%d... start.", this._index));
		
		List<JSONObject> listSyslog = SharedData.getInstance().popSyslogList(1000, 500);

		while(true)
		{
			listSyslog = SharedData.getInstance().popSyslogList(1000, 500);
			
			if (listSyslog != null && listSyslog.size() > 0)
			{
				int count = listSyslog.size();
				
				for(JSONObject jObj : listSyslog)
				{
					if (jObj == null)
						continue;
					
					try {
						
						EventLogEntity eventLog = new EventLogEntity(); 

						eventLog.setHostIp(JsonUtils.getValueToString(jObj, "host", ""));
						eventLog.setDeviceId(0);
						eventLog.setEventType("syslog");
						eventLog.setSeverity((int)JsonUtils.getValueToNumber(jObj, "severity", 6));
						eventLog.setMessage(JsonUtils.getValueToString(jObj, "message", ""));
						eventLog.setCollectTime(JsonUtils.getValueToTimestamp(jObj, "recv_time", TimeUtils.currentTimeMilis() ));
						
						eventLogMapper.insert(eventLog);
						
						_logger.info(jObj.toJSONString());
						
						
					} catch (Exception ex) {
						_logger.error(ex.getMessage(), ex);
					}

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
				
				listSyslog.clear();
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
			_logger.info(String.format("Syslog Consumer#%d... end.", this._index));
		
		/*
		// Process Infoblox DHCP
		try
		{
			String rawData = _syslog.getData();

			// Remove Carriage-Return & Line-Feed
			rawData = rawData.replaceAll("\\r\\n|\\r|\\n", " ");
			
			// Trim
			rawData = rawData.trim();
			
			JSONObject jResult = SyslogParser.processSyslog(rawData);
			
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
}
