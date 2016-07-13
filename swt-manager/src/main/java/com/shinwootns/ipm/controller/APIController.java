package com.shinwootns.ipm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.data.entity.DeviceEntity;
import com.shinwootns.ipm.data.mapper.DeviceMapper;

@RestController
public class APIController {
	
	@Autowired(required=true)
	private DeviceMapper deviceMapper;
	
	@RequestMapping(value="/api/device", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<DeviceEntity> getDevice(
			@RequestParam(value="device_type", defaultValue="") String device_type ) 
	{
		
		List<DeviceEntity> listDevice;
		
		if (device_type == null || device_type.isEmpty())
			listDevice = deviceMapper.selectDevice();
		else
			listDevice = deviceMapper.selectDeviceByType(device_type);
		
		return listDevice;
	}
	
	@RequestMapping(value="/api/device/{device_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public DeviceEntity getDevice(
			@PathVariable(value="device_id") int deviceId ) {
		
		DeviceEntity device = deviceMapper.selectDeviceById(deviceId);
		
		return device;
	}
	
	/*
	@RequestMapping("/api/device")
	public List<DeviceInfo> device() {
		
		List<DeviceInfo> listDevice = deviceMapper.selectDevice();
		
		return listDevice;
	}*/
}
