package com.shinwootns.ipm.collector.service.syslog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogReceiveHandler;
import com.shinwootns.ipm.collector.data.SharedData;

public class SyslogReceiveHandlerImpl implements SyslogReceiveHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final int SYSLOG_POP_COUNT = 100;

	public void processSyslog(SyslogEntity syslog) {

		// Add task
		SharedData.getInstance().addSyslogData(syslog);
		
		//System.out.println(syslog.getData());
	}
}
