package com.shinwootns.data.auth;

public class AuthParam {

	private String user_id;
	private String password;
	private String macaddr;
	private Integer setup_id;
	
	@Override
	public String toString() {
		return (new StringBuilder())
			.append("setup_id=").append(setup_id)
			.append("user_id=").append(user_id)
			.append("macaddr=").append(macaddr)
			.toString();
	}
	
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
	public Integer getSetupId() {
		return setup_id;
	}
	public void setSetupId(Integer setup_id) {
		this.setup_id = setup_id;
	}
}
