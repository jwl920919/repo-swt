package com.shinwootns.common.auth;

public class LdapGroup {

	private String groupDn;
	private String groupName;
	private String groupType;
	
	public String getGroupDn() {
		return groupDn;
	}
	public void setGroupDn(String groupDn) {
		this.groupDn = groupDn;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
				.append("dn=").append(groupDn)
				.append(", name=").append(groupName)
				.append(", type=").append(groupType)
				.toString();
	}
}
