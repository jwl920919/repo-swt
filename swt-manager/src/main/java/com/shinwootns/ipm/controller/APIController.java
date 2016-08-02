package com.shinwootns.ipm.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.shinwootns.ipm.data.mapper.DataMapper;


@RestController
public class APIController {
	
	//@Autowired
	//private DataMapper dataMapper;
	
	@RequestMapping(value="/api/auth", method=RequestMethod.GET)
	public String checkAuth(
			@RequestParam(value="userid") String userid, 
			@RequestParam(value="password") String password,
			@RequestParam(value="macaddr", defaultValue="") String macaddr) 
	{
		JsonObject json = new JsonObject();
		
		json.addProperty("userid", userid);
		json.addProperty("result", true);
		
		// ...
		
		return json.toString();
	}
	
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
