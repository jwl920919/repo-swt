package com.shinwootns.ipm.service.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shinwootns.ipm.ApplicationProperty;

@Configuration
public class AmqpConfig {
	
	@Autowired
	private ApplicationProperty appProperty;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
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
		
		// Debug - Skip recv syslog
		if ( appProperty.debugEnable && appProperty.enableRecvSyslog == false ) {
			container.stop();
			return container;
		}
		
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames("ipm.syslog");
		container.setRecoveryInterval(5000);		// amqp reconnect interval
		container.setMessageListener(listenerAdapter);
		
		return container;
	}
}
