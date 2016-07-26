package com.shinwootns.ipm.collector.service.syslog;

import org.apache.log4j.Logger;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogReceiveHandler;
import com.shinwootns.ipm.collector.worker.WorkerManager;

public class SyslogReceiveHandlerImpl implements SyslogReceiveHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private final int SYSLOG_POP_COUNT = 100;

	public void processSyslog(SyslogEntity syslog) {

		// Add task
		WorkerManager.getInstance().addSyslogData(syslog);
	}
}
