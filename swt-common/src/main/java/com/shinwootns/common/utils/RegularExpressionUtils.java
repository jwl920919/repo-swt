package com.shinwootns.common.utils;

public class RegularExpressionUtils {
	
	public static final String PATTERN_USER_NAME = "/^[a-z0-9_-]{3,16}$/";
	public static final String PATTERN_PASSWORD = "/^[a-z0-9_-]{6,18}$/";
	public static final String PATTERN_HEX_VALUE = "/^#?([a-f0-9]{6}|[a-f0-9]{3})$/";								// ex) #fac113
	public static final String PATTERN_EMAIL = "/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/";			// ex) songagi@gmail.com
	public static final String PATTERN_URL = "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w_\\.-]*)*\\/?$/";	// ex) http://test.abc.com/about
	public static final String PATTERN_IP = "/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/";
	public static final String PATTERN_REGIDENT_CODE = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])-[1-4]\\d{6}";
}
