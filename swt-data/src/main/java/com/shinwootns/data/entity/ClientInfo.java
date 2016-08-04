package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String macaddr;
	private String comment;
	private String duid;
	private String hostname;
	private String ip_type;
	private String last_ip;
	private String model;
	private String os;
	private Timestamp update_time;
	private String vendor;

	public ClientInfo() {
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDuid() {
		return this.duid;
	}

	public void setDuid(String duid) {
		this.duid = duid;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIpType() {
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public String getLastIp() {
		return this.last_ip;
	}

	public void setLastIp(String lastIp) {
		this.last_ip = lastIp;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}