package com.shinwootns.ipm.collector.service;

import org.apache.log4j.Logger;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogReceiveHandler;

public class SyslogReceiveHandlerImpl implements SyslogReceiveHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());

	public void processSyslog(SyslogEntity syslog) {

		// Add task
		WorkerPoolManager.getInstance().addSyslogTask(syslog);
	}
}
