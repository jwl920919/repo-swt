package com.shinwootns.swt.data.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shinwootns.swt.data.entity.SiteInfoEntity;

@Mapper
public interface SiteInfoDAO extends JpaRepository<SiteInfoEntity, Integer>{
	
	//@Select("SELECT now();")
	//String getCurrentTime();
}
