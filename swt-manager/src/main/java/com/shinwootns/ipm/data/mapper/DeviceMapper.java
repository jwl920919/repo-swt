package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.ipm.data.entity.DeviceDhcp;

@Mapper
public interface DeviceMapper {
	
	// DHCP
	List<DeviceDhcp> selectDeviceDhcp();
	List<DeviceDhcp> selectDeviceDhcpBySiteId(@Param("site_id")int site_id);

	/*
	// SELECT device_nfo
	List<DeviceEntity> selectDevice();
	List<DeviceEntity> selectDeviceByType(String device_type);
	DeviceEntity selectDeviceById(int device_id);
	
	void updateDevice(DeviceEntity deviceInfo);
	*/
}
