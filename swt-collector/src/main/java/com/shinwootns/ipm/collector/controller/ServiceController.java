package com.shinwootns.ipm.collector.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.ipm.collector.SpringBeanProvider;
import com.shinwootns.ipm.collector.config.ApplicationProperty;
import com.shinwootns.ipm.collector.data.SharedData;
import com.shinwootns.ipm.collector.service.amqp.RabbitmqHandler;
import com.shinwootns.ipm.collector.service.syslog.SyslogReceiveHandlerImpl;
import com.shinwootns.ipm.collector.worker.WorkerManager;
import com.shinwootns.ipm.data.entity.DeviceDhcp;

@RestController
public class ServiceController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required=true)
	private ApplicationProperty appProperty;
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() throws Exception {
		
		_logger.info("Start Service Controller.");
		
		// Set BeanProvider
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperty( appProperty );
		
		_logger.info(appProperty.toString());
		
		// Connect RabbitMQ
		RabbitmqHandler.getInstance().connect();
		
		// Start Work Manager
		WorkerManager.getInstance().start();

		if (appProperty.debugEnable == false || appProperty.enable_recv_syslog == true )
		{
			// Start receive handler
			SyslogManager.getInstance().start(new SyslogReceiveHandlerImpl());
		}
	}
	
	@PreDestroy
	public void stopService() {
		
		// Stop receive handler
		SyslogManager.getInstance().stop();
				
		// Stop Work Manager
		WorkerManager.getInstance().stop();
		
		// Close RabbitMQ
		RabbitmqHandler.getInstance().close();
		
		_logger.info("Stop Service Controller.");
	}
	
	public void testInitData() throws Exception {
		
		DeviceDhcp dhcp = new DeviceDhcp();
		dhcp.setDeviceId(2);
		dhcp.setSiteId(1);
		dhcp.setHost("192.168.1.11");
		dhcp.setSnmpCommunity("public");
		dhcp.setWapiUserid("admin");
		dhcp.setWapiUserid( CryptoUtils.Decode_AES128("SctL7q8ogUkfBwqqz3hP6A=="));
		
		SharedData.getInstance().dhcpDevice = dhcp;
	}
}
