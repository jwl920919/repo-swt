package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.shinwootns.ipm.data.entity.SiteInfo;

@Mapper
public interface SiteInfoMapper {

	/*
	@Select(""
			+ "SELECT site_id, site_name, site_code, dhcp_ipaddr, dhcp_version, dhcp_userid," 
			+ " dhcp_password, dhcp_snmp_community, dhcp_snmp_version, blacklist_enable," 
			+ " blacklist_filter_name, blacklist_time_sec, description"
			+ " FROM public.site_info;"
			)
	*/
	List<SiteInfo> findAll();
	
	SiteInfo findById(int site_id);
}
