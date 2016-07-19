package com.shinwootns.common.auth;

public abstract class UserAuthenticatorBase {

	public abstract boolean Authenticate(String userId, String passowrd) throws Exception;

	public abstract boolean Authenticate(String userId, String passowrd, String macAddr) throws Exception;
}
