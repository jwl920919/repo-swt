package com.shinwootns.ipm.data.entity;

import java.io.Serializable;


/**
 * The persistent class for the site_info database table.
 * 
 */
public class SiteEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String site_code;
	private String site_name;

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

}