package com.shinwootns.swt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;

import com.shinwootns.swt.property.SystemPropertiesValidator;

@ComponentScan
@EnableScheduling
@SpringBootApplication
public class SWTManagerApplication{
	
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
