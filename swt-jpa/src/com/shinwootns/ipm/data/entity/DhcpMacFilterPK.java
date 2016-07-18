package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dhcp_mac_filter database table.
 * 
 */
@Embeddable
public class DhcpMacFilterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="filter_name")
	private String filterName;

	public DhcpMacFilterPK() {
	}
	public Integer getSiteId() {
		return this.siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getFilterName() {
		return this.filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DhcpMacFilterPK)) {
			return false;
		}
		DhcpMacFilterPK castOther = (DhcpMacFilterPK)other;
		return 
			this.siteId.equals(castOther.siteId)
			&& this.filterName.equals(castOther.filterName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.siteId.hashCode();
		hash = hash * prime + this.filterName.hashCode();
		
		return hash;
	}
}