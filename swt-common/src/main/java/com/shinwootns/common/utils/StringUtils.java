package com.shinwootns.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

public class StringUtils {

	public static String escapeUnicodeString(String value) {
		return StringEscapeUtils.escapeJava(value);
	}

	public static String unescapeUnicodeString(String value) {
		return StringEscapeUtils.unescapeJava(value);
	}

	private static String removeUTF8BOMCode(String s) {
		if (s.startsWith("\uFEFF")) {
			s = s.substring(1);
		}
		return s;
	}
}
