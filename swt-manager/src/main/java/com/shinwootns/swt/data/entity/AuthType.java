package com.shinwootns.swt.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuthType implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="auth_type_id")
	private Integer authTypeId;

	@Column(name="auth_name")
	private String authName;

	@Column(name="auth_type")
	private String authType;

	public AuthType() {
	}

	public Integer getAuthTypeId() {
		return this.authTypeId;
	}

	public void setAuthTypeId(Integer authTypeId) {
		this.authTypeId = authTypeId;
	}

	public String getAuthName() {
		return this.authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

}
