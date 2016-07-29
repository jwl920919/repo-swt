package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class DhcpNetwork implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String network;
	private String ip_type;
	private String start_ip;
	private String end_ip;
	private BigInteger start_num;
	private BigInteger end_num;
	private BigInteger ip_count;
	private String comment;
	private Timestamp insert_time;
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

	public String getIpType() {
		return ip_type;
	}

	public void setIpType(String ip_type) {
		this.ip_type = ip_type;
	}

	public BigInteger getStartNum() {
		return start_num;
	}

	public void setStartNum(BigInteger start_num) {
		this.start_num = start_num;
	}

	public BigInteger getEndNum() {
		return end_num;
	}

	public void setEndNum(BigInteger end_num) {
		this.end_num = end_num;
	}

	public BigInteger getIpCount() {
		return ip_count;
	}

	public void setIpCount(BigInteger ip_count) {
		this.ip_count = ip_count;
	}

}