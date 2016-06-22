package com.shinwootns.swt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
public class SWTManagerApplication{
	
	@Autowired
	private SystemProperties properties;
	
	@Autowired
	SyslogHandlerService syslogHandlerService;

	@Bean
	public Validator configurationPropertiesValidator() {
		return new SystemPropertiesValidator();
	}

	public static void main(String[] args) {

		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(SWTManagerApplication.class);
		SpringApplication app = appBuilder.build();
		app.run(args);
	}
}
