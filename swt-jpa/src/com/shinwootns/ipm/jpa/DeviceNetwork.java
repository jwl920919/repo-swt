package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_network database table.
 * 
 */
@Entity
@Table(name="device_network")
@NamedQuery(name="DeviceNetwork.findAll", query="SELECT d FROM DeviceNetwork d")
public class DeviceNetwork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="check_time")
	private Timestamp checkTime;

	private String description;

	@Column(name="device_type")
	private String deviceType;

	@Column(name="fix_insight")
	private Boolean fixInsight;

	private String host;

	@Column(name="host_name")
	private String hostName;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="insight_id")
	private Integer insightId;

	private String model;

	@Column(name="service_type")
	private Integer serviceType;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="snmp_community")
	private String snmpCommunity;

	@Column(name="snmp_result")
	private Boolean snmpResult;

	@Column(name="snmp_version")
	private Integer snmpVersion;

	@Column(name="sys_location")
	private String sysLocation;

	@Column(name="sys_oid")
	private String sysOid;

	@Column(name="update_time")
	private Timestamp updateTime;

	private String vendor;

	public DeviceNetwork() {
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Boolean getFixInsight() {
		return this.fixInsight;
	}

	public void setFixInsight(Boolean fixInsight) {
		this.fixInsight = fixInsight;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getInsightId() {
		return this.insightId;
	}

	public void setInsightId(Integer insightId) {
		this.insightId = insightId;
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

	public Boolean getSnmpResult() {
		return this.snmpResult;
	}

	public void setSnmpResult(Boolean snmpResult) {
		this.snmpResult = snmpResult;
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