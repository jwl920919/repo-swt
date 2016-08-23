package com.shinwootns.data.entity;

import java.io.Serializable;


public class InterfaceCam implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private Integer if_number;
	private String macaddr;

	public InterfaceCam() {
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
		.append("[").append(if_number).append("]")
		.append(" device_id=").append(device_id)
		.append(", if_number=").append(if_number)
		.append(", macaddr=").append(macaddr)
		.toString();
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

	public String getMacaddr() {
		return macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}
}