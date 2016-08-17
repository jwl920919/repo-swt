package com.shinwootns.ipm.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.WorkerManager;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.service.amqp.RabbitMQHandler;
import com.shinwootns.ipm.service.cluster.ClusterManager;
import com.shinwootns.ipm.service.redis.RedisManager;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationProperty appProperty;
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() throws Exception {
		
		SecurityContextHolder
			.getContext()
			.setAuthentication(
					new UsernamePasswordAuthenticationToken(
							"user", 
							"N/A", 
							AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")
					)
			);

		
		_logger.info("Start Service Controller.");
		
		// Set BeanProvider
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperty( appProperty );
		
		_logger.info(appProperty.toString());
		
		// Connect RabbitMQ
		if ( RabbitMQHandler.getInstance().connect() == false ) {
			_logger.error("RabbitMQHandler connect()... Failed.");
			WorkerManager.getInstance().TerminateApplication();
			return;
		}
		
		// Connect Redis
		if (RedisManager.getInstance().connect() == false ) {
			_logger.error("RedisHandler connect()... Failed.");
			WorkerManager.getInstance().TerminateApplication();
			return;
		}
		
		// Cluster Info
		ClusterManager.getInstance().updateMember();
		
		// Start
		WorkerManager.getInstance().start();
	}
	
	@PreDestroy
	public void stopService() {
		
		// Stop
		WorkerManager.getInstance().stop();
		
		RabbitMQHandler.getInstance().close();
		
		_logger.info("Stop Service Controller.");
	}
}
