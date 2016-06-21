package test.syslog;

import com.shinwootns.common.network.SyslogServer;

public class testSyslog {
	
	public static void main(String[] args) {

		SyslogServer thread = new SyslogServer();
		thread.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
