package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dhcp_ip_status database table.
 * 
 */
@Embeddable
public class DhcpIpStatusPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="site_id")
	private Integer siteId;

	private String ipaddr;

	public DhcpIpStatusPK() {
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
		if (!(other instanceof DhcpIpStatusPK)) {
			return false;
		}
		DhcpIpStatusPK castOther = (DhcpIpStatusPK)other;
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