package com.shinwootns.ipm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinwootns.ipm.data.entity.AuthTypeEntity;
import com.shinwootns.ipm.data.mapper.AuthTypeMapper;

//import com.shinwootns.ipm.data.entity.AuthType;
//import com.shinwootns.ipm.data.repository.AuthTypeRepository;

@RestController
public class MainController {
	
	//@Autowired
	//private AuthTypeMapper mapper;
	
	/*
	@PostConstruct
	public void startService() {
	}
	
	@PreDestroy
	public void stopService() {
	}
	*/

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
