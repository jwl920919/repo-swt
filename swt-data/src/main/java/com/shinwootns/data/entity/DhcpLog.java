package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DhcpLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private Integer device_id;
	private String dhcp_ip;
	private String dhcp_type;
	private String ip_type;
	private String client_ip;
	private String client_mac;
	private String client_duid;
	private Timestamp collect_time;

	public DhcpLog() {
	}

	public String getClientDuid() {
		return this.client_duid;
	}

	public void setClientDuid(String clientDuid) {
		this.client_duid = clientDuid;
	}

	public String getClientIp() {
		return this.client_ip;
	}

	public void setClientIp(String clientIp) {
		this.client_ip = clientIp;
	}

	public String getClientMac() {
		return this.client_mac;
	}

	public void setClientMac(String clientMac) {
		this.client_mac = clientMac;
	}

	public Timestamp getCollectTime() {
		return this.collect_time;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collect_time = collectTime;
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getDhcpIp() {
		return this.dhcp_ip;
	}

	public void setDhcpIp(String dhcpIp) {
		this.dhcp_ip = dhcpIp;
	}

	public String getDhcpType() {
		return this.dhcp_type;
	}

	public void setDhcpType(String dhcpType) {
		this.dhcp_type = dhcpType;
	}

	public String getIpType() {
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

}