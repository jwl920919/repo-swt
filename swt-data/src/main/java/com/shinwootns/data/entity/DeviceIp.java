package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DeviceIp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String ipaddr;
	private Integer device_id;

	public DeviceIp() {
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
}