package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DeviceInsight implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private Boolean enable_collect;
	private String host;
	private Timestamp insert_time;
	private Integer port;
	private Integer site_id;
	private Timestamp update_time;
	private String version;

	public DeviceInsight() {
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public Boolean getEnableCollect() {
		return this.enable_collect;
	}

	public void setEnableCollect(Boolean enableCollect) {
		this.enable_collect = enableCollect;
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

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}