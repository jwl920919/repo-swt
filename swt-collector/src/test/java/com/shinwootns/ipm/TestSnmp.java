package com.shinwootns.ipm;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.shinwootns.common.utils.SystemUtils.InterfaceIP;
import com.shinwootns.data.entity.InterfaceCam;
import com.shinwootns.data.entity.InterfaceInfo;
import com.shinwootns.data.entity.InterfaceIp;
import com.shinwootns.ipm.insight.service.snmp.SnmpHandler;
import com.shinwootns.ipm.insight.service.snmp.SystemEntry;

public class TestSnmp {
	
	/*
	@Test
	public void testSnmp() {
		
		int device_id = 1;
		String host = "192.168.1.35";
		String community = "public";
		
		SnmpHandler handler = new SnmpHandler(device_id, host, community);

		// system
		System.out.println( "[SYSTEM] ==================================" );
		SystemEntry system = handler.collectSystem();
		System.out.println( system );
		
		// Interface
		System.out.println( "[INTERFACE] ==================================" );
		HashMap<Integer, InterfaceInfo> mapInf = handler.collectInterface();
		if (mapInf != null) {
			for(InterfaceInfo inf : mapInf.values()) {
				System.out.println(inf);
			}
		}
		
		// Interface IP
		System.out.println( "[INTERFACE IP] ==================================" );
		List<InterfaceIp> listInfIp = handler.collectInterfaceIP();
		if (listInfIp != null) {
			for(InterfaceIp ifIp : listInfIp) {
				System.out.println(ifIp);
			}
		}
		
		// Interface CAM
		System.out.println( "[CAM TABLE] ==================================" );
		HashMap<String, InterfaceCam> mapCam = handler.collectCam();
		if (mapCam != null) {
			for(InterfaceCam cam : mapCam.values()) {
				System.out.println(cam);
			}
		}
	}
	*/
	
	/*
	@Test
	public void testExtractVlanNumber() {
		
		String[] ifNames = {"unrouted VLAN 117", "VLAN 00005 (OFF14_2F_SW1)", "VLAN 1004", "Vlan145", "VLAN-1002"};
		
		for(String ifName : ifNames) {
			
			Pattern pattern = Pattern.compile("^(unrouted )?(VLAN|Vlan|Vl)[-| ]?([0-9]+)");
			
			Matcher matcher = pattern.matcher(ifName);
			if (matcher.find() && matcher.groupCount() > 0 ) {
				
				String vlanString =  matcher.group(3);
				
				int vlan = Integer.parseInt(vlanString);
				
				System.out.println(ifName + " => " + vlan);
			}
		}
		
		return;
	}
	*/
}
