package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinwootns.ipm.data.entity.DhcpNetwork;
import com.shinwootns.ipm.data.entity.DhcpRange;

@Mapper
public interface DhcpMapper {
	
	// DHCP Network
	List<DhcpNetwork> selectDhcpNetworkBySiteId(int site_id);
	int insertDhcpNetwork(DhcpNetwork network);
	int updateDhcpNetwork(DhcpNetwork network);
	int deleteDhcpNetwork(int site_id, String network);
	
	// DHCP Range
	List<DhcpRange> selectDhcpRangeBySiteId(int site_id);
	int insertDhcpRange(DhcpRange range);
	int updateDhcpRange(DhcpRange range);
	int deleteDhcpRange(int site_id, String network, String startIP);
}
