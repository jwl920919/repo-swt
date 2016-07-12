package com.shinwootns.ipm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ComponentScan(basePackages = "com.shinwootns.*")
@SpringBootApplication
@EnableScheduling
public class MainApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		//SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(MainApplication.class);
		//SpringApplication app = appBuilder.build();
		//app.run(args);
		SpringApplication.run(MainApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
	}
}
