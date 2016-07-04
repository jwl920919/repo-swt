package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.shinwootns.ipm.data.entity.EventLogEntity;

@Mapper
public interface EventLogMapper {
	
	public List<EventLogEntity> findAll();
	
	public void insert(EventLogEntity eventLog);

}
