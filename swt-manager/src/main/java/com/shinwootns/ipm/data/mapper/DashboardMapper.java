package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.ViewNetworkIpStatus;

@Mapper
public interface DashboardMapper {
	
	// DHCP
	List<ViewNetworkIpStatus> selectViewNetworkIpStatus(@Param("site_id")Integer site_id);
}
