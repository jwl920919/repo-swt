package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.ipm.data.entity.DeviceDhcp;
import com.shinwootns.ipm.data.entity.DeviceInsight;

@Mapper
public interface DeviceMapper {
	
	// DHCP
	List<DeviceDhcp> selectDhcp();
	List<DeviceDhcp> selectDhcpBySiteId(@Param("site_id")int site_id);

	
	// Insight
	List<DeviceInsight> selectInsight();
	DeviceInsight selectInsightByDeviceId(@Param("device_id")int device_id);
	int insertInsight(DeviceInsight device);
	int updateInsight(DeviceInsight device);
	int deleteInsight(DeviceInsight device);
	
	/*
	// SELECT device_nfo
	List<DeviceEntity> selectDevice();
	List<DeviceEntity> selectDeviceByType(String device_type);
	DeviceEntity selectDeviceById(int device_id);
	
	void updateDevice(DeviceEntity deviceInfo);
	*/
}
