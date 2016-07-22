package com.shinwootns.common.auth;

public abstract class AuthChecker {
	
	public abstract boolean checkUserAuth(String userId, String passowrd) throws Exception;

	public abstract boolean checkUserAuth(String userId, String passowrd, String macAddr) throws Exception;
}
