package com.shinwootns.ipm.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.service.handler.RedisHandler;
import com.shinwootns.ipm.worker.WorkerManager;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationProperty appProperty;
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("Start Service Controller.");
		
		// Set BeanProvider
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperty( appProperty );
		
		_logger.info(appProperty.toString());
		
		// Cluster Info
		RedisHandler.getInstance().registClusterRank();
		RedisHandler.getInstance().updateClusterMember();
		
		// Start
		WorkerManager.getInstance().start();
	}
	
	@PreDestroy
	public void stopService() {
		// Stop
		WorkerManager.getInstance().stop();
		
		_logger.info("Stop Service Controller.");
	}
}
