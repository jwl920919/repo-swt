package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_mac_filter database table.
 * 
 */
@Entity
@Table(name="dhcp_mac_filter")
@NamedQuery(name="DhcpMacFilter.findAll", query="SELECT d FROM DhcpMacFilter d")
public class DhcpMacFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpMacFilterPK id;

	@Column(name="filter_desc")
	private String filterDesc;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DhcpMacFilter() {
	}

	public DhcpMacFilterPK getId() {
		return this.id;
	}

	public void setId(DhcpMacFilterPK id) {
		this.id = id;
	}

	public String getFilterDesc() {
		return this.filterDesc;
	}

	public void setFilterDesc(String filterDesc) {
		this.filterDesc = filterDesc;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}