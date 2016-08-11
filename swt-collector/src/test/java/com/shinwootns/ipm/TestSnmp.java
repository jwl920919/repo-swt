package com.shinwootns.ipm;

import org.junit.Test;

import com.shinwootns.ipm.insight.service.snmp.SnmpHandler;
import com.shinwootns.ipm.insight.service.snmp.SystemEntry;

public class TestSnmp {
	
	@Test
	public void TestSnmp() {
		
		String host = "192.168.1.35";
		String community = "public";
		
		SnmpHandler handler = new SnmpHandler(host, community);
		
		SystemEntry system = handler.collectSystem();
		
		System.out.println( system );
	}
}
