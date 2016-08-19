package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.EventCount;
import com.shinwootns.data.entity.EventData;

@Mapper
public interface EventMapper {

	// Event
	int insertEventLog(EventData eventLog);

	// SyslogCount
	List<EventCount> selectSyslogCount(@Param("site_id")int site_id, @Param("period_hour")int period_hour, @Param("interval_sec")int interval_sec);
}

