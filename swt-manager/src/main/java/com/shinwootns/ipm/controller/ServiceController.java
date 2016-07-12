package com.shinwootns.ipm.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.ApplicationProperty;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.service.redis.RedisHandler;
import com.shinwootns.ipm.service.WorkerPoolManager;
import com.shinwootns.ipm.service.amqp.AmqpReceiver;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationProperty appProperty;
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("Start ServiceController.");
		
		// Set BeanProvider
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperties( appProperty );
		
		// Cluster Info
		RedisHandler.getInstance().registClusterRank();
		RedisHandler.getInstance().updateClusterMember();
		
		// Start
		WorkerPoolManager.getInstance().start();
	}
	
	@PreDestroy
	public void stopService() {
		// Stop
		WorkerPoolManager.getInstance().stop();
		
		_logger.info("Stop ServiceController.");
	}
}
