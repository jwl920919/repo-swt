package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DeviceNetwork implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private String description;
	private String device_type;
	private String host;
	private String host_name;
	private Timestamp insert_time;
	private String model;
	private Integer service_type;
	private Integer site_id;
	private String snmp_community;
	private Integer snmp_version;
	private String sys_location;
	private String sys_oid;
	private Timestamp update_time;

	private String vendor;

	public DeviceNetwork() {
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceType() {
		return this.device_type;
	}

	public void setDeviceType(String deviceType) {
		this.device_type = deviceType;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHostName() {
		return this.host_name;
	}

	public void setHostName(String hostName) {
		this.host_name = hostName;
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

	public Integer getServiceType() {
		return this.service_type;
	}

	public void setServiceType(Integer serviceType) {
		this.service_type = serviceType;
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

}