package com.shinwootns.ipm.collector.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
public class APIController {
	
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
