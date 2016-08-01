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
	private BigDecimal ip_count;
	private BigDecimal ip_total;
	private BigDecimal total_usage;
	private BigDecimal rgip_count;
	private BigDecimal range_total;
	private BigDecimal alloc_usage;

	public ViewNetworkIpStatus() {
	}

	public BigDecimal getAllocUsage() {
		return this.alloc_usage;
	}

	public void setAllocUsage(BigDecimal allocUsage) {
		this.alloc_usage = allocUsage;
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

	public BigDecimal getIpCount() {
		return this.ip_count;
	}

	public void setIpCount(BigDecimal ipCount) {
		this.ip_count = ipCount;
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

	public BigDecimal getRgipCount() {
		return this.rgip_count;
	}

	public void setRgipCount(BigDecimal rgipCount) {
		this.rgip_count = rgipCount;
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

	public BigDecimal getTotalUsage() {
		return this.total_usage;
	}

	public void setTotalUsage(BigDecimal totalUsage) {
		this.total_usage = totalUsage;
	}

}
