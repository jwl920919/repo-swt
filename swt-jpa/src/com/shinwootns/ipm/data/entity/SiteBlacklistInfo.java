package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the site_blacklist_info database table.
 * 
 */
@Entity
@Table(name="site_blacklist_info")
@NamedQuery(name="SiteBlacklistInfo.findAll", query="SELECT s FROM SiteBlacklistInfo s")
public class SiteBlacklistInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="blacklist_id")
	private Integer blacklistId;

	@Column(name="blacklist_enable")
	private Boolean blacklistEnable;

	@Column(name="blacklist_filter_name")
	private String blacklistFilterName;

	@Column(name="blacklist_time_sec")
	private Integer blacklistTimeSec;

	private String description;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="update_time")
	private Timestamp updateTime;

	public SiteBlacklistInfo() {
	}

	public Integer getBlacklistId() {
		return this.blacklistId;
	}

	public void setBlacklistId(Integer blacklistId) {
		this.blacklistId = blacklistId;
	}

	public Boolean getBlacklistEnable() {
		return this.blacklistEnable;
	}

	public void setBlacklistEnable(Boolean blacklistEnable) {
		this.blacklistEnable = blacklistEnable;
	}

	public String getBlacklistFilterName() {
		return this.blacklistFilterName;
	}

	public void setBlacklistFilterName(String blacklistFilterName) {
		this.blacklistFilterName = blacklistFilterName;
	}

	public Integer getBlacklistTimeSec() {
		return this.blacklistTimeSec;
	}

	public void setBlacklistTimeSec(Integer blacklistTimeSec) {
		this.blacklistTimeSec = blacklistTimeSec;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
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