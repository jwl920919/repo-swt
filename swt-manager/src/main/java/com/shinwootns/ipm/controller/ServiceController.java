package com.shinwootns.ipm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.ApplicationProperties;
import com.shinwootns.ipm.MainApplication;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.AuthTypeEntity;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;
import com.shinwootns.ipm.service.WorkerPoolManager;
import com.shinwootns.ipm.service.amqp.AmqpReceiver;

//import com.shinwootns.ipm.data.entity.AuthType;
//import com.shinwootns.ipm.data.repository.AuthTypeRepository;

@RestController
//@SpringApplicationConfiguration(classes = MainApplication.class)
public class ServiceController {
	
	private final Log _logger = LogFactory.getLog(this.getClass());
	
	@Autowired(required=true)
	private EventMapper eventMapper;

	@Autowired(required=true)
	private ApplicationProperties appProperties;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
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
		container.setMessageListener(listenerAdapter);
		return container;
	}
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("Start ServiceController.");
		
		System.out.println(context.toString());
		
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
