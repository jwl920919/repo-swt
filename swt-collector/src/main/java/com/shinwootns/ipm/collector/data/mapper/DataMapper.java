package com.shinwootns.ipm.collector.data.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.SiteInfo;

@Mapper
public interface DataMapper {

	// Site Info
	SiteInfo selectSiteInfoByCode(@Param("site_code")String site_code);
	
	// DHCP
	DeviceDhcp selectDeviceDhcp(@Param("site_id")int site_id);
	
	// Insight
	DeviceInsight selectInsightByHost(@Param("host")String host);
	int insertInsight(DeviceInsight insight);
	int updateInsight(DeviceInsight insight);
	void updateInsightMaster(@Param("site_id")int site_id, @Param("host")String host);
}
