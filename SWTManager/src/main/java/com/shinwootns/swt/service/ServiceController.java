package com.shinwootns.swt.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shinwootns.swt.property.SystemProperties;


@Controller
public class ServiceController {
	
	@Autowired
	private SystemProperties properties;
	
	@PostConstruct
	public void startService() {
		
		System.out.println(String.format("ServiceController - startService(%s)... Start", properties.getName()));
		
		System.out.println(String.format("ServiceController - startService(%s)... End", properties.getName()));
	}
	
	@PreDestroy
	public void stopService() {
		System.out.println("ServiceController - stopService()... end");
	}
}

