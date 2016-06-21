package com.shinwootns.swt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.shinwootns.swt.property.SystemProperties;

@Service
public class SyslogHandlerService {
	
	@Autowired
	private SystemProperties properties;
	
	@Async
	public void startService() {
		
		System.out.println(String.format("SyslogHandlerService - StartService()... Start(%s)", properties.getName()));
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("SyslogHandlerService - StartService()... end");
	}
	
	@Async
	public void stopService() {
		
	}
}
