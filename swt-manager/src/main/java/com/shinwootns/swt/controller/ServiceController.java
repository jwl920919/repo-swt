package com.shinwootns.swt.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.swt.service.SyslogAnalyzer;
import com.shinwootns.swt.service.SyslogReceiveHandler;

@EnableAsync
@Controller
public class ServiceController {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private SyslogAnalyzer analyzer = null;
	
	//@Autowired
	//private SystemProperties properties;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("startService()... start");

		startSyslogHandler();
		
		_logger.info("endService()... start");
	}
	
	@PreDestroy
	public void stopService() {
		
		_logger.info("stopService()... start");
		
		// Syslog
		stopSyslogHandler();
		
		_logger.info("stopService()... end");
	}
	
	private void startSyslogHandler() {
		
		SyslogAnalyzer.getInstance().start();
		
		// Start receive handler
		SyslogManager.getInstance().start(new SyslogReceiveHandler());
	}
	
	private void stopSyslogHandler() {

		// Stop receive handler
		SyslogManager.getInstance().stop();
		
		// Stop Analyzer
		SyslogAnalyzer.getInstance().stop();
	}
}

