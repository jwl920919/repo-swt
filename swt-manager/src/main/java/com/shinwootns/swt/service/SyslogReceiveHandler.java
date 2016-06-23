package com.shinwootns.swt.service;

import org.apache.log4j.Logger;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogHandler;

public class SyslogReceiveHandler implements SyslogHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());

	public void processSyslog(SyslogEntity syslog) {

		// Add task
		SyslogAnalyzer.getInstance().addTask(syslog);
	}
}
