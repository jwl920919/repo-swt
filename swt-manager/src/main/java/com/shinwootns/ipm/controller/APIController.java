package com.shinwootns.ipm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIController {
	
	//@Autowired
	//private DeviceMapper deviceMapper;
	
	/*
	@RequestMapping(value="/api/device/dhcp", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<DeviceDhcp> getDeviceDhcp(
			@RequestParam(value="device_type", defaultValue="") String device_type ) 
	{
		
		List<DeviceDhcp> listDhcp = deviceMapper.selectDeviceDhcp();
		
		return listDhcp;
	}*/

	/*
	@RequestMapping(value="/api/device/{device_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public DeviceEntity getDevice(
			@PathVariable(value="device_id") int deviceId ) {
		
		DeviceEntity device = deviceMapper.selectDeviceById(deviceId);
		
		return device;
	}
	*/
	
	/*
	@RequestMapping("/api/device")
	public List<DeviceInfo> device() {
		
		List<DeviceInfo> listDevice = deviceMapper.selectDevice();
		
		return listDevice;
	}*/
}
