package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_dhcp database table.
 * 
 */
@Entity
@Table(name="device_dhcp")
@NamedQuery(name="DeviceDhcp.findAll", query="SELECT d FROM DeviceDhcp d")
public class DeviceDhcp implements Serializable {
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

	@Column(name="device_name")
	private String deviceName;

	@Column(name="device_type")
	private String deviceType;

	private String host;

	@Column(name="insert_time")
	private Timestamp insertTime;

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

	public DeviceDhcp() {
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

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
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