package com.shinwootns.ipm.collector;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.collector.service.SyslogReceiveHandlerImpl;
import com.shinwootns.ipm.collector.service.WorkerPoolManager;

@SpringBootApplication
public class CollectorApplication implements CommandLineRunner {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	Queue queue() {
		return new Queue("ipm.syslog", false);
	}
	/*
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}
	*/
	
	/*
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	*/
	
	class TestThread extends Thread{
		@Override
		public void run() {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Test Start.");
			
			int count = 10000;
			long startTime = TimeUtils.currentTimeMilis();
			
			for(int i=0; i<count; i++) {
				SyslogEntity syslog = new SyslogEntity();
				syslog.setHost("1.1.1.1");
				syslog.setFacility(1);
				syslog.setSeverity(i%7);
				syslog.setRecvTime(TimeUtils.currentTimeMilis());
				syslog.setData(String.format("This is test message #%d", i));
				
				WorkerPoolManager.getInstance().addSyslogTask(syslog);				
			}
			
			long elapsed = TimeUtils.currentTimeMilis()-startTime;
			
			System.out.println("Test End.");
			
			System.out.println(String.format("Count: %d,  Elapsed: %d ms, CPS: %.2f", count, elapsed, (elapsed/(float)count)*1000.0));
		}
	}
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(CollectorApplication.class, args);
				
		AppContextProvider.getInstance().setApplicationContext( context );
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Application Start...");
		
		//rabbitTemplate.convertAndSend("ipm.syslog", "Hello from RabbitMQ!");
		
		startSyslogHandler();
		
		TestThread thread = new TestThread();
		thread.start();
	}
		
	private void startSyslogHandler() {
		
		WorkerPoolManager.getInstance().start();
		
		// Start receive handler
		SyslogManager.getInstance().start(new SyslogReceiveHandlerImpl());
	}
	
	private void stopSyslogHandler() {

		// Stop receive handler
		SyslogManager.getInstance().stop();
		
		// Stop Analyzer
		WorkerPoolManager.getInstance().stop();
	}
}
