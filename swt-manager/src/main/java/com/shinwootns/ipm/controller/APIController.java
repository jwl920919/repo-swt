package com.shinwootns.ipm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.shinwootns.common.auth.AuthCheckerLDAP;
import com.shinwootns.common.auth.LdapUserGroupAttr;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.service.auth.AuthCheckHandler;
import com.shinwootns.ipm.service.auth.AuthParam;
import com.shinwootns.ipm.service.auth.AuthResult;


@RestController
public class APIController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/auth", method=RequestMethod.GET)
	public String checkAuth(
			@RequestParam(value="userid") String userid, 
			@RequestParam(value="password") String password,
			@RequestParam(value="macaddr", defaultValue="") String macaddr) 
	{
		JsonObject json = new JsonObject();
		
		//json.addProperty("userid", userid);
		//json.addProperty("result", true);
		
		
		AuthParam param = new AuthParam();
		param.setUserId(userid);
		param.setPassword(password);
		param.setMacAddr(macaddr);
		
		AuthCheckHandler handler = new AuthCheckHandler();

		AuthResult result = handler.checkLogin(param);
		
		return JsonUtils.serialize(result).toString();
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
