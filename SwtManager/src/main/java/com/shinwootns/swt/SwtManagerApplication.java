package com.shinwootns.swt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwtManagerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SwtManagerApplication.class, args);
	}
}
