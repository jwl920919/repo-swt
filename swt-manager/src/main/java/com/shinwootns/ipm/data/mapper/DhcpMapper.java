package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinwootns.ipm.data.entity.DhcpNetwork;

@Mapper
public interface DhcpMapper {
	
	// Network
	List<DhcpNetwork> selectDhcpNetworkBySiteId(int site_id);
	int insertDhcpNetwork(DhcpNetwork network);
	int updateDhcpNetwork(DhcpNetwork network);
	int deleteDhcpNetwork(int site_id, String network);
	
}
