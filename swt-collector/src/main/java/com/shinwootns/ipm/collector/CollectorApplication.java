package com.shinwootns.ipm.collector;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.collector.service.RabbitMQHandler;
import com.shinwootns.ipm.collector.service.SyslogReceiveHandlerImpl;
import com.shinwootns.ipm.collector.service.WorkerPoolManager;

@EnableScheduling
@SpringBootApplication
public class CollectorApplication implements CommandLineRunner {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
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
	
	/*
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
			
			int count = 100000;
			long startTime = TimeUtils.currentTimeMilis();
			
			for(int i=1; i<=count; i++) {
				
				// Test 1
//				{
//					SyslogEntity syslog = new SyslogEntity();
//					syslog.setHost("1.1.1.1");
//					syslog.setFacility(1);
//					syslog.setSeverity(i%7);
//					syslog.setRecvTime(TimeUtils.currentTimeMilis());
//					syslog.setData(String.format("This is test message #%d", i));
//					
//					WorkerPoolManager.getInstance().addSyslogData(syslog);
//				}
				
				// Test 2
//				{
//					JSONObject jobj = new JSONObject();
//					jobj.put("host", "1.1.1.1");
//					jobj.put("facility", 1);
//					jobj.put("severity", i%7);
//					jobj.put("recv_time", TimeUtils.currentTimeMilis());
//					jobj.put("message", String.format("This is test message #%d", i));
//					
//					RabbitMQHandler.SendData(jobj, _logger);
//				}
			}
			
			try {
				while(WorkerPoolManager.getInstance().syslogQueue.size() > 0) {
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			long elapsed = TimeUtils.currentTimeMilis()-startTime;
			
			System.out.println("Test End.");
			
			System.out.println(String.format("Count: %d,  Elapsed: %d ms, CPS: %.2f", count, elapsed, 1000.0/((float)elapsed/(float)count)));
		}
	}
	*/
	
	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		ConfigurableApplicationContext context = SpringApplication.run(CollectorApplication.class, args);
				
		AppContextProvider.getInstance().setApplicationContext( context );
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Application Start...");
		
		//rabbitTemplate.convertAndSend("ipm.syslog", "Hello from RabbitMQ!");
		
		startSyslogHandler();
		
		//TestThread thread = new TestThread();
		//thread.start();
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
