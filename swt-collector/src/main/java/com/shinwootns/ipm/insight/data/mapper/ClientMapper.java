package com.shinwootns.ipm.insight.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.ClientInfo;

@Mapper
public interface ClientMapper {

	// Client Info
	//ClientInfo selectClientInfo(@Param("macaddr")String macaddr);
	//int insertClientInfo(ClientInfo clientInfo);
	//int updateClientInfo(ClientInfo clientInfo);
	
	int updateClientInfoFromIpStatus(@Param("site_id")int site_id, @Param("network")String network);
}
