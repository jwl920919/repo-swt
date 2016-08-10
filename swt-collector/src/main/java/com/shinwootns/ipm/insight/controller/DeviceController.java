package com.shinwootns.ipm.insight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
	
	//@Autowired
	//private ApplicationProperty appProperty;
	
	/*
	//==========================================================================
	// DHCP
	//==========================================================================
	@RequestMapping(value = "/api/dhcp/status", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String status(@RequestParam HashMap<String, String> paramMap) {
    	
    	//String searchValue = map.get("searchValue");
		
		DeviceDhcp dhcp = SharedData.getInstance().dhcpDevice;
		if (dhcp == null)
			return "";
		
		DhcpDeviceStatus dhcpStatus = null;
		
		DhcpHandler handler = new DhcpHandler(); 
		
		if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
			dhcpStatus = handler.getDeviceStatus();
		}

		handler.close();
		
		if (dhcpStatus != null)
			return JsonUtils.serialize(dhcpStatus);
		
		return "{}";
    }*/
	
}
