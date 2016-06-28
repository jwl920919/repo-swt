package com.shinwootns.ipm.data.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shinwootns.ipm.data.entity.AuthType;


@Mapper
public interface AuthTypeMapper {
	
	@Select("SELECT auth_type_id, auth_name, auth_type FROM auth_type;")
	List<AuthType> findAll();
	
	@Select("SELECT * FROM auth_type WHERE auth_type_id= #{id};")
	AuthType findById(@Param("id") Integer id);
}
