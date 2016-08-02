package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;


public class ViewNetworkIpStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String ip_type;
	private String network;
	private String start_ip;
	private String end_ip;
	private String comment;
	
	private BigDecimal used_ip;
	private BigDecimal ip_total;
	private BigDecimal ip_usage;
	
	private BigDecimal range_used;
	private BigDecimal range_total;
	private BigDecimal range_usage;

	public ViewNetworkIpStatus() {
	}

	public BigDecimal getRangeUsage() {
		return this.range_usage;
	}

	public void setRangeUsage(BigDecimal range_usage) {
		this.range_usage = range_usage;
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

	public BigDecimal getUsedIp() {
		return this.used_ip;
	}

	public void setUsedIp(BigDecimal used_ip) {
		this.used_ip = used_ip;
	}

	public BigDecimal getIpTotal() {
		return this.ip_total;
	}

	public void setIpTotal(BigDecimal ipTotal) {
		this.ip_total = ipTotal;
	}

	public String getIpType() {
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public BigDecimal getRangeTotal() {
		return this.range_total;
	}

	public void setRangeTotal(BigDecimal rangeTotal) {
		this.range_total = rangeTotal;
	}

	public BigDecimal getRangeUsed() {
		return this.range_used;
	}

	public void setRangeUsed(BigDecimal range_used) {
		this.range_used = range_used;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public String getStartIp() {
		return this.start_ip;
	}

	public void setStartIp(String startIp) {
		this.start_ip = startIp;
	}

	public BigDecimal getIpUsage() {
		return this.ip_usage;
	}

	public void setIpUsage(BigDecimal ip_usage) {
		this.ip_usage = ip_usage;
	}

}
