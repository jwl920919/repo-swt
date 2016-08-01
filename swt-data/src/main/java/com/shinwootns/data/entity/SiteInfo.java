package com.shinwootns.data.entity;

import java.io.Serializable;


public class SiteInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String site_name;
	private String site_code;
	private String description;
	private Boolean site_master;

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public String getSiteCode() {
		return this.site_code;
	}

	public void setSiteCode(String siteCode) {
		this.site_code = siteCode;
	}

	public String getSiteName() {
		return this.site_name;
	}

	public void setSiteName(String siteName) {
		this.site_name = siteName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getSiteMaster() {
		return site_master;
	}

	public void setSiteMaster(Boolean site_master) {
		this.site_master = site_master;
	}

}