package com.shinwootns.common.network;

import org.apache.log4j.Logger;

public class SyslogManager {
	
	private final Logger _logger = Logger.getLogger(this.getClass());

	// Syslog Server
	private SyslogServer syslogServer = null;
	
	//region Singleton
	private static SyslogManager _instance;
	private SyslogManager() {}
	public static synchronized SyslogManager getInstance() {

		if (_instance == null) {
			_instance = new SyslogManager();
		}
		return _instance;
	}
	//endregion
	
	//region [FUNC] start
	public boolean start(SyslogReceiveHandler handler) {
		
		if (syslogServer == null) {
			
			syslogServer = new SyslogServer(handler);
			
			// bind socket
			if ( syslogServer.bindSocket() == false)
				return false;
			
			// start receive
			syslogServer.start();
			
			return true;
		}
		
		return false;
	}
	//endregion
	
	//region [FUNC] stop
	public void stop() {
		
		if (syslogServer != null) {
			syslogServer.setStopFlag(true);
			
			syslogServer.interrupt();
			
			syslogServer.closeSocket();
		}
	}
	//endregion
}
