package com.shinwootns.swt.service;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.utils.TimeUtils;

public class SyslogTask implements Runnable {

	private SyslogEntity _syslog;
	
	public SyslogTask(SyslogEntity syslog) {
		this._syslog = syslog;
	}
	
	@Override
	public void run() {
		
		System.out.println(String.format("[%s, %s] - %s"
				, _syslog.getHost(), TimeUtils.convertToStringTime(_syslog.getRecvTime()), _syslog.getData()));
		
		// ...
	}
}
