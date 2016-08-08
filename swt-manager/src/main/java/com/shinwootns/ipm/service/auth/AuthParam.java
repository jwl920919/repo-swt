package com.shinwootns.ipm.service.auth;

public class AuthParam {
	// Input
	private String user_id;
	private String password;
	private String macaddr;
	
	public String getUserId() {
		return user_id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMacAddr() {
		return macaddr;
	}
	public void setMacAddr(String macaddr) {
		this.macaddr = macaddr;
	}
}
