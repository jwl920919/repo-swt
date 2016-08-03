package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class AuthSetup implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer setup_id;
	private String auth_type;
	private Integer site_id;
	private String setup_name;
	private Boolean disable;
	private Integer order_num;

	public AuthSetup() {
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

	public Boolean getDisable() {
		return this.disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Integer getOrderNum() {
		return this.order_num;
	}

	public void setOrderNum(Integer orderNum) {
		this.order_num = orderNum;
	}

	public String getSetupName() {
		return this.setup_name;
	}

	public void setSetupName(String setupName) {
		this.setup_name = setupName;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

}