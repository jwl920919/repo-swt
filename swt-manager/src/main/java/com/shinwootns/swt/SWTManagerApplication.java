package com.shinwootns.swt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
//import org.springframework.validation.Validator;
import org.springframework.validation.Validator;

import com.shinwootns.swt.property.SystemProperties;
import com.shinwootns.swt.property.SystemPropertiesValidator;
//import com.shinwootns.swt.property.SystemPropertiesValidator;
import com.shinwootns.swt.service.SyslogHandlerService;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SWTManagerApplication implements CommandLineRunner{
	
	@Autowired
	private SystemProperties properties;
	
	@Autowired
	SyslogHandlerService syslogHandlerService;

	@Bean
	public Validator configurationPropertiesValidator() {
		return new SystemPropertiesValidator();
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("=========================================");
		System.out.println("system.name: " + this.properties.getName());
		System.out.println("system.version: " + this.properties.getVersion());
		System.out.println("=========================================");
		
		System.out.println("StartService()... Call() Start");
		syslogHandlerService.startService();
		System.out.println("StartService()... Call() End");
	}
	
	public static void main(String[] args) {

		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(SWTManagerApplication.class);
		SpringApplication app = appBuilder.build();
		app.run(args);
	}
}
