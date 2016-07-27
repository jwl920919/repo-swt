package com.shinwootns.ipm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.shinwootns.*")
@SpringBootApplication
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
