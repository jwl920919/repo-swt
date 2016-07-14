package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.shinwootns.ipm.data.entity.SiteEntity;

@Mapper
public interface SiteMapper {

	@Select(""
			+ " SELECT site_id, site_name, site_code "
			+ " FROM public.site_info;"
	)
	List<SiteEntity> findAll();
	
	@Select(""
			+ " SELECT site_id, site_name, site_code "
			+ " FROM public.site_info;"
			+ " WHERE site_id=#{site_id}"
	)
	SiteEntity findById(int site_id);
}
