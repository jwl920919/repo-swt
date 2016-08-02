package com.shinwootns.ipm.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.shinwootns.ipm.data.mapper.DashboardMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;

@Configuration
public class MapperConfig {
	
	@Autowired
	private EventMapper eventMapper;
	
	@Autowired
	private DataMapper dataMapper;
	
	@Autowired
	private DashboardMapper dashboardMapper;
}
