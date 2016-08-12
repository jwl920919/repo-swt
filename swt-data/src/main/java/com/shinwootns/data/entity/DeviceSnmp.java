package com.shinwootns.data.entity;

import java.io.Serializable;

public class DeviceSnmp implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private Integer device_id;
	private String host;
	private String snmp_community;
	private Integer snmp_version;
	
	private Boolean fix_insight;
	private Integer insight_id;
	private String insight_host;

	public DeviceSnmp() {
	}
	
	public DeviceSnmp clone() throws CloneNotSupportedException {
		DeviceSnmp a = (DeviceSnmp)super.clone();
	  return a;
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public String getSnmpCommunity() {
		return this.snmp_community;
	}

	public void setSnmpCommunity(String snmpCommunity) {
		this.snmp_community = snmpCommunity;
	}

	public Integer getSnmpVersion() {
		return this.snmp_version;
	}

	public void setSnmpVersion(Integer snmpVersion) {
		this.snmp_version = snmpVersion;
	}

	public Boolean getFixInsight() {
		return fix_insight;
	}

	public void setFixInsight(Boolean fix_insight) {
		this.fix_insight = fix_insight;
	}

	public Integer getInsightId() {
		return insight_id;
	}

	public void setInsightId(Integer insight_id) {
		this.insight_id = insight_id;
	}

	public String getInsightHost() {
		return insight_host;
	}

	public void setInsightHost(String insight_host) {
		this.insight_host = insight_host;
	}
}