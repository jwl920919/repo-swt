package com.shinwootns.ipm.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.shinwootns.ipm.data.entity.SiteInfoEntity;

@Mapper
public interface SiteMapper {

	@Select(""
			+ " SELECT site_id, site_name, site_code "
			+ " FROM public.site_info;"
	)
	List<SiteInfoEntity> findAll();
	
	@Select(""
			+ " SELECT site_id, site_name, site_code "
			+ " FROM public.site_info;"
			+ " WHERE site_id=#{site_id}"
	)
	SiteInfoEntity findById(int site_id);
}
