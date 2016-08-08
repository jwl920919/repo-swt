package com.shinwootns.ipm.service.auth;

public class AuthResult {
	
	// result
	private boolean is_check;
	private boolean is_login;
	private String message;
	
	public boolean isIsCheck() {
		return is_check;
	}
	public void setIsCheck(boolean is_check) {
		this.is_check = is_check;
	}
	public boolean isIsLogin() {
		return is_login;
	}
	public void setIsLogin(boolean is_login) {
		this.is_login = is_login;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
