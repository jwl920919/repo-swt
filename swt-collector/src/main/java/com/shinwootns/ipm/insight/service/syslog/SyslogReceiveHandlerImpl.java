package com.shinwootns.ipm.insight.service.syslog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogReceiveHandler;
import com.shinwootns.ipm.insight.data.SharedData;

public class SyslogReceiveHandlerImpl implements SyslogReceiveHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private final int SYSLOG_POP_COUNT = 100;

	public void processSyslog(SyslogEntity syslog) {

		// Put into buffer
		SharedData.getInstance().addSyslogData(syslog);
	}
}
