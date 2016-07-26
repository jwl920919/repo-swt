package com.shinwootns.ipm.collector.config;

/*
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
*/