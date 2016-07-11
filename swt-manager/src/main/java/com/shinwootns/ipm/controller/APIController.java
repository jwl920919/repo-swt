package com.shinwootns.ipm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.data.entity.DeviceInfo;
import com.shinwootns.ipm.data.mapper.DeviceMapper;

@RestController
public class APIController {
	
	@Autowired(required=true)
	private DeviceMapper deviceMapper;
	
	/*
	@RequestMapping("/api/device")
	public List<DeviceInfo> device() {
		
		List<DeviceInfo> listDevice = deviceMapper.selectDevice();
		
		return listDevice;
	}*/
}
