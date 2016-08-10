package com.shinwootns.ipm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.shinwootns.*")
@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	
	private final static Logger _logger = LoggerFactory.getLogger(MainApplication.class);
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext appContext = null;
		try
		{
			appContext = SpringApplication.run(MainApplication.class, args);
		}
		catch(Exception ex) {
			
			_logger.error(ex.getMessage(), ex);
			
			WorkerManager.getInstance().TerminateApplication();
		}
	}
	
	@Override
	public void run(String... arg0) throws Exception {
	}
}
