package com.shinwootns.ipm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.shinwootns.ipm.data.entity.DeviceInfo;
import com.shinwootns.ipm.data.mapper.DeviceMapper;

//@ComponentScan(basePackages = "com.shinwootns.ipm.*")
@EnableScheduling
@SpringBootApplication
public class MainApplication implements CommandLineRunner {
	
	@Autowired
	private ApplicationProperty appProperty;
	
	public static void main(String[] args) {
		
		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(MainApplication.class);
		SpringApplication app = appBuilder.build();
		app.run(args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println( appProperty.toString() );
		
	}
}
