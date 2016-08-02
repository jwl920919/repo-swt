package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the auth_setup_ldap_group database table.
 * 
 */
@Embeddable
public class AuthSetupLdapGroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="setup_id")
	private Integer setupId;

	@Column(name="group_dn")
	private String groupDn;

	public AuthSetupLdapGroupPK() {
	}
	public Integer getSetupId() {
		return this.setupId;
	}
	public void setSetupId(Integer setupId) {
		this.setupId = setupId;
	}
	public String getGroupDn() {
		return this.groupDn;
	}
	public void setGroupDn(String groupDn) {
		this.groupDn = groupDn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AuthSetupLdapGroupPK)) {
			return false;
		}
		AuthSetupLdapGroupPK castOther = (AuthSetupLdapGroupPK)other;
		return 
			this.setupId.equals(castOther.setupId)
			&& this.groupDn.equals(castOther.groupDn);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.setupId.hashCode();
		hash = hash * prime + this.groupDn.hashCode();
		
		return hash;
	}
}