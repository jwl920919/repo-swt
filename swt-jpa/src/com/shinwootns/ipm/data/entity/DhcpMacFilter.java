package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dhcp_mac_filter database table.
 * 
 */
@Entity
@Table(name="dhcp_mac_filter")
@NamedQuery(name="DhcpMacFilter.findAll", query="SELECT d FROM DhcpMacFilter d")
public class DhcpMacFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="filter_id")
	private Integer filterId;

	@Column(name="filter_desc")
	private String filterDesc;

	@Column(name="filter_name")
	private String filterName;

	@Column(name="site_id")
	private Integer siteId;

	public DhcpMacFilter() {
	}

	public Integer getFilterId() {
		return this.filterId;
	}

	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}

	public String getFilterDesc() {
		return this.filterDesc;
	}

	public void setFilterDesc(String filterDesc) {
		this.filterDesc = filterDesc;
	}

	public String getFilterName() {
		return this.filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}