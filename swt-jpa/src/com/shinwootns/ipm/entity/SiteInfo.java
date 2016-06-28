package com.shinwootns.ipm.entity;

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

	@Column(name="blacklist_enable")
	private Boolean blacklistEnable;

	@Column(name="blacklist_filter_name")
	private String blacklistFilterName;

	@Column(name="blacklist_time_sec")
	private Integer blacklistTimeSec;

	private String description;

	@Column(name="dhcp_ipaddr")
	private String dhcpIpaddr;

	@Column(name="dhcp_password")
	private String dhcpPassword;

	@Column(name="dhcp_snmp_community")
	private String dhcpSnmpCommunity;

	@Column(name="dhcp_snmp_version")
	private Integer dhcpSnmpVersion;

	@Column(name="dhcp_userid")
	private String dhcpUserid;

	@Column(name="dhcp_version")
	private String dhcpVersion;

	@Column(name="site_code")
	private String siteCode;

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

	public String getDhcpIpaddr() {
		return this.dhcpIpaddr;
	}

	public void setDhcpIpaddr(String dhcpIpaddr) {
		this.dhcpIpaddr = dhcpIpaddr;
	}

	public String getDhcpPassword() {
		return this.dhcpPassword;
	}

	public void setDhcpPassword(String dhcpPassword) {
		this.dhcpPassword = dhcpPassword;
	}

	public String getDhcpSnmpCommunity() {
		return this.dhcpSnmpCommunity;
	}

	public void setDhcpSnmpCommunity(String dhcpSnmpCommunity) {
		this.dhcpSnmpCommunity = dhcpSnmpCommunity;
	}

	public Integer getDhcpSnmpVersion() {
		return this.dhcpSnmpVersion;
	}

	public void setDhcpSnmpVersion(Integer dhcpSnmpVersion) {
		this.dhcpSnmpVersion = dhcpSnmpVersion;
	}

	public String getDhcpUserid() {
		return this.dhcpUserid;
	}

	public void setDhcpUserid(String dhcpUserid) {
		this.dhcpUserid = dhcpUserid;
	}

	public String getDhcpVersion() {
		return this.dhcpVersion;
	}

	public void setDhcpVersion(String dhcpVersion) {
		this.dhcpVersion = dhcpVersion;
	}

	public String getSiteCode() {
		return this.siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}