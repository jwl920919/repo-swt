package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the site_info database table.
 * 
 */
@Entity
@Table(name="site_info")
@NamedQuery(name="SiteInfo.findAll", query="SELECT s FROM SiteInfo s")
public class SiteInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="site_id")
	private Integer siteId;

	private String description;

	@Column(name="site_code")
	private String siteCode;

	@Column(name="site_master")
	private Boolean siteMaster;

	@Column(name="site_name")
	private String siteName;

	public SiteInfo() {
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSiteCode() {
		return this.siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Boolean getSiteMaster() {
		return this.siteMaster;
	}

	public void setSiteMaster(Boolean siteMaster) {
		this.siteMaster = siteMaster;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}