package com.shinwootns.data.auth;

public class AuthResult {
	
	// result
	private String auth_type;
	private String setup_name;
	private String user_id;
	private boolean is_check;
	private boolean is_login;
	private String message;

	@Override
	public String toString() {
		return (new StringBuilder())
			.append("user_id=").append(user_id)
			.append("auth_type=").append(auth_type)
			.append(",setup_name=").append(setup_name)
			.append(",is_check=").append(is_check)
			.append(",is_login=").append(is_login)
			.append(",message=").append(message)
			.toString();
	}
	
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
		return auth_type;
	}
	public void setAuthType(String authType) {
		this.auth_type = authType;
	}
	public String getSetupName() {
		return setup_name;
	}
	public void setSetupName(String setupName) {
		this.setup_name = setupName;
	}
	public String getUserId() {
		return user_id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
}
