package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DhcpRange implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String network;
	private String start_ip;
	private String end_ip;
	private Timestamp insert_time;
	private Timestamp update_time;

	public DhcpRange() {
	}

	public Timestamp getInsertTime() {
		return this.insert_time;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insert_time = insertTime;
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

	public String getStartIp() {
		return start_ip;
	}

	public void setStartIp(String start_ip) {
		this.start_ip = start_ip;
	}

	public String getEndIp() {
		return end_ip;
	}

	public void setEndIp(String end_ip) {
		this.end_ip = end_ip;
	}

}