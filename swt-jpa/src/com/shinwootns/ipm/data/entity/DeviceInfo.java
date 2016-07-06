package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_info database table.
 * 
 */
@Entity
@Table(name="device_info")
@NamedQuery(name="DeviceInfo.findAll", query="SELECT d FROM DeviceInfo d")
public class DeviceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="api_password")
	private String apiPassword;

	@Column(name="api_type")
	private String apiType;

	@Column(name="api_userid")
	private String apiUserid;

	@Column(name="api_version")
	private String apiVersion;

	private String description;

	@Column(name="device_name")
	private String deviceName;

	@Column(name="device_type")
	private String deviceType;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String ipv4;

	private String ipv6;

	private String model;

	@Column(name="service_type")
	private Integer serviceType;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="snmp_community")
	private String snmpCommunity;

	@Column(name="snmp_version")
	private Integer snmpVersion;

	@Column(name="sys_location")
	private String sysLocation;

	@Column(name="sys_oid")
	private String sysOid;

	@Column(name="update_time")
	private Timestamp updateTime;

	private String vendor;

	public DeviceInfo() {
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getApiPassword() {
		return this.apiPassword;
	}

	public void setApiPassword(String apiPassword) {
		this.apiPassword = apiPassword;
	}

	public String getApiType() {
		return this.apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getApiUserid() {
		return this.apiUserid;
	}

	public void setApiUserid(String apiUserid) {
		this.apiUserid = apiUserid;
	}

	public String getApiVersion() {
		return this.apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getIpv4() {
		return this.ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getIpv6() {
		return this.ipv6;
	}

	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSnmpCommunity() {
		return this.snmpCommunity;
	}

	public void setSnmpCommunity(String snmpCommunity) {
		this.snmpCommunity = snmpCommunity;
	}

	public Integer getSnmpVersion() {
		return this.snmpVersion;
	}

	public void setSnmpVersion(Integer snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public String getSysLocation() {
		return this.sysLocation;
	}

	public void setSysLocation(String sysLocation) {
		this.sysLocation = sysLocation;
	}

	public String getSysOid() {
		return this.sysOid;
	}

	public void setSysOid(String sysOid) {
		this.sysOid = sysOid;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}