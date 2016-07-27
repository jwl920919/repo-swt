package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.shinwootns.data.entity.EventData;

@Mapper
public interface EventMapper {
	
	@Insert(""
			+ " INSERT INTO event_log (device_id, host_ip, event_type, severity, collect_time, message)"
			+ " VALUES ("
			+ " #{device_id}" 
			+ " , #{host_ip}"
			+ " , #{event_type}" 
			+ " , #{severity}"
			+ " , #{collect_time}" 
			+ " , #{message}"
			+ ");"
	)
	public void insert(EventData eventLog);

}

