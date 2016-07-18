package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_probe database table.
 * 
 */
@Entity
@Table(name="device_probe")
@NamedQuery(name="DeviceProbe.findAll", query="SELECT d FROM DeviceProbe d")
public class DeviceProbe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="enable_collect_cam")
	private Boolean enableCollectCam;

	@Column(name="enable_control_ipv4")
	private Boolean enableControlIpv4;

	@Column(name="enable_control_ipv6")
	private Boolean enableControlIpv6;

	private String host;

	@Column(name="insert_time")
	private Timestamp insertTime;

	private Integer port;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="update_time")
	private Timestamp updateTime;

	private String version;

	public DeviceProbe() {
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Boolean getEnableCollectCam() {
		return this.enableCollectCam;
	}

	public void setEnableCollectCam(Boolean enableCollectCam) {
		this.enableCollectCam = enableCollectCam;
	}

	public Boolean getEnableControlIpv4() {
		return this.enableControlIpv4;
	}

	public void setEnableControlIpv4(Boolean enableControlIpv4) {
		this.enableControlIpv4 = enableControlIpv4;
	}

	public Boolean getEnableControlIpv6() {
		return this.enableControlIpv6;
	}

	public void setEnableControlIpv6(Boolean enableControlIpv6) {
		this.enableControlIpv6 = enableControlIpv6;
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