package com.shinwootns.common.network;

public class SyslogManager {
	
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
		
		synchronized(this)
		{
			if (syslogServer == null) {
				
				syslogServer = new SyslogServer(handler);
				
				// bind socket
				if ( syslogServer.bindSocket() == false)
					return false;
				
				// start receive
				syslogServer.start();
				
				return true;
			}
		}
		
		return false;
	}
	//endregion
	
	//region [FUNC] stop
	public void stop() {
		
		synchronized(this)
		{
			if (syslogServer != null) {
		
				syslogServer.setStopFlag(true);
				
				syslogServer.interrupt();
				
				syslogServer.closeSocket();
			}
		}
	}
	//endregion
}
