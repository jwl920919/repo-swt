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
import com.shinwootns.ipm.collector.service.WorkerPoolManager;
import com.shinwootns.ipm.collector.service.syslog.SyslogReceiveHandlerImpl;

@EnableScheduling
@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	Queue queue() {
		return new Queue("ipm.syslog", false);
	}
	
	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
				
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
