package com.shinwootns.swt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import com.shinwootns.swt.property.SystemProperties;
import com.shinwootns.swt.property.SystemPropertiesValidator;

@SpringBootApplication
@EnableScheduling
//@PropertySource("application.properties")
public class SWTManagerApplication {
	
	@Bean
	public Validator configurationPropertiesValidator() {
		return new SystemPropertiesValidator();
	}

	@Service
	@Profile("app")
	static class Startup implements CommandLineRunner {

		@Autowired
		private SystemProperties properties;

		@Override
		public void run(String... args) {
			System.out.println("=========================================");
			System.out.println("system.name: " + this.properties.getName());
			System.out.println("system.version: " + this.properties.getVersion());
			System.out.println("=========================================");
		}
	}
	
	public static void main(String[] args) {
		
		// Spring Application Builder
		new SpringApplicationBuilder(SWTManagerApplication.class)
			.profiles("app")
			.run(args);
	}
}
