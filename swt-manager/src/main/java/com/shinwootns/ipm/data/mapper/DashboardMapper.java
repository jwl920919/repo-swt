package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.IpCount;
import com.shinwootns.data.entity.ViewLeaseIpStatus;
import com.shinwootns.data.entity.ViewNetworkIpStatus;

@Mapper
public interface DashboardMapper {
	
	// Network IP Status
	List<ViewNetworkIpStatus> selectViewNetworkIpStatus(@Param("site_id")Integer site_id);
	
	// Lease IP Status
	List<ViewLeaseIpStatus> selectViewLeaseIpStatus(@Param("site_id")Integer site_id, @Param("ip_type")String ip_type);
	
	// Guest IP Count
	IpCount selectGuestIpCount(@Param("site_id")Integer site_id);
}
