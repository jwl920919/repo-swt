package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dhcp_range database table.
 * 
 */
@Embeddable
public class DhcpRangePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="site_id")
	private Integer siteId;

	private String network;

	@Column(name="start_ip")
	private String startIp;

	@Column(name="end_ip")
	private String endIp;

	public DhcpRangePK() {
	}
	public Integer getSiteId() {
		return this.siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getNetwork() {
		return this.network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getStartIp() {
		return this.startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return this.endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DhcpRangePK)) {
			return false;
		}
		DhcpRangePK castOther = (DhcpRangePK)other;
		return 
			this.siteId.equals(castOther.siteId)
			&& this.network.equals(castOther.network)
			&& this.startIp.equals(castOther.startIp)
			&& this.endIp.equals(castOther.endIp);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.siteId.hashCode();
		hash = hash * prime + this.network.hashCode();
		hash = hash * prime + this.startIp.hashCode();
		hash = hash * prime + this.endIp.hashCode();
		
		return hash;
	}
}