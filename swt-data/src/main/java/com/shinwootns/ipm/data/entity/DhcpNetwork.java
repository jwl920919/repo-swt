package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DhcpNetwork implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String network;
	private String comment;
	private String end_ip;
	private Timestamp insert_time;
	private String start_ip;
	private Timestamp update_time;

	public DhcpNetwork() {
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEndIp() {
		return this.end_ip;
	}

	public void setEndIp(String endIp) {
		this.end_ip = endIp;
	}

	public Timestamp getInsertTime() {
		return this.insert_time;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insert_time = insertTime;
	}

	public String getStartIp() {
		return this.start_ip;
	}

	public void setStartIp(String startIp) {
		this.start_ip = startIp;
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

}