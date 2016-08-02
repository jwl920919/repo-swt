package com.shinwootns.data.entity;

import java.io.Serializable;


public class AuthSetupLdapGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer setup_id;
	private String group_dn;
	private String auth_type;
	private String group_name;
	private String group_type;

	public AuthSetupLdapGroup() {
	}

	public String getAuthType() {
		return this.auth_type;
	}

	public void setAuthType(String authType) {
		this.auth_type = authType;
	}

	public String getGroupName() {
		return this.group_name;
	}

	public void setGroupName(String groupName) {
		this.group_name = groupName;
	}

	public String getGroupType() {
		return this.group_type;
	}

	public void setGroupType(String groupType) {
		this.group_type = groupType;
	}


	public Integer getSetupId() {
		return setup_id;
	}


	public void setSetupId(Integer setup_id) {
		this.setup_id = setup_id;
	}


	public String getGroupDn() {
		return group_dn;
	}


	public void setGroupDn(String group_dn) {
		this.group_dn = group_dn;
	}

}