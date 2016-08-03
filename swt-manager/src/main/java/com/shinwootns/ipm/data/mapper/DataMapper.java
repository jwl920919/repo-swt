package com.shinwootns.ipm.data.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.SiteInfo;

@Mapper
public interface DataMapper {
	
	// SiteInfo
	List<SiteInfo> selectSiteInfo();
	
	// Insight
	List<DeviceInsight> selectInsight(HashMap<String, Object> paramMap);
	
	// DHCP
	//List<DeviceDhcp> selectDhcp();
	//List<DeviceDhcp> selectDhcpBySiteId(@Param("site_id")int site_id);
	//DeviceDhcp selectDhcpByInsightHost(@Param("host")String host);
}
