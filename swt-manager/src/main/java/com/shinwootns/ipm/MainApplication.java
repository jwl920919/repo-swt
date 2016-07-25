package com.shinwootns.ipm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.mapper.DeviceMapper;

//@ComponentScan(basePackages = "com.shinwootns.*")
@SpringBootApplication
//@EnableScheduling
public class MainApplication implements CommandLineRunner {
	
	//@Autowired
	//private DeviceMapper deviceMapper;
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		/*
		List<DeviceDhcp> listDhcp = deviceMapper.selectDeviceDhcp();
		
		for(DeviceDhcp dhcp : listDhcp) {
			System.out.println(dhcp.getHost());
		}*/
	}
}
