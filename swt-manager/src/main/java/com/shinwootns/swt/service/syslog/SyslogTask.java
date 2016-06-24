package com.shinwootns.swt.service.syslog;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.TimeUtils;

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
		
		/*
		System.out.println(String.format("[%s, %s] - %s", _syslog.getHost(),
				TimeUtils.convertToStringTime(_syslog.getRecvTime()),
				_syslog.getData())
		);*/
	}

	/*
	private void DebugPrint(String sKeyword, boolean bRenew, String sIP, String sMac, String sCoperation)
    {
        String sDHCP = sKeyword;
        sDHCP += (bRenew) ? "-RE" : "";

        System.out.println(String.format("{0,-10}, {1,-12}, {2,-15}, {3}, {4}"
        	, TimeUtils.currentTimeString("HH:mm:ss")
        	, sDHCP, sIP, sMac, sCoperation)
        );
    }
    */
}
