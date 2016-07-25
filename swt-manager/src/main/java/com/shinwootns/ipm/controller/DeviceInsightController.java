package com.shinwootns.ipm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.data.entity.DeviceInsight;
import com.shinwootns.ipm.data.mapper.DeviceMapper;

@RestController
public class DeviceInsightController {
	
	@Autowired(required=true)
	private DeviceMapper deviceMapper;
	
	@RequestMapping(value="/api/device/insights", method=RequestMethod.GET)
	@ResponseBody
	public List<DeviceInsight> index() {
		return deviceMapper.selectInsight();
	}
	
	@RequestMapping(value = "/api/device/insights/{device_id}", method = RequestMethod.GET)
    @ResponseBody
    public DeviceInsight selectInsight(@PathVariable(value = "device_id") int device_id) {
        return deviceMapper.selectInsightByDeviceId(device_id);
    }
	
	
	@RequestMapping(value = "/api/device/insights", method = RequestMethod.GET)
    @ResponseBody
    public int insertInsight(@RequestBody DeviceInsight device) {
		
		if (device  == null || device.getHost().isEmpty())
			return 0;
		
        return deviceMapper.insertInsight(device);
    }
}
