package com.shinwootns.ipm.collector.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.data.status.DhcpDeviceStatus;

@RestController
public class DeviceController {
	
	@Autowired
	private ApplicationProperty appProperty;
	
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
