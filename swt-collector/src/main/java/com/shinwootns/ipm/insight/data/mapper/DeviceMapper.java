package com.shinwootns.ipm.insight.data.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shinwootns.data.entity.DeviceDhcp;
import com.shinwootns.data.entity.DeviceIp;
import com.shinwootns.data.entity.DeviceNetwork;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.data.entity.DeviceSysOID;
import com.shinwootns.data.entity.InterfaceCam;
import com.shinwootns.data.entity.InterfaceInfo;
import com.shinwootns.data.entity.InterfaceIp;
import com.shinwootns.data.entity.SiteInfo;

@Mapper
public interface DeviceMapper {

	// Site Info
	SiteInfo selectSiteInfoByCode(@Param("site_code")String site_code);
	
	List<DeviceSysOID> selectSysOID();
	
	// Device SNMP
	List<DeviceSnmp> selectDeviceSnmp(@Param("site_id")int site_id, @Param("insight_host")String insight_host);
	
	// DHCP Device
	DeviceDhcp selectDeviceDhcp(@Param("site_id")int site_id);
	List<DeviceIp>selectDeviceIP(@Param("site_id")int site_id);
	
	// Insight
	DeviceInsight selectInsightByHost(@Param("host")String host);
	int insertInsight(DeviceInsight insight);
	int updateInsight(DeviceInsight insight);
	void updateInsightMaster(@Param("site_id")int site_id, @Param("host")String host);
	
	// Network Device
	int updateNetworkDevice(DeviceNetwork device);
	
	// Interface Info
	List<InterfaceInfo> selectInterfaceInfo(@Param("device_id")int device_id);
	int insertInterfaceInfo(InterfaceInfo inf);
	int updateInterfaceInfo(InterfaceInfo inf);
	int deleteInterfaceInfo(@Param("device_id")int device_id, @Param("if_number")int if_number);
	
	// Interface IP
	List<InterfaceIp> selectInterfaceIp(@Param("device_id")int device_id);
	int insertInterfaceIp(InterfaceIp ifIp);
	int updateInterfaceIp(InterfaceIp ifIp);
	int deleteInterfaceIp(@Param("device_id")int device_id, @Param("if_number")int if_number, @Param("if_ipaddr")String if_ipaddr);
	
	// Interface CAM
	List<InterfaceCam> selectInterfaceCam(@Param("device_id")int device_id);
	int insertInterfaceCam(InterfaceCam cam);
	int updateInterfaceCam(InterfaceCam cam);
	int deleteInterfaceCam(@Param("device_id")int device_id, @Param("if_number")int if_number, @Param("macaddr")String macaddr);
}
