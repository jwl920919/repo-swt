package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_setup_ldap_group database table.
 * 
 */
@Entity
@Table(name="auth_setup_ldap_group")
@NamedQuery(name="AuthSetupLdapGroup.findAll", query="SELECT a FROM AuthSetupLdapGroup a")
public class AuthSetupLdapGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuthSetupLdapGroupPK id;

	@Column(name="auth_type")
	private String authType;

	@Column(name="group_name")
	private String groupName;

	@Column(name="group_type")
	private String groupType;

	public AuthSetupLdapGroup() {
	}

	public AuthSetupLdapGroupPK getId() {
		return this.id;
	}

	public void setId(AuthSetupLdapGroupPK id) {
		this.id = id;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}