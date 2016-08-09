package com.shinwootns.data.entity;

import java.io.Serializable;

public class IpCount implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer used_count;
	private Integer total_count;
	
	public Integer getUsedCount() {
		return used_count;
	}
	public void setUsedCount(Integer used_count) {
		this.used_count = used_count;
	}
	public Integer getTotalCount() {
		return total_count;
	}
	public void setTotalCount(Integer total_count) {
		this.total_count = total_count;
	}

}
