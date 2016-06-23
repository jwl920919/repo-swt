package com.shinwootns.swt.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogHandler;
import com.shinwootns.common.utils.LogUtils;
import com.shinwootns.common.utils.TimeUtils;

public class SyslogHandlerImpl implements SyslogHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());

	public void processSyslog(SyslogEntity syslog) {
		
		//_logger.debug(String.format("[%s, %s] - %s", syslog.getHost(), TimeUtils.convertToStringTime(syslog.getRecvTime()), syslog.getData()));
		
		System.out.println(String.format("[%s, %s] - %s", syslog.getHost(), TimeUtils.convertToStringTime(syslog.getRecvTime()), syslog.getData()));
	}
}
