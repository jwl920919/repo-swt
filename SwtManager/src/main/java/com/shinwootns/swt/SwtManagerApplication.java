package com.shinwootns.swt;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwtManagerApplication {
	
	public static void main(String[] args) {
		
		// Spring Application
		SpringApplication app = new SpringApplication(SwtManagerApplication.class);
		
		app.run(args);
	}
}
