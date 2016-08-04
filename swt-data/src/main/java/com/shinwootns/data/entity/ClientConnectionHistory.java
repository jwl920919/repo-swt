package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClientConnectionHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String macaddr;
	private String client_ip;
	private Timestamp collect_time;
	private String ip_type;
	private String port;
	private Integer site_id;
	private String switch_name;

	public ClientConnectionHistory() {
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getClientIp() {
		return this.client_ip;
	}

	public void setClientIp(String clientIp) {
		this.client_ip = clientIp;
	}

	public Timestamp getCollectTime() {
		return this.collect_time;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collect_time = collectTime;
	}

	public String getIpType() {
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public String getSwitch_() {
		return this.switch_name;
	}

	public void setSwitch_(String switch_) {
		this.switch_name = switch_;
	}

}