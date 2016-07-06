package com.shinwootns.ipm.data.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shinwootns.ipm.data.entity.AuthTypeEntity;


@Mapper
public interface AuthMapper {
	
	@Select("SELECT auth_type_id, auth_name, auth_type FROM auth_type;")
	List<AuthTypeEntity> findAll();
	
	@Select("SELECT * FROM auth_type WHERE auth_type_id= #{id};")
	AuthTypeEntity findById(@Param("id") Integer id);
}
