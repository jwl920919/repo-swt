package com.shinwootns.common.network;

import org.apache.log4j.Logger;

public class SyslogManager {
	
	// Singleton
	private static SyslogManager _instance;
	private SyslogManager(Logger logger) {
		this._logger = logger;
	}
	public static synchronized SyslogManager getInstance(Logger logger) {

		if (_instance == null) {
			_instance = new SyslogManager(logger);
		}
		return _instance;
	}
	
	// Syslog Server
	private SyslogServer syslogServer = null;
	private Logger _logger = null;

	public boolean start(SyslogHandler handler) {
		
		if (syslogServer == null) {
			
			syslogServer = new SyslogServer(this._logger, handler);
			
			// bind socket
			if ( syslogServer.bindSocket() == false)
				return false;
			
			// start receive
			syslogServer.start();
			
			return true;
		}
		
		return false;
	}
	
	public void stop() {
		
		if (syslogServer != null) {
			syslogServer.setStopFlag(true);
			
			syslogServer.interrupt();
			
			syslogServer.closeSocket();
		}
	}
}
