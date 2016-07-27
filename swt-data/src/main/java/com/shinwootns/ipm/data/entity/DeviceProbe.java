package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceProbe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private Boolean enable_collect_cam;
	private Boolean enable_control_ipv4;
	private Boolean enable_control_ipv6;
	private String host;
	private Timestamp insert_time;
	private Integer port;
	private Integer site_id;
	private Timestamp update_time;
	private String version;

	public DeviceProbe() {
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer device_id) {
		this.device_id = device_id;
	}

	public Boolean getEnableCollectCam() {
		return this.enable_collect_cam;
	}

	public void setEnableCollectCam(Boolean enableCollectCam) {
		this.enable_collect_cam = enableCollectCam;
	}

	public Boolean getEnableControlIpv4() {
		return this.enable_control_ipv4;
	}

	public void setEnableControlIpv4(Boolean enableControlIpv4) {
		this.enable_control_ipv4 = enableControlIpv4;
	}

	public Boolean getEnableControlIpv6() {
		return this.enable_control_ipv6;
	}

	public void setEnableControlIpv6(Boolean enableControlIpv6) {
		this.enable_control_ipv6 = enableControlIpv6;
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