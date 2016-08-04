package com.shinwootns.ipm.collector.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.ClientInfo;

@Mapper
public interface ClientMapper {

	// Client Info
	ClientInfo selectClientInfo(@Param("macaddr")String macaddr);
	int insertClientInfo(ClientInfo clientInfo);
	int updateClientInfo(ClientInfo clientInfo);
}
