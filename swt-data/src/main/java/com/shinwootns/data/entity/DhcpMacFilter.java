package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DhcpMacFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String filter_name;
	private String filter_desc;
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

}