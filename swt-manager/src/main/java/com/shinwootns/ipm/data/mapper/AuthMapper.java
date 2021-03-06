package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;

@Mapper
public interface AuthMapper {
	
	// Auth
	List<AuthSetup> selectAuthSetup();
	AuthSetupLdap selectAuthSetupLdap(@Param("setup_id")int setup_id);
	AuthSetupRadius selectAuthSetupRadius(@Param("setup_id")int setup_id);
	AuthSetupEsb selectAuthSetupEsb(@Param("setup_id")int setup_id);
}
