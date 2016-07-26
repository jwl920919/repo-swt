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
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.infoblox.DhcpHandler;
import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.status.DhcpStatus;

@RestController
public class DeviceController {
	
	@Autowired
	private ApplicationProperty appProperty;
	
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
		
		DhcpHandler handler = new DhcpHandler(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()); 
		
		DhcpStatus dhcpStatus = handler.getHWStatus();
		
        return JsonUtils.serialize(dhcpStatus);
    }
	
}
