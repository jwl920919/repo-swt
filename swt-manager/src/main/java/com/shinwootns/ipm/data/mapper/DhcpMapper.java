package com.shinwootns.ipm.data.mapper;

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
	
	// DHCP Range
	List<DhcpRange> selectDhcpRangeBySiteId(@Param("site_id")int site_id);
	
	// DHCP Fixed IP
	List<DhcpFixedIp> selectDhcpFixedIpBySiteId(@Param("site_id")int site_id);
	int insertDhcpFixedIp(DhcpFixedIp fixedIp);
	int updateDhcpFixedIp(DhcpFixedIp fixedIp);
	int deleteDhcpFixedIp(@Param("site_id")int site_id, @Param("ipaddr")String ipaddr);
	
	// DHCP Mac Filter
	List<DhcpMacFilter> selectDhcpFilterBySiteId(@Param("site_id")int site_id);
	int insertDhcpFilter(DhcpMacFilter range);
	int updateDhcpFilter(DhcpMacFilter range);
	int deleteDhcpFilter(@Param("site_id")int site_id, @Param("filter_name")String filter_name);
	
	// DHCP IP Status
	List<DhcpIpStatus> selectDhcpIpStatusByNetwork(@Param("site_id")int site_id, @Param("network")String network);
	int insertDhcpIpStatus(DhcpIpStatus ip);
	int updateDhcpIpStatus(DhcpIpStatus ip);
	int deleteDhcpIpStatus(@Param("site_id")int site_id, @Param("ipaddr")String ipaddr);
	
	
}
