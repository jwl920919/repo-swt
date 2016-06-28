package com.shinwootns.ipm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.shinwootns.swt.data.entity.AuthType;
//import com.shinwootns.swt.data.repository.AuthTypeRepository;

@RestController
public class MainController {
	
	//@Autowired 
	//private AuthTypeRepository repository;

	@RequestMapping("/")
	public String greeting(String name) {
		String output = "Hi " + name + " !!!";
		return output;
	}
	
	/*@RequestMapping("/AuthType")
    public @ResponseBody List<AuthType> getUserList() {
        return repository.findAll();
    }*/
}
