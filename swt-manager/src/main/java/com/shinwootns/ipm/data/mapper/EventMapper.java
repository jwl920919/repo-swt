package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.shinwootns.data.entity.EventData;

@Mapper
public interface EventMapper {

	// Event
	int insertEventLog(EventData eventLog);

}

