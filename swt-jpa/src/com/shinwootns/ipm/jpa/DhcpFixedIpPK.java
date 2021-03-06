package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dhcp_fixed_ip database table.
 * 
 */
@Embeddable
public class DhcpFixedIpPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="site_id")
	private Integer siteId;

	private String ipaddr;

	public DhcpFixedIpPK() {
	}
	public Integer getSiteId() {
		return this.siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getIpaddr() {
		return this.ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DhcpFixedIpPK)) {
			return false;
		}
		DhcpFixedIpPK castOther = (DhcpFixedIpPK)other;
		return 
			this.siteId.equals(castOther.siteId)
			&& this.ipaddr.equals(castOther.ipaddr);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.siteId.hashCode();
		hash = hash * prime + this.ipaddr.hashCode();
		
		return hash;
	}
}