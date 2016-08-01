package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DhcpMacFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String filter_name;
	private String filter_desc;
	private Boolean disable;
	private Boolean never_expires;
	private Integer default_expiration_time;
	private Boolean enforce_expiration_times;
	private Timestamp insert_time;
	private Timestamp update_time;

	public Integer getSiteId() {
		return site_id;
	}

	public void setSiteId(Integer site_id) {
		this.site_id = site_id;
	}

	public String getFilterName() {
		return filter_name;
	}

	public void setFilterName(String filter_name) {
		this.filter_name = filter_name;
	}

	public DhcpMacFilter() {
	}

	public String getFilterDesc() {
		return this.filter_desc;
	}

	public void setFilterDesc(String filterDesc) {
		this.filter_desc = filterDesc;
	}

	public Timestamp getInsertTime() {
		return this.insert_time;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insert_time = insertTime;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}

	public Integer getDefaultExpiration_time() {
		return default_expiration_time;
	}

	public void setDefaultExpiration_time(Integer default_expiration_time) {
		this.default_expiration_time = default_expiration_time;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Boolean getNeverExpires() {
		return never_expires;
	}

	public void setNeverExpires(Boolean never_expires) {
		this.never_expires = never_expires;
	}

	public Boolean getEnforceExpiration_times() {
		return enforce_expiration_times;
	}

	public void setEnforceExpiration_times(Boolean enforce_expiration_times) {
		this.enforce_expiration_times = enforce_expiration_times;
	}

}