package com.shinwootns.ipm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWAPI {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	/*
	@Test
	public void testWAPI_Info() {
	
		BasicConfigurator.configure();
		
		InfobloxWAPIHandler wapiHandler = new InfobloxWAPIHandler("192.168.1.11", "admin", "infoblox");

		JsonArray jArray;
		if (wapiHandler.Connect()) {
			System.out.println("================== Network List");

			jArray = wapiHandler.getNetworkInfo();
			if (jArray != null)
				System.out.println(jArray.toString());

			System.out.println("================== Grid INfo");

			jArray = wapiHandler.getGridInfo();
			if (jArray != null)
				System.out.println(jArray.toString());

			System.out.println("================== Filter List");

			jArray = wapiHandler.getFilterInfo();
			if (jArray != null)
				System.out.println(jArray.toString());

			System.out.println("================== Range Info");

			jArray = wapiHandler.getRangeInfo();
			if (jArray != null)
				System.out.println(jArray.toString());
		}
	}
	*/
	
	/*
	@Test
	public void testWAPI_MacFilter() {

		BasicConfigurator.configure();
		
		InfobloxWAPIHandler wapiHandler = new InfobloxWAPIHandler("192.168.1.11", "admin", "infoblox");

		JsonArray jArray;
		
		if (wapiHandler.Connect()) {

			String macAddr = "10:41:7f:54:39:8f";
			String filterName = "BlackList";
			String userName = "전상수-테스트";

			System.out.println("================== Insert MacFilter");

			boolean isAdded = wapiHandler.insertMacFilter(macAddr, filterName, userName);
			System.out.println("Result=" + isAdded);

			System.out.println("================== Get MacFilter");

			jArray = wapiHandler.getMacFilter(macAddr);
			if (jArray != null)
				System.out.println(jArray.toString());

			System.out.println("================== Delete MacFilter");

			boolean isDelete = wapiHandler.deleteMacFilter(macAddr);
			System.out.println("Result=" + isDelete);

		}
	}
	*/
	
	/*
	@Test
	public void testWAPI() {
	
		BasicConfigurator.configure();
		
		InfobloxWAPIHandler wapiHandler = new InfobloxWAPIHandler("192.168.1.11", "admin", "infoblox");

		JsonArray jArray;
		
		if (wapiHandler.Connect()) {

			// LiceseInfo
			String hwid = "1412201310100022";
			jArray = wapiHandler.getLicenseInfo(hwid);
			
			System.out.println( JsonUtils.toPrettyString(jArray) );
		}
		
		return;
	}
	*/
	
	/*
	@Test
	public void testWAPI_DeviceStatus() {
	
		BasicConfigurator.configure();
		
		String host = "192.168.1.11";
		String wapiUser = "admin";
		String wapiPasswd = "infoblox";
		String community = "public";

		DhcpHandler handler = new DhcpHandler(host, wapiUser, wapiPasswd, community);

		// Get H/W Status
		DhcpDeviceStatus dhcpStatus = handler.getDeviceStatus();
		
		System.out.println( JsonUtils.serialize(dhcpStatus));
		
		return;
	}
	*/
}
