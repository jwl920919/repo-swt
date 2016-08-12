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

	@Column(name="cluster_index")
	private Integer clusterIndex;

	@Column(name="cluster_mode")
	private String clusterMode;

	private String host;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private String ipaddr;

	@Column(name="is_master")
	private Boolean isMaster;

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

	public Integer getClusterIndex() {
		return this.clusterIndex;
	}

	public void setClusterIndex(Integer clusterIndex) {
		this.clusterIndex = clusterIndex;
	}

	public String getClusterMode() {
		return this.clusterMode;
	}

	public void setClusterMode(String clusterMode) {
		this.clusterMode = clusterMode;
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

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public Boolean getIsMaster() {
		return this.isMaster;
	}

	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
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