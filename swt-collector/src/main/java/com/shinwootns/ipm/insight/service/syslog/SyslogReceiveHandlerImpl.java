package com.shinwootns.ipm.insight.service.syslog;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogReceiveHandler;
import com.shinwootns.ipm.insight.data.SharedData;

public class SyslogReceiveHandlerImpl implements SyslogReceiveHandler {
	
	public void processSyslog(SyslogEntity syslog) {

		// Put into buffer
		SharedData.getInstance().addSyslogData(syslog);
	}
}
