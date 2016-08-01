package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_mac_filter database table.
 * 
 */
@Entity
@Table(name="dhcp_mac_filter")
@NamedQuery(name="DhcpMacFilter.findAll", query="SELECT d FROM DhcpMacFilter d")
public class DhcpMacFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpMacFilterPK id;

	@Column(name="default_expiration_time")
	private Integer defaultExpirationTime;

	private Boolean disable;

	@Column(name="enforce_expiration_times")
	private Boolean enforceExpirationTimes;

	@Column(name="filter_desc")
	private String filterDesc;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="never_expires")
	private Boolean neverExpires;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DhcpMacFilter() {
	}

	public DhcpMacFilterPK getId() {
		return this.id;
	}

	public void setId(DhcpMacFilterPK id) {
		this.id = id;
	}

	public Integer getDefaultExpirationTime() {
		return this.defaultExpirationTime;
	}

	public void setDefaultExpirationTime(Integer defaultExpirationTime) {
		this.defaultExpirationTime = defaultExpirationTime;
	}

	public Boolean getDisable() {
		return this.disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Boolean getEnforceExpirationTimes() {
		return this.enforceExpirationTimes;
	}

	public void setEnforceExpirationTimes(Boolean enforceExpirationTimes) {
		this.enforceExpirationTimes = enforceExpirationTimes;
	}

	public String getFilterDesc() {
		return this.filterDesc;
	}

	public void setFilterDesc(String filterDesc) {
		this.filterDesc = filterDesc;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Boolean getNeverExpires() {
		return this.neverExpires;
	}

	public void setNeverExpires(Boolean neverExpires) {
		this.neverExpires = neverExpires;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}