package com.shinwootns.ipm.collector.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.ipm.collector.ApplicationProperty;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.service.WorkerPoolManager;
import com.shinwootns.ipm.collector.service.syslog.SyslogReceiveHandlerImpl;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=true)
	private ApplicationProperty appProperties;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Bean
	Queue queue() {
		return new Queue("ipm.syslog", false);
	}
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("Start ServiceController.");
		
		// Set BeanProvider
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperties( appProperties );
		
		// Start
		WorkerPoolManager.getInstance().start();
		
		// Start receive handler
		SyslogManager.getInstance().start(new SyslogReceiveHandlerImpl());
	}
	
	@PreDestroy
	public void stopService() {
		
		// Stop receive handler
		SyslogManager.getInstance().stop();
				
		// Stop
		WorkerPoolManager.getInstance().stop();
		
		_logger.info("Stop ServiceController.");
	}
}
