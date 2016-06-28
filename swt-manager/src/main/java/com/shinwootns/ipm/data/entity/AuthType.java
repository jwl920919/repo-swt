package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_type database table.
 * 
 */
//@Entity
//@Table(name="auth_type")
//@NamedQuery(name="AuthType.findAll", query="SELECT a FROM AuthType a")
public class AuthType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//@Id
	//@Column(name="auth_type_id")
	private Integer auth_type_id;

	//@Column(name="auth_name")
	private String auth_name;

	///@Column(name="auth_type")
	private String auth_type;

	public AuthType() {
	}

	public Integer getAuthTypeId() {
		return this.auth_type_id;
	}

	public void setAuthTypeId(Integer authTypeId) {
		this.auth_type_id = authTypeId;
	}

	public String getAuthName() {
		return this.auth_name;
	}

	public void setAuthName(String authName) {
		this.auth_name = authName;
	}

	public String getAuthType() {
		return this.auth_type;
	}

	public void setAuthType(String authType) {
		this.auth_type = authType;
	}

}