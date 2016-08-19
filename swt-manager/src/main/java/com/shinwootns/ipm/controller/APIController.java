package com.shinwootns.ipm.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.crypto.IllegalBlockSizeException;
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
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.auth.AuthParam;
import com.shinwootns.data.auth.AuthResult;
import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;
import com.shinwootns.ipm.WorkerManager;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.service.auth.AuthCheckHandler;


@RestController
public class APIController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	//region /api/cmd
	@RequestMapping(value="/api/cmd", method=RequestMethod.GET)
	public String exec_command(@RequestParam(value="command") String command) {
		
		JsonObject json = new JsonObject();
		
		if (command.toUpperCase().equals("SHUTDOWN")) {
			json.addProperty("command", command);
			json.addProperty("result", "OK");
			
			(new Thread( new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
					}
					WorkerManager.getInstance().TerminateApplication();					
				}
			})).start();
			
			return json.toString();
		}
		
		return json.toString();
	}
	//endregion
	
	//region /api/auth
	@RequestMapping(value="/api/auth", method=RequestMethod.GET)
	public String checkAuth(
			@RequestParam(value="userid") String userid, 
			@RequestParam(value="password") String password,
			@RequestParam(value="macaddr", defaultValue="") String macaddr) 
	{
		AuthResult result = new AuthResult();
		
		try
		{
			AuthParam param = new AuthParam();
			param.setUserId(userid);
			param.setPassword(CryptoUtils.Decode_AES128(password));
			param.setMacAddr(macaddr);
			
			AuthCheckHandler handler = new AuthCheckHandler();
			handler.checkLogin(param, result);
		}
		catch(IllegalBlockSizeException ex) {
			_logger.error(ex.getMessage());
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return JsonUtils.serialize(result).toString();
	}
	//endregion
	
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
