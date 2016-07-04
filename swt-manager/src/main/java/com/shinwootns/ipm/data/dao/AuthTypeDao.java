package com.shinwootns.ipm.data.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shinwootns.ipm.data.entity.AuthType;

/*
@Component
public class AuthTypeDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<AuthType> findAll() {
		return this.sqlSession.selectList("");
	}
	
	public List<AuthType> findById(int id) {
		return this.sqlSession.selectList("auth_type_id", id);
	}
}
*/