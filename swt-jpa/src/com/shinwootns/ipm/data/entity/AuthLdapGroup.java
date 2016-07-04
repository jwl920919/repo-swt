package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_ldap_group database table.
 * 
 */
@Entity
@Table(name="auth_ldap_group")
@NamedQuery(name="AuthLdapGroup.findAll", query="SELECT a FROM AuthLdapGroup a")
public class AuthLdapGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ldap_group_id")
	private Integer ldapGroupId;

	@Column(name="domain_name")
	private String domainName;

	@Column(name="group_name")
	private String groupName;

	@Column(name="group_type")
	private String groupType;

	public AuthLdapGroup() {
	}

	public Integer getLdapGroupId() {
		return this.ldapGroupId;
	}

	public void setLdapGroupId(Integer ldapGroupId) {
		this.ldapGroupId = ldapGroupId;
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
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