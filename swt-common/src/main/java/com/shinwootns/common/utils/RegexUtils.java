package com.shinwootns.common.utils;

import java.util.regex.Pattern;

public class RegexUtils {
	
	public static final Pattern PATTERN_USER_NAME = Pattern.compile(""
			+ "/^[a-z0-9_-]{3,16}$/");
	
	public static final Pattern PATTERN_PASSWORD = Pattern.compile(""
			+ "/^[a-z0-9_-]{6,18}$/");
	
	public static final Pattern PATTERN_HEX_VALUE = Pattern.compile(""
			+ "/^#?([a-f0-9]{6}|[a-f0-9]{3})$/");									// ex) #fac113	

	public static final Pattern PATTERN_EMAIL = Pattern.compile(""
			+ "/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/");			// ex) songagi@gmail.com
	
	public static final Pattern PATTERN_URL = Pattern.compile(""
			+ "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w_\\.-]*)*\\/?$/");	// ex) http://test.abc.com/about
	
	public static final Pattern PATTERN_REGIDENT_CODE = Pattern.compile(""
			+ "\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])-[1-4]\\d{6}");

	public static final Pattern PATTERN_IPV4 = Pattern.compile(""
			+ "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
	
	public static final Pattern PATTERN_IPV6 = Pattern.compile(""
            + "^(((?=(?>.*?::)(?!.*::)))(::)?([0-9A-F]{1,4}::?){0,5}"
            + "|([0-9A-F]{1,4}:){6})(\\2([0-9A-F]{1,4}(::?|$)){0,2}|((25[0-5]"
            + "|(2[0-4]|1\\d|[1-9])?\\d)(\\.|$)){4}|[0-9A-F]{1,4}:[0-9A-F]{1,"
            + "4})(?<![^:]:|\\.)\\z", Pattern.CASE_INSENSITIVE);
	
	
	//region checkRegexPattern
	public boolean checkRegexPattern(String data, Pattern pattern) {
		
		if (pattern.matcher(data).matches())
			return true;
        
		return false;
	}
	
	public boolean checkRegexPattern(String data, String patternString) {
		
		Pattern pattern = Pattern.compile(patternString);
		
		if (pattern.matcher(data).matches())
			return true;
        
		return false;
	}
	//endregion
}
