package com.shinwootns.swt.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.common.utils.LogUtils;
import com.shinwootns.swt.SWTManagerApplication;
import com.shinwootns.swt.property.SystemProperties;

@EnableAsync
@Controller
public class ServiceController {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SystemProperties properties;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("startService()... start");
		
		// Start SyslogHandler
		startSyslogHandler();
		
		_logger.info("endService()... start");
	}
	
	@PreDestroy
	public void stopService() {
		
		_logger.info("stopService()... start");
		
		// Stop SyslogHandler
		stopSyslogHandler();
		
		_logger.info("stopService()... end");
	}
	
	private void startSyslogHandler() {
		SyslogManager.getInstance().start(new SyslogHandlerImpl());
	}
	
	private void stopSyslogHandler() {
		SyslogManager.getInstance().stop();
	}
}

