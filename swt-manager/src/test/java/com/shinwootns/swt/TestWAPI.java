package com.shinwootns.swt;

import org.json.simple.JSONArray;
import org.junit.Test;

import com.shinwootns.swt.service.wapi.InfobloxWAPIHandler;

public class TestWAPI {

	/*
	@Test
	public void testWAPI_Info() {
		
		InfobloxWAPIHandler wapiHandler = new InfobloxWAPIHandler("192.168.1.11", "admin", "infoblox");

		JSONArray jArray;
		if (wapiHandler.Connect()) {
			System.out.println("================== Network List");

			jArray = wapiHandler.getNetworkInfo();
			if (jArray != null)
				System.out.println(jArray.toJSONString());

			System.out.println("================== Grid INfo");

			jArray = wapiHandler.getGridInfo();
			if (jArray != null)
				System.out.println(jArray.toJSONString());

			System.out.println("================== Filter List");

			jArray = wapiHandler.getFilterInfo();
			if (jArray != null)
				System.out.println(jArray.toJSONString());

			System.out.println("================== Range Info");

			jArray = wapiHandler.getRangeInfo();
			if (jArray != null)
				System.out.println(jArray.toJSONString());
		}
	}
	 */
	
	/*
	@Test
	public void testWAPI_MacFilter() {

		InfobloxWAPIHandler wapiHandler = new InfobloxWAPIHandler("192.168.1.11", "admin", "infoblox");

		JSONArray jArray;
		
		if (wapiHandler.Connect()) {

			String macAddr = "10:41:7f:54:39:8f";
			String filterName = "ipt_test";
			String userName = "전상수-테스트";

			System.out.println("================== Insert MacFilter");

			boolean isAdded = wapiHandler.insertMacFilter(macAddr, filterName, userName);
			System.out.println("Result=" + isAdded);

			System.out.println("================== Get MacFilter");

			jArray = wapiHandler.getMacFilter(macAddr);
			if (jArray != null)
				System.out.println(jArray.toJSONString());

			System.out.println("================== Delete MacFilter");

			boolean isDelete = wapiHandler.deleteMacFilter(macAddr);
			System.out.println("Result=" + isDelete);

		}
	}
	*/
	
	@Test
	public void testWAPI_LeaseIPList() {

		InfobloxWAPIHandler wapiHandler = new InfobloxWAPIHandler("192.168.1.11", "admin", "infoblox");

		JSONArray jArray;
		
		if (wapiHandler.Connect()) {
			
			System.out.println("================== Get Lease-IP List");

			jArray = wapiHandler.getLeaseIPList(200);
			
			if (jArray != null)
				System.out.println(jArray.toJSONString());
			
			System.out.println("Count = " + jArray.size());
		}
	}
}
