package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_insight database table.
 * 
 */
@Entity
@Table(name="device_insight")
@NamedQuery(name="DeviceInsight.findAll", query="SELECT d FROM DeviceInsight d")
public class DeviceInsight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="enable_collect")
	private Boolean enableCollect;

	private String host;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private Integer port;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="update_time")
	private Timestamp updateTime;

	private String version;

	public DeviceInsight() {
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Boolean getEnableCollect() {
		return this.enableCollect;
	}

	public void setEnableCollect(Boolean enableCollect) {
		this.enableCollect = enableCollect;
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

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}