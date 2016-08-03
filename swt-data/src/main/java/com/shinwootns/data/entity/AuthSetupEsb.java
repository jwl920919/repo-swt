package com.shinwootns.data.entity;

import java.io.Serializable;


public class AuthSetupEsb implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer setup_id;
	private String auth_type;
	private String url;
	private String user_id;
	private String user_passwd;

	public AuthSetupEsb() {
	}

	public Integer getSetupId() {
		return this.setup_id;
	}

	public void setSetupId(Integer setupId) {
		this.setup_id = setupId;
	}

	public String getAuthType() {
		return this.auth_type;
	}

	public void setAuthType(String authType) {
		this.auth_type = authType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return this.user_id;
	}

	public void setUserId(String userId) {
		this.user_id = userId;
	}

	public String getUserPasswd() {
		return this.user_passwd;
	}

	public void setUserPasswd(String userPasswd) {
		this.user_passwd = userPasswd;
	}

}