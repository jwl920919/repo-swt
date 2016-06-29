package com.shinwootns.ipm.collector;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.rabbitmq.client.AMQP.Basic.Recover;
import com.shinwootns.ipm.collector.service.AmqpManager;
import com.rabbitmq.client.ConnectionFactory;

@SpringBootApplication
public class IpmCollectorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		SpringApplication.run(IpmCollectorApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
        
		System.out.println("Application Start...");
    }
}
