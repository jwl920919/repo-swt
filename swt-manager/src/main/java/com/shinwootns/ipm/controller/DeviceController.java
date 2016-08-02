package com.shinwootns.ipm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.ipm.data.mapper.DataMapper;

/*
@RestController
public class DeviceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataMapper deviceMapper;
	

	//==========================================================================
	// DHCP
	//==========================================================================
	@RequestMapping(value = "/api/device/dhcp", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DeviceDhcp selectDhcpByInsightHost(@RequestParam(value="host", defaultValue="") String host) {
        return deviceMapper.selectDhcpByInsightHost(host);
    }
	
	//==========================================================================
	// INSIGHT
	//==========================================================================
	@RequestMapping(value="/api/device/insight", method=RequestMethod.GET)
	@ResponseBody
	public List<DeviceInsight> index() {
		return deviceMapper.selectInsight();
	}
	
	@RequestMapping(value = "/api/device/insight/{device_id}", method = RequestMethod.GET)
    @ResponseBody
    public DeviceInsight selectInsight(@PathVariable(value = "device_id") int device_id) {
        return deviceMapper.selectInsightByDeviceId(device_id);
    }
	
	@RequestMapping(value = "/api/device/insight", method = RequestMethod.POST)
    @ResponseBody
    public int insertInsight(@RequestBody DeviceInsight device) {
		
		if (device  == null || device.getHost().isEmpty())
			return 0;
		
        return deviceMapper.insertInsight(device);
    }

	
	@RequestMapping(value = "/api/device/insights", method = RequestMethod.PUT)
    @ResponseBody
    public int updateInsight(@RequestBody DeviceInsight device) {
		
		if (device  == null || device.getHost().isEmpty())
			return 0;
		
        return deviceMapper.updateInsight(device);
    }
}
*/
