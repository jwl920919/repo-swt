package com.shinwootns.ipm.insight.controller;

import javax.crypto.IllegalBlockSizeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.api.AuthParam;
import com.shinwootns.data.api.AuthResult;
import com.shinwootns.data.api.ProcessResult;
import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.WorkerManager;
import com.shinwootns.ipm.insight.data.SharedData;
import com.shinwootns.ipm.insight.data.mapper.DeviceMapper;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;
import com.shinwootns.ipm.insight.service.auth.AuthCheckHandler;
import com.shinwootns.ipm.insight.service.infoblox.DhcpHandler;

@RestController
public class APIController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());

	//region [GET] /api/exec_cmd
	@RequestMapping(value="/api/exec_cmd", method=RequestMethod.GET)
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
		else if (command.toUpperCase().equals("LOAD_ALL")) {
			
			SharedData.getInstance().LoadConfigAll();
			
			json.addProperty("command", command);
			json.addProperty("result", "OK");
		}
		else if (command.toUpperCase().equals("LOAD_DEVICE")) {
			
			DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
			if (deviceMapper != null) {
				SharedData.getInstance().LoadDhcpDevice(deviceMapper);
				SharedData.getInstance().LoadNetworkDevice(deviceMapper);
				SharedData.getInstance().LoadDeviceIP(deviceMapper);
				
				json.addProperty("command", command);
				json.addProperty("result", "OK");
			}
			else {
				json.addProperty("command", command);
				json.addProperty("result", "Failed");
			}
		}
		else if (command.toUpperCase().equals("LOAD_CONFIGS")) {
			
			DeviceMapper deviceMapper = SpringBeanProvider.getInstance().getDeviceMapper();
			DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
			
			if (deviceMapper != null && dhcpMapper != null) {
				SharedData.getInstance().LoadDhcpDevice(deviceMapper);
				SharedData.getInstance().LoadGuestRange(dhcpMapper);
				SharedData.getInstance().LoadBlacklistMacFilter(dhcpMapper);
				
				json.addProperty("command", command);
				json.addProperty("result", "OK");
			}
			else {
				json.addProperty("command", command);
				json.addProperty("result", "Failed");
			}
		}
		
		return json.toString();
	}
	//endregion
	
	//region [GET] /api/check_auth
	@RequestMapping(value="/api/check_auth", method=RequestMethod.GET)
	public String checkAuth(
			@RequestParam(value="setupid") String setupId,
			@RequestParam(value="userid") String userid, 
			@RequestParam(value="password") String password,
			@RequestParam(value="macaddr", defaultValue="") String macaddr) 
	{
		AuthResult result = new AuthResult();
		
		try
		{
			AuthParam param = new AuthParam();
			param.setSetupId(Integer.parseInt(setupId));
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
	
	//region [GET] /api/macfilter (Get)
	@RequestMapping(value="/api/macfilter", method=RequestMethod.GET)
	public String getMacFilter(@RequestParam(value="macaddr") String macAddr) 
	{
		ProcessResult resultValue = new ProcessResult();
		
		if (SharedData.getInstance().getSiteID() <= 0) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] SiteID is zero");
			return JsonUtils.serialize(resultValue).toString();
		}
		
		DeviceDhcp dhcp = SharedData.getInstance().GetDhcpDevice();
		if (dhcp == null) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] Failed get DHCP device info.");
			return JsonUtils.serialize(resultValue).toString();
		}
		
		DhcpHandler handler = new DhcpHandler();
		try
		{
			if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
				
				String filterName = handler.getDhcpMacFilter(macAddr);
				
				if (filterName != null) {
					resultValue.setResult(true);
					resultValue.setMessage((new StringBuilder()).append("Registed. (").append(filterName).append(", ").append(macAddr).append(")").toString());
				}
				else {
					resultValue.setResult(false);
					resultValue.setMessage((new StringBuilder()).append("Not registed. (").append(macAddr).append(")").toString());
				}
				
				return JsonUtils.serialize(resultValue).toString();
			}
			else
			{
				resultValue.setErrorOccurred(true);
				resultValue.setMessage("[ERROR] Failed connect DHCP device.");
				return JsonUtils.serialize(resultValue).toString();
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
			
			resultValue.setErrorOccurred(true);
			resultValue.setMessage( ex.getMessage() );
			return JsonUtils.serialize(resultValue).toString();
		} finally {
			handler.close();
		}
	}
	//endregion
	
	//region [PUT] /api/macfilter (Insert)
	@RequestMapping(value="/api/macfilter", method=RequestMethod.PUT)
	public String insertMacFilter(
			@RequestParam(value="macaddr") String macAddr,
			@RequestParam(value="filtername") String filtername,
			@RequestParam(value="userid") String userid) 
	{
		ProcessResult resultValue = new ProcessResult();
		
		if (SharedData.getInstance().getSiteID() <= 0) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] SiteID is zero");
			return JsonUtils.serialize(resultValue).toString();
		}
		
		DeviceDhcp dhcp = SharedData.getInstance().GetDhcpDevice();
		if (dhcp == null) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] Failed get DHCP device info.");
			return JsonUtils.serialize(resultValue).toString();
		}
		
		DhcpHandler handler = new DhcpHandler();
		try
		{
			if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
				
				boolean result = handler.insertDhcpMacFilter(macAddr, filtername, userid);
				
				resultValue.setResult(result);
				if (result)
					resultValue.setMessage((new StringBuilder()).append("INSERT OK. (").append(filtername).append(", ").append(macAddr).append(")").toString());
				else
					resultValue.setMessage((new StringBuilder()).append("INSERT failed. (").append(filtername).append(", ").append(macAddr).append(")").toString());
				
				return JsonUtils.serialize(resultValue).toString();
			}
			else
			{
				resultValue.setErrorOccurred(true);
				resultValue.setMessage("[ERROR] Failed connect DHCP device.");
				return JsonUtils.serialize(resultValue).toString();
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
			
			resultValue.setErrorOccurred(true);
			resultValue.setMessage( ex.getMessage() );
			return JsonUtils.serialize(resultValue).toString();
		} finally {
			handler.close();
		}
	}
	//endregion
	
	//region [DELETE] /api/macfilter (Delete)
	@RequestMapping(value="/api/macfilter", method=RequestMethod.DELETE)
	public String deleteMacFilter(@RequestParam(value="macaddr") String macAddr) 
	{
		ProcessResult resultValue = new ProcessResult();
		
		if (SharedData.getInstance().getSiteID() <= 0) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] SiteID is zero");
			return JsonUtils.serialize(resultValue).toString();
		}
		
		DeviceDhcp dhcp = SharedData.getInstance().GetDhcpDevice();
		if (dhcp == null) {
			resultValue.setErrorOccurred(true);
			resultValue.setMessage("[ERROR] Failed get DHCP device info.");
			return JsonUtils.serialize(resultValue).toString();
		}
		
		DhcpHandler handler = new DhcpHandler();
		try
		{
			if ( handler.Connect(dhcp.getHost(), dhcp.getWapiUserid(), dhcp.getWapiPassword(), dhcp.getSnmpCommunity()) ) {
				
				boolean result = handler.deleteDhcpMacFilter(macAddr, null, null);
				
				resultValue.setResult(result);
				if (result)
					resultValue.setMessage((new StringBuilder()).append("DELETE OK. (").append(macAddr).append(")").toString());
				else
					resultValue.setMessage((new StringBuilder()).append("DELETE Failed. (").append(macAddr).append(")").toString());
				
				return JsonUtils.serialize(resultValue).toString();
			}
			else
			{
				resultValue.setErrorOccurred(true);
				resultValue.setMessage("[ERROR] Failed connect DHCP device.");
				return JsonUtils.serialize(resultValue).toString();
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
			
			resultValue.setErrorOccurred(true);
			resultValue.setMessage( ex.getMessage() );
			return JsonUtils.serialize(resultValue).toString();
		} finally {
			handler.close();
		}
	}
	//endregion
}
