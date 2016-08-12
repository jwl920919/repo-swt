package com.shinwootns.data.entity;

import java.io.Serializable;

public class InterfaceIp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private Integer if_number;
	private String if_ipaddr;
	private String network_ip;
	private String network_mask;

	public InterfaceIp() {
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
		.append("[").append(if_number).append("]")
		.append(" if_ipaddr=").append(if_ipaddr)
		.append(", network_ip=").append(network_ip)
		.append(", network_mask=").append(network_mask)
		.toString();
	}

	public String getNetworkIp() {
		return this.network_ip;
	}

	public void setNetworkIp(String networkIp) {
		this.network_ip = networkIp;
	}

	public String getNetworkMask() {
		return this.network_mask;
	}

	public void setNetworkMask(String networkMask) {
		this.network_mask = networkMask;
	}

	public Integer getDeviceId() {
		return device_id;
	}

	public void setDeviceId(Integer device_id) {
		this.device_id = device_id;
	}

	public Integer getIfNumber() {
		return if_number;
	}

	public void setIfNumber(Integer if_number) {
		this.if_number = if_number;
	}

	public String getIfIpaddr() {
		return if_ipaddr;
	}

	public void setIfIpaddr(String if_ipaddr) {
		this.if_ipaddr = if_ipaddr;
	}
}