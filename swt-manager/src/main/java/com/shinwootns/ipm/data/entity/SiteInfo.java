package com.shinwootns.ipm.data.entity;

import java.io.Serializable;


/**
 * The persistent class for the site_info database table.
 * 
 */
public class SiteInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private Boolean blacklist_enable;
	private String blacklist_filter_name;
	private Integer blacklist_time_sec;
	private String description;
	private String dhcp_ipaddr;
	private String dhcp_password;
	private String dhcp_snmp_community;
	private Integer dhcp_snmp_version;
	private String dhcp_userid;
	private String dhcp_version;
	private String site_code;
	private String site_name;

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public Boolean getBlacklistEnable() {
		return this.blacklist_enable;
	}

	public void setBlacklistEnable(Boolean blacklistEnable) {
		this.blacklist_enable = blacklistEnable;
	}

	public String getBlacklistFilterName() {
		return this.blacklist_filter_name;
	}

	public void setBlacklistFilterName(String blacklistFilterName) {
		this.blacklist_filter_name = blacklistFilterName;
	}

	public Integer getBlacklistTimeSec() {
		return this.blacklist_time_sec;
	}

	public void setBlacklistTimeSec(Integer blacklistTimeSec) {
		this.blacklist_time_sec = blacklistTimeSec;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDhcpIpaddr() {
		return this.dhcp_ipaddr;
	}

	public void setDhcpIpaddr(String dhcpIpaddr) {
		this.dhcp_ipaddr = dhcpIpaddr;
	}

	public String getDhcpPassword() {
		return this.dhcp_password;
	}

	public void setDhcpPassword(String dhcpPassword) {
		this.dhcp_password = dhcpPassword;
	}

	public String getDhcpSnmpCommunity() {
		return this.dhcp_snmp_community;
	}

	public void setDhcpSnmpCommunity(String dhcpSnmpCommunity) {
		this.dhcp_snmp_community = dhcpSnmpCommunity;
	}

	public Integer getDhcpSnmpVersion() {
		return this.dhcp_snmp_version;
	}

	public void setDhcpSnmpVersion(Integer dhcpSnmpVersion) {
		this.dhcp_snmp_version = dhcpSnmpVersion;
	}

	public String getDhcpUserid() {
		return this.dhcp_userid;
	}

	public void setDhcpUserid(String dhcpUserid) {
		this.dhcp_userid = dhcpUserid;
	}

	public String getDhcpVersion() {
		return this.dhcp_version;
	}

	public void setDhcpVersion(String dhcpVersion) {
		this.dhcp_version = dhcpVersion;
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