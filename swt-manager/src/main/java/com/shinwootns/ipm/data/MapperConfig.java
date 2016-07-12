package com.shinwootns.ipm.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.shinwootns.ipm.data.mapper.EventMapper;

@Configuration
public class MapperConfig {
	
	@Autowired(required=true)
	private EventMapper eventMapper;
	
}
