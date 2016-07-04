package com.shinwootns.ipm.data.entity;

import java.io.Serializable;

public class AuthType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer auth_type_id;
	private String auth_name;
	private String auth_type;

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