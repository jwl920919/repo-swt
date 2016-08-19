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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
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
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.WorkerManager;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.service.auth.AuthCheckHandler;
import com.shinwootns.ipm.service.redis.RedisManager;

import redis.clients.jedis.Jedis;


@RestController
public class APIController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	//region /api/cmd
	@RequestMapping(value="/api/cmd", method=RequestMethod.GET)
	public String ApiExecuteCommand(@RequestParam(value="command") String command) {
		
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
	public String ApiCheckAuthentication(
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
	
	//region /api/status/dhcp/device_status/
	@RequestMapping(value="/api/status/dhcp/device_status/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiDhcpDeviceStatus(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result = redis.get((new StringBuilder())
					.append(RedisKeys.KEY_STATUS_DEVICE)
					.append(":").append(site_id).toString()
			);
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonArray()).toString();
	}
	//endregion
	
	//region /api/status/dhcp/dhcp_counter/
	@RequestMapping(value="/api/status/dhcp/dhcp_counter/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiDhcpCounter(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result = redis.get((new StringBuilder())
					.append(RedisKeys.KEY_STATUS_DHCP_COUNTER)
					.append(":").append(site_id).toString()
			);
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonObject()).toString();
	}
	//endregion
	
	//region /api/status/dhcp/dns_counter/
	@RequestMapping(value="/api/status/dhcp/dns_counter/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiDnsCounter(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result = redis.get((new StringBuilder())
					.append(RedisKeys.KEY_STATUS_DNS_COUNTER)
					.append(":").append(site_id).toString()
			);
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonObject()).toString();
	}
	//endregion
}
