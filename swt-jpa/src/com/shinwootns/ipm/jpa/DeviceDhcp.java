package com.shinwootns.ipm.jpa;

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

	@Column(name="device_name")
	private String deviceName;

	private String host;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String model;

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

	@Column(name="wapi_password")
	private String wapiPassword;

	@Column(name="wapi_userid")
	private String wapiUserid;

	public DeviceDhcp() {
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	public String getWapiPassword() {
		return this.wapiPassword;
	}

	public void setWapiPassword(String wapiPassword) {
		this.wapiPassword = wapiPassword;
	}

	public String getWapiUserid() {
		return this.wapiUserid;
	}

	public void setWapiUserid(String wapiUserid) {
		this.wapiUserid = wapiUserid;
	}

}