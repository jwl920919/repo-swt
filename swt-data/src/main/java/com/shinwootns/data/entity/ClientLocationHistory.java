package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClientLocationHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String macaddr;
	private Integer site_id;
	private String client_ip;
	private String ip_type;
	private String switch_name;
	private String port_name;
	private Timestamp collect_time;

	public ClientLocationHistory() {
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
		return this.port_name;
	}

	public void setPort(String port) {
		this.port_name = port;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public String getSwitchName() {
		return this.switch_name;
	}

	public void setSwitchName(String switch_name) {
		this.switch_name = switch_name;
	}

}