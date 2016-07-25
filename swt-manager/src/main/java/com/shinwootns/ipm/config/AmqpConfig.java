package com.shinwootns.ipm.config;

/*

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shinwootns.ipm.service.handler.AmqpRecvHandler;

@Configuration
public class AmqpConfig {
	
	private final static String SYSLOG_QUEUE_NAME = "ipm.syslog";
	private final static int AMQP_RECONNECT_TIME = 5000;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Bean
	Queue queue() {
		return new Queue(SYSLOG_QUEUE_NAME, false);
	}
	
	@Bean
	AmqpRecvHandler receiver() {
        return new AmqpRecvHandler();
    }
	
	@Bean
	MessageListenerAdapter listenerAdapter(AmqpRecvHandler receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		
		// Set Queue
		container.setQueueNames(SYSLOG_QUEUE_NAME);
		
		// Reconnect interval
		container.setRecoveryInterval(AMQP_RECONNECT_TIME);
		
		// Listener
		container.setMessageListener(listenerAdapter);
		
		return container;
	}
}

*/