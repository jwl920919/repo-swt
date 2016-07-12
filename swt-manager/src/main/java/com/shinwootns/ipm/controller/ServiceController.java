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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.ApplicationProperty;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.service.WorkerPoolManager;
import com.shinwootns.ipm.service.amqp.AmqpReceiver;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=true)
	private EventMapper eventMapper;

	@Autowired(required=true)
	private ApplicationProperty appProperties;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ApplicationProperty appProperty;
	
	@Bean
	Queue queue() {
		return new Queue("ipm.syslog", false);
	}
	
	@Bean
	AmqpReceiver receiver() {
        return new AmqpReceiver();
    }
	
	@Bean
	MessageListenerAdapter listenerAdapter(AmqpReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames("ipm.syslog");
		container.setRecoveryInterval(5000);		// amqp reconnect interval
		container.setMessageListener(listenerAdapter);
		return container;
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
	}
	
	@PreDestroy
	public void stopService() {
		// Stop
		WorkerPoolManager.getInstance().stop();
		
		_logger.info("Stop ServiceController.");
	}
}
