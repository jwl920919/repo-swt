package com.shinwootns.swt.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SampleDAO {
	
	@Select("SELECT now();")
	String getCurrentTime();
}
