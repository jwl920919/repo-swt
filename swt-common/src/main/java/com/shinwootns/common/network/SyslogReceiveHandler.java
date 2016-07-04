package com.shinwootns.common.network;

public interface SyslogReceiveHandler {
	
	public abstract void processSyslog(SyslogEntity syslog) ;
	
}
