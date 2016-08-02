package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;


public class ViewLeaseIpStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private BigDecimal fixed_ip;
	private BigDecimal lease_ip;
	private BigDecimal unused_ip;
	private BigDecimal total_count;

	public ViewLeaseIpStatus() {
	}

	public BigDecimal getFixedIp() {
		return fixed_ip;
	}

	public void setFixedIp(BigDecimal fixed_ip) {
		this.fixed_ip = fixed_ip;
	}

	public BigDecimal getLeaseIp() {
		return lease_ip;
	}

	public void setLeaseIp(BigDecimal lease_ip) {
		this.lease_ip = lease_ip;
	}

	public BigDecimal getUnusedIp() {
		return unused_ip;
	}

	public void setUnusedIp(BigDecimal unused_ip) {
		this.unused_ip = unused_ip;
	}

	public BigDecimal getTotalCount() {
		return total_count;
	}

	public void setTotalCount(BigDecimal total_count) {
		this.total_count = total_count;
	}
	
	
}
