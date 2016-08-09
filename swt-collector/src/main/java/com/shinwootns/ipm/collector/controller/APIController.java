package com.shinwootns.ipm.collector.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.shinwootns.ipm.collector.WorkerManager;

@RestController
public class APIController {
	
	
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
	
	@RequestMapping(value="/api/auth/ldap", method=RequestMethod.GET)
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
}
