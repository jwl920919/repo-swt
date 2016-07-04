package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the auth_order database table.
 * 
 */
@Embeddable
public class AuthOrderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="auth_type_id")
	private Integer authTypeId;

	@Column(name="auth_order")
	private Integer authOrder;

	public AuthOrderPK() {
	}
	public Integer getSiteId() {
		return this.siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getAuthTypeId() {
		return this.authTypeId;
	}
	public void setAuthTypeId(Integer authTypeId) {
		this.authTypeId = authTypeId;
	}
	public Integer getAuthOrder() {
		return this.authOrder;
	}
	public void setAuthOrder(Integer authOrder) {
		this.authOrder = authOrder;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AuthOrderPK)) {
			return false;
		}
		AuthOrderPK castOther = (AuthOrderPK)other;
		return 
			this.siteId.equals(castOther.siteId)
			&& this.authTypeId.equals(castOther.authTypeId)
			&& this.authOrder.equals(castOther.authOrder);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.siteId.hashCode();
		hash = hash * prime + this.authTypeId.hashCode();
		hash = hash * prime + this.authOrder.hashCode();
		
		return hash;
	}
}