package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shinwootns.data.entity.SiteInfo;

@Mapper
public interface SiteMapper {

	@Select(""
			+ " SELECT site_id, site_name, site_code "
			+ " FROM public.site_info;"
	)
	List<SiteInfo> findAll();
	
	@Select(""
			+ " SELECT site_id, site_name, site_code "
			+ " FROM public.site_info;"
			+ " WHERE site_id=#{site_id}"
	)
	SiteInfo findById(@Param("site_id")int site_id);
}
