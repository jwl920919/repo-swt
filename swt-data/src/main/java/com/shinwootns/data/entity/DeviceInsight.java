package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DeviceInsight implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private Integer site_id;
	private String host;
	private Integer port;
	private String version;
	private Boolean is_master;
	private String cluster_mode;
	private Integer cluster_index;
	private Timestamp insert_time;
	private Timestamp update_time;
	

	public DeviceInsight() {
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public Boolean getIsmaster() {
		return this.is_master;
	}

	public void setIsMaster(Boolean isMaster) {
		this.is_master = isMaster;
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

	public String getClusterMode() {
		return cluster_mode;
	}

	public void setClusterMode(String cluster_mode) {
		this.cluster_mode = cluster_mode;
	}

	public Integer getClusterIndex() {
		return cluster_index;
	}

	public void setClusterIndex(Integer cluster_index) {
		this.cluster_index = cluster_index;
	}

}