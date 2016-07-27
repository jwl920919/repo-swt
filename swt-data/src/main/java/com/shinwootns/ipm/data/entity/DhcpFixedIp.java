package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DhcpFixedIp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String network;
	private String ipaddr;
	private String comment;
	private String macaddr;
	private Boolean disable;
	private Timestamp update_time;

	public DhcpFixedIp() {
	}


	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}


	public Integer getSiteId() {
		return site_id;
	}


	public void setSiteId(Integer site_id) {
		this.site_id = site_id;
	}


	public String getNetwork() {
		return network;
	}


	public void setNetwork(String network) {
		this.network = network;
	}


	public String getIpaddr() {
		return ipaddr;
	}


	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}


	public Boolean getDisable() {
		return disable;
	}


	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

}