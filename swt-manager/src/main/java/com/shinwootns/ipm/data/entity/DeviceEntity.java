package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

public class DeviceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private String api_password;
	private String api_type;
	private String api_userid;
	private String api_version;
	private String description;
	private String device_name;
	private String device_type;
	private String ipv4;
	private String ipv6;
	private String model;
	private Integer service_type;
	private Integer site_id;
	private String snmp_community;
	private Integer snmp_version;
	private String sys_location;
	private String sys_oid;
	private String vendor;
	
	public DeviceEntity() {}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer device_id) {
		this.device_id = device_id;
	}

	public String getApiPassword() {
		return this.api_password;
	}

	public void setApiPassword(String apiPassword) {
		this.api_password = apiPassword;
	}

	public String getApiType() {
		return this.api_type;
	}

	public void setApiType(String apiType) {
		this.api_type = apiType;
	}

	public String getApiUserid() {
		return this.api_userid;
	}

	public void setApiUserid(String apiUserid) {
		this.api_userid = apiUserid;
	}

	public String getApiVersion() {
		return this.api_version;
	}

	public void setApiVersion(String api_version) {
		this.api_version = api_version;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceName() {
		return this.device_name;
	}

	public void setDeviceName(String device_name) {
		this.device_name = device_name;
	}

	public String getDeviceType() {
		return this.device_type;
	}

	public void setDeviceType(String device_type) {
		this.device_type = device_type;
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
		return this.service_type;
	}

	public void setServiceType(Integer service_type) {
		this.service_type = service_type;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer site_id) {
		this.site_id = site_id;
	}

	public String getSnmpCommunity() {
		return this.snmp_community;
	}

	public void setSnmpCommunity(String snmp_community) {
		this.snmp_community = snmp_community;
	}

	public Integer getSnmpVersion() {
		return this.snmp_version;
	}

	public void setSnmpVersion(Integer snmp_version) {
		this.snmp_version = snmp_version;
	}

	public String getSysLocation() {
		return this.sys_location;
	}

	public void setSysLocation(String sys_location) {
		this.sys_location = sys_location;
	}

	public String getSysOid() {
		return this.sys_oid;
	}

	public void setSysOid(String sys_oid) {
		this.sys_oid = sys_oid;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}