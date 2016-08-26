package com.shinwootns.ipm.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
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
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.api.AuthParam;
import com.shinwootns.data.api.AuthResult;
import com.shinwootns.data.api.ProcessResult;
import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.WorkerManager;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.service.auth.AuthCheckHandler;
import com.shinwootns.ipm.service.redis.RedisManager;

import redis.clients.jedis.Jedis;


@RestController
public class APIController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	//region [GET] /api/exec_cmd
	@RequestMapping(value="/api/exec_cmd", method=RequestMethod.GET)
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
	
	//region [GET] /api/check_auth
	@RequestMapping(value="/api/check_auth", method=RequestMethod.GET)
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
			//param.setPassword(CryptoUtils.Decode_AES128(password));
			param.setPassword(password);
			param.setMacAddr(macaddr);
			
			AuthCheckHandler handler = new AuthCheckHandler();
			handler.checkLogin(param, result);
		}
		//catch(IllegalBlockSizeException ex) {
		//	_logger.error(ex.getMessage());
		//}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return JsonUtils.serialize(result).toString();
	}
	//endregion
	
	//region [GET] /api/status/dhcp/device_status/
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
	
	//region [GET] /api/status/dhcp/dhcp_counter/
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
	
	//region [GET] /api/status/dhcp/dns_counter/
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

	//region [GET] /api/macfilter (Get)
	@RequestMapping(value="/api/macfilter", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getMacFilter(@RequestParam(value="site_id") Integer site_id, @RequestParam(value="macaddr") String macAddr) 
	{
		ProcessResult resultValue = new ProcessResult();
		
		// Process MacFilter 
		_processMacFilter(RequestMethod.GET, site_id, macAddr, null, null, resultValue);
		
		return JsonUtils.serialize(resultValue).toString();
	}
	//endregion
	
	//region [PUT] /api/macfilter (Insert)
	@RequestMapping(value="/api/macfilter", method=RequestMethod.PUT)
	public String insertMacFilter(
			@RequestParam(value="site_id") Integer site_id,
			@RequestParam(value="macaddr") String macAddr,
			@RequestParam(value="filtername") String filtername,
			@RequestParam(value="userid") String userid) 
	{
		ProcessResult resultValue = new ProcessResult();
		
		// Process MacFilter 
		_processMacFilter(RequestMethod.PUT, site_id, macAddr, filtername, userid, resultValue);
		
		return JsonUtils.serialize(resultValue).toString();
	}
	//endregion
	
	//region [DELETE] /api/macfilter (Delete)
	@RequestMapping(value="/api/macfilter", method=RequestMethod.DELETE)
	public String deleteMacFilter(
			@RequestParam(value="site_id") Integer site_id,
			@RequestParam(value="macaddr") String macAddr)
	{
		ProcessResult resultValue = new ProcessResult();
		
		// Process MacFilter 
		_processMacFilter(RequestMethod.DELETE, site_id, macAddr, null, null, resultValue);
		
		return JsonUtils.serialize(resultValue).toString();
	}
	//endregion
	
	private boolean _processMacFilter(RequestMethod reqMode, Integer site_id, String macAddr, String filtername, String userid, ProcessResult resultValue) {
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();

		if (appProperty == null || dataMapper == null) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] ApplicationProperty or DataMapper is null.");
			return false;
		}
		else if (site_id == null || site_id <= 0) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] SiteID is null.");
			return false;
		}
		
		// Get Master DeviceInsight
		DeviceInsight insight = dataMapper.selectInsightMaster(site_id);
		if (insight == null) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] Failed get Master-Insight. SiteID=" + site_id);
			return false;
		}
		
		StringBuilder url = new StringBuilder();
		url.append("http://").append(insight.getIpaddr()).append(":").append(insight.getPort()).append("/api");
		
		HttpClient restClient = new HttpClient();
		
		try {
			
			if (restClient.Connect_Https(
					url.toString()
					, appProperty.security_user
					, CryptoUtils.Decode_AES128(appProperty.security_password)) == false ) 
			{
				// Failed to connect
				resultValue.setErrorOccurred(true);
				resultValue.setMessage(
						(new StringBuilder())
						.append("[ERROR] Failed connect master insight. ")
						.append(insight.getHost())
						.append("(").append(insight.getIpaddr()).append(")")
						.toString());
				
				return false;
			}
			
			StringBuilder apiUrl = new StringBuilder();
			apiUrl.append("/macfilter")
			.append("?macaddr=").append(macAddr);
			
			// GET
			if (reqMode == RequestMethod.GET) {

				// Get
				String output = restClient.Get(apiUrl.toString());
				
				if (output != null) {
					
					// Deserialize
					ProcessResult queryResult = (ProcessResult)JsonUtils.deserialize(output, ProcessResult.class);
					
					if (queryResult != null) {
						resultValue.setResult( queryResult.getResult() );
						resultValue.setErrorOccurred( queryResult.isErrorOccurred() );
						resultValue.setMessage(queryResult.getMessage());
					}
				}
				else {
					resultValue.setErrorOccurred(true);
					resultValue.setMessage("[ERROR] Failed call insight api. "+ apiUrl.toString());
				}
			}
			// PUT
			else if (reqMode == RequestMethod.PUT) {

				apiUrl.append("&filtername=").append(filtername);
				apiUrl.append("&userid=").append(userid);
				
				// Put
				String output = restClient.Put(apiUrl.toString(), null, null);
				
				if (output != null) {
					// Deserialize
					ProcessResult queryResult = (ProcessResult)JsonUtils.deserialize(output, ProcessResult.class);
					
					if (queryResult != null) {
						resultValue.setResult( queryResult.getResult() );
						resultValue.setErrorOccurred( queryResult.isErrorOccurred() );
						resultValue.setMessage(queryResult.getMessage());
					}
				}
				else {
					resultValue.setErrorOccurred(true);
					resultValue.setMessage("[ERROR] Failed call insight api. "+ apiUrl.toString());
				}
			}
			// DELETE
			else if (reqMode == RequestMethod.DELETE) {
				
				// Delete
				String output = restClient.Delete(apiUrl.toString(), null, null);
				
				if (output != null) {
					// Deserialize
					ProcessResult queryResult = (ProcessResult)JsonUtils.deserialize(output, ProcessResult.class);
					
					if (queryResult != null) {
						resultValue.setResult( queryResult.getResult() );
						resultValue.setErrorOccurred( queryResult.isErrorOccurred() );
						resultValue.setMessage(queryResult.getMessage());
					}
				}
				else {
					resultValue.setErrorOccurred(true);
					resultValue.setMessage("[ERROR] Failed call insight api. "+ apiUrl.toString());
				}
			}
			
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			resultValue.setErrorOccurred(true);
			resultValue.setMessage(e.getMessage());
		} finally {
			restClient.Close();
		}
		
		return false;
	}
}
