package com.shinwootns.ipm.insight.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DhcpFixedIp;
import com.shinwootns.data.entity.DhcpIpStatus;
import com.shinwootns.data.entity.DhcpMacFilter;
import com.shinwootns.data.entity.DhcpNetwork;
import com.shinwootns.data.entity.DhcpRange;

@Mapper
public interface DhcpMapper {

	// DHCP Network
	List<DhcpNetwork> selectDhcpNetworkBySiteId(@Param("site_id")int site_id);
	int insertDhcpNetwork(DhcpNetwork network);
	int updateDhcpNetwork(DhcpNetwork network);
	int deleteDhcpNetwork(@Param("site_id")int site_id, @Param("network")String network);
	
	// DHCP Range
	List<DhcpRange> selectDhcpRangeBySiteId(@Param("site_id")int site_id);
	int insertDhcpRange(DhcpRange range);
	int updateDhcpRange(DhcpRange range);
	int deleteDhcpRange(@Param("site_id")int site_id, @Param("network")String network, @Param("start_ip")String start_ip);

	// DHCP Mac Filter
	List<DhcpMacFilter> selectDhcpFilterBySiteId(@Param("site_id")int site_id);
	int insertDhcpFilter(DhcpMacFilter range);
	int updateDhcpFilter(DhcpMacFilter range);
	int deleteDhcpFilter(@Param("site_id")int site_id, @Param("filter_name")String filter_name);
	
	// DHCP Fixed IP
	List<DhcpFixedIp> selectDhcpFixedIpBySiteId(@Param("site_id")int site_id);
	int insertDhcpFixedIp(DhcpFixedIp range);
	int updateDhcpFixedIp(DhcpFixedIp range);
	int deleteDhcpFixedIp(@Param("site_id")int site_id, @Param("ipaddr")String ipaddr);
	
	// DHCP IP Status
	List<DhcpIpStatus> selectDhcpIpStatusByNetwork(@Param("site_id")int site_id, @Param("network")String network);
	int insertDhcpIpStatus(DhcpIpStatus ip);
	int updateDhcpIpStatus(DhcpIpStatus ip);
	int deleteDhcpIpStatus(@Param("site_id")int site_id, @Param("ipaddr")String ipaddr);
}