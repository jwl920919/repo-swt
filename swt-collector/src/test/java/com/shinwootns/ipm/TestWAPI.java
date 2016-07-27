package com.shinwootns.ipm;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shinwootns.common.infoblox.InfobloxWAPIHandler;
import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.data.status.DhcpStatus;

public class TestWAPI {

	private final Logger _logger = Logger.getLogger(this.getClass());
	
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

	@Test
	public void testWAPI_DeviceStatus() {
	
		BasicConfigurator.configure();
		
		String host = "192.168.1.11";
		String wapiUser = "admin";
		String wapiPasswd = "infoblox";
		String community = "public";

		DhcpHandler handler = new DhcpHandler(host, wapiUser, wapiPasswd, community);

		// Get H/W Status
		DhcpStatus dhcpStatus = handler.getHWStatus();
		
		System.out.println( JsonUtils.serialize(dhcpStatus));
		
		return;
	}
}
