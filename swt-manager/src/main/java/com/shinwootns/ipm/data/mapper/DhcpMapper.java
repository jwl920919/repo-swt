package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinwootns.ipm.data.entity.DhcpFixedIp;
import com.shinwootns.ipm.data.entity.DhcpLeaseIp;
import com.shinwootns.ipm.data.entity.DhcpMacFilter;
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
	
	// DHCP Mac Filter
	List<DhcpMacFilter> selectDhcpFilterBySiteId(int site_id);
	int insertDhcpFilter(DhcpMacFilter range);
	int updateDhcpFilter(DhcpMacFilter range);
	int deleteDhcpFilter(int site_id, String filter_name);
	
	// DHCP Lease IP
	List<DhcpLeaseIp> selectDhcpLeaseIpBySiteId(int site_id);
	int insertDhcpLeaseIp(DhcpLeaseIp ip);
	int updateDhcpLeaseIp(DhcpLeaseIp ip);
	int deleteDhcpLeaseIp(int site_id, String ip);
	
	// DHCP Fixed IP
	List<DhcpFixedIp> selectDhcpFixedIpBySiteId(int site_id);
	int insertDhcpFixedIp(DhcpFixedIp fixedIp);
	int updateDhcpFixedIp(DhcpFixedIp fixedIp);
	int deleteDhcpFixedIp(int site_id, String ip);
}
