package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the auth_setup database table.
 * 
 */
@Entity
@Table(name="auth_setup")
@NamedQuery(name="AuthSetup.findAll", query="SELECT a FROM AuthSetup a")
public class AuthSetup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="setup_id")
	private Integer setupId;

	@Column(name="auth_type")
	private String authType;

	private Boolean disable;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="order_num")
	private Integer orderNum;

	@Column(name="setup_name")
	private String setupName;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="update_time")
	private Timestamp updateTime;

	public AuthSetup() {
	}

	public Integer getSetupId() {
		return this.setupId;
	}

	public void setSetupId(Integer setupId) {
		this.setupId = setupId;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Boolean getDisable() {
		return this.disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getSetupName() {
		return this.setupName;
	}

	public void setSetupName(String setupName) {
		this.setupName = setupName;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}