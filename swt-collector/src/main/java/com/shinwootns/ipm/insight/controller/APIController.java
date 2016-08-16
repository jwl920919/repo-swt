package com.shinwootns.ipm.insight.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.auth.AuthParam;
import com.shinwootns.data.auth.AuthResult;
import com.shinwootns.ipm.insight.WorkerManager;
import com.shinwootns.ipm.insight.service.auth.AuthCheckHandler;

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
			param.setPassword(CryptoUtils.Decode_AES128(password));
			param.setMacAddr(macaddr);
			
			AuthCheckHandler handler = new AuthCheckHandler();
			handler.checkLogin(param, result);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return JsonUtils.serialize(result).toString();
	}
	//endregion
}