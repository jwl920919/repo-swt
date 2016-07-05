package com.shinwootns.ipm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.ApplicationProperties;
import com.shinwootns.ipm.MainApplication;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.entity.AuthTypeEntity;
import com.shinwootns.ipm.data.mapper.AuthTypeMapper;
import com.shinwootns.ipm.data.mapper.EventLogMapper;
import com.shinwootns.ipm.service.WorkerPoolManager;

//import com.shinwootns.ipm.data.entity.AuthType;
//import com.shinwootns.ipm.data.repository.AuthTypeRepository;

@RestController
@SpringApplicationConfiguration(classes = MainApplication.class)
public class MainController {
	
	private final Log _logger = LogFactory.getLog(this.getClass());
	
	//@Autowired
	//private AuthTypeMapper mapper;
	
	@Autowired(required=true)
	private EventLogMapper eventLogMapper;

	@Autowired(required=true)
	private ApplicationProperties appProperties;
	
	@Autowired
	private ApplicationContext context;
	
	@PostConstruct
	public void startService() {
		
		_logger.info("Start service all.");
		
		System.out.println(context.toString());
		
		SpringBeanProvider.getInstance().setApplicationContext( context );
		SpringBeanProvider.getInstance().setApplicationProperties( appProperties );
		
		// Start
		WorkerPoolManager.getInstance().start();
	}
	
	@PreDestroy
	public void stopService() {
		// Stop
		WorkerPoolManager.getInstance().stop();
		
		//_logger.info("Stop service all.");
	}

	/*
	@RequestMapping("/")
	public String greeting(String name) {
		String output = "Hi " + name + " !!!";
		return output;
	}
	*/
	
	/*@RequestMapping("/AuthType")
    public @ResponseBody List<AuthTypeEntity> getUserList() {
		
		List<AuthTypeEntity> result = mapper.findAll();
		
        return result;
    }*/
}
