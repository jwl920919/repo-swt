package com.shinwootns.ipm.insight.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceIp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.data.entity.SiteInfo;

@Mapper
public interface DeviceMapper {

	// Site Info
	SiteInfo selectSiteInfoByCode(@Param("site_code")String site_code);
	
	// Device SNMP
	List<DeviceSnmp> selectDeviceSnmp(@Param("site_id")int site_id, @Param("insight_host")String insight_host);
	
	// Device DHCP
	DeviceDhcp selectDeviceDhcp(@Param("site_id")int site_id);
	List<DeviceIp>selectDeviceIP(@Param("site_id")int site_id);
	
	// Device Insight
	DeviceInsight selectInsightByHost(@Param("host")String host);
	int insertInsight(DeviceInsight insight);
	int updateInsight(DeviceInsight insight);
	void updateInsightMaster(@Param("site_id")int site_id, @Param("host")String host);
}
