package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shinwootns.ipm.data.entity.DeviceEntity;

@Mapper
public interface DeviceMapper {
	
	@Select(""
			+ " SELECT dev.*"
			+ " FROM device_info dev, SITE_INFO site"
			+ " WHERE dev.site_id = site.site_id"
	)
	List<DeviceEntity> selectDevice();

	@Select(""
			+ " SELECT dev.*"
			+ " FROM device_info dev, SITE_INFO site"
			+ " WHERE dev.site_id = site.site_id"
			+ " AND dev.device_type = #{device_type}"
	)
	List<DeviceEntity> selectDeviceByType(String device_type);
	
	
	@Update(""
			+ " UPDATE device_info"
			+ " SET	device_name=#{device_name}"
			+ " , vendor=#{vendor}"
			+ " , model=#{model}"
			+ " , service_type=#{service_type}"
			+ " , sys_oid=#{sys_oid}"
			+ " , sys_location=#{sys_location}"
			+ " , update_time = now()"
			+ " WHERE device_id = #{device_id}"
    )
	void updateDevice(DeviceEntity deviceInfo);
}
