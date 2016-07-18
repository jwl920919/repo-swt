package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shinwootns.ipm.data.entity.DeviceEntity;

@Mapper
public interface DeviceMapper {

	// SELECT device_nfo
	List<DeviceEntity> selectDevice();
	List<DeviceEntity> selectDeviceByType(String device_type);
	DeviceEntity selectDeviceById(int device_id);
	
	void updateDevice(DeviceEntity deviceInfo);
}
