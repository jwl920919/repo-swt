package com.shinwootns.common.auth;

public class LdapUserGroupAttr {
	private String userClass= "person";
	private String userAttr= "sAMAccountName";
	private String userNameAttr= "displayName";
	private String userMemberOfAttr = "memberOf";
	
	private String groupClass = "group";
	private String groupNameAttr = "name";
	private String groupMemberAttr = "member";
	
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	public String getUserAttr() {
		return userAttr;
	}
	public void setUserAttr(String userAttr) {
		this.userAttr = userAttr;
	}
	public String getUserNameAttr() {
		return userNameAttr;
	}
	public void setUserNameAttr(String userNameAttr) {
		this.userNameAttr = userNameAttr;
	}
	public String getUserMemberOfAttr() {
		return userMemberOfAttr;
	}
	public void setUserMemberOfAttr(String userMemberOfAttr) {
		this.userMemberOfAttr = userMemberOfAttr;
	}
	
	public String getGroupClass() {
		return groupClass;
	}
	public void setGroupClass(String groupClass) {
		this.groupClass = groupClass;
	}
	public String getGroupNameAttr() {
		return groupNameAttr;
	}
	public void setGroupNameAttr(String groupNameAttr) {
		this.groupNameAttr = groupNameAttr;
	}
	public String getGroupMemberAttr() {
		return groupMemberAttr;
	}
	public void setGroupMemberAttr(String groupMemberAttr) {
		this.groupMemberAttr = groupMemberAttr;
	}
}
