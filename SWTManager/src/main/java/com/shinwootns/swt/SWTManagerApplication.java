package com.shinwootns.swt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SWTManagerApplication {
	
	public static void main(String[] args) {
		
		// Spring Application
		SpringApplication app = new SpringApplication(SWTManagerApplication.class);
		
		app.run(args);
	}
}
