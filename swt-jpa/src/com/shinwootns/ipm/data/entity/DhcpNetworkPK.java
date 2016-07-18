package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dhcp_network database table.
 * 
 */
@Embeddable
public class DhcpNetworkPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="site_id")
	private Integer siteId;

	private String network;

	public DhcpNetworkPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DhcpNetworkPK)) {
			return false;
		}
		DhcpNetworkPK castOther = (DhcpNetworkPK)other;
		return 
			this.siteId.equals(castOther.siteId)
			&& this.network.equals(castOther.network);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.siteId.hashCode();
		hash = hash * prime + this.network.hashCode();
		
		return hash;
	}
}