package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceDhcp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private String device_name;
	private String host;
	private Timestamp insert_time;
	private String model;
	private Integer site_id;
	private String snmp_community;
	private Integer snmp_version;
	private String sys_location;
	private String sys_oid;
	private Timestamp update_time;
	private String vendor;
	private String wapi_password;
	private String wapi_userid;

	public DeviceDhcp() {
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getDeviceName() {
		return this.device_name;
	}

	public void setDeviceName(String deviceName) {
		this.device_name = deviceName;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Timestamp getInsertTime() {
		return this.insert_time;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insert_time = insertTime;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public String getSysLocation() {
		return this.sys_location;
	}

	public void setSysLocation(String sysLocation) {
		this.sys_location = sysLocation;
	}

	public String getSysOid() {
		return this.sys_oid;
	}

	public void setSysOid(String sysOid) {
		this.sys_oid = sysOid;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getWapiPassword() {
		return this.wapi_password;
	}

	public void setWapiPassword(String wapiPassword) {
		this.wapi_password = wapiPassword;
	}

	public String getWapiUserid() {
		return this.wapi_userid;
	}

	public void setWapiUserid(String wapiUserid) {
		this.wapi_userid = wapiUserid;
	}

}