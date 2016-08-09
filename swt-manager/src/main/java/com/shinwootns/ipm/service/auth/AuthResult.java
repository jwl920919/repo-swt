package com.shinwootns.ipm.service.auth;

public class AuthResult {
	
	// result
	private String authType;
	private String setupName;
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
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getSetupName() {
		return setupName;
	}
	public void setSetupName(String setupName) {
		this.setupName = setupName;
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
			.append("AuthType=").append(authType)
			.append(",Name=").append(setupName)
			.append(",is_check=").append(is_check)
			.append(",is_login=").append(is_login)
			.append(",message=").append(message)
			.toString();
	}
}
