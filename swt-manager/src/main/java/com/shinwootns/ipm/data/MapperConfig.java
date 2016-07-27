package com.shinwootns.ipm.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.shinwootns.ipm.data.mapper.DeviceMapper;
import com.shinwootns.ipm.data.mapper.DhcpMapper;
import com.shinwootns.ipm.data.mapper.EventMapper;

@Configuration
public class MapperConfig {
	
	@Autowired
	private EventMapper eventMapper;
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private DhcpMapper dhcpMapper;
}
