package com.shinwootns.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static long currentTimeMilis() {
		return System.currentTimeMillis();
	}

	public static String currentTimeString() {
		long time = System.currentTimeMillis();

		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return dayTime.format(new Date(time));
	}

	// format = "yyyy-MM-dd HH:mm:ss"
	public static String currentTimeString(String format) {
		long time = System.currentTimeMillis();

		SimpleDateFormat dayTime = new SimpleDateFormat(format);

		return dayTime.format(new Date(time));
	}
	
	public static String convertToStringTime(long miliSecond) {

		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return dayTime.format(new Date(miliSecond));
	}

	// format = "yyyy-MM-dd HH:mm:ss"
	public static String convertToStringTime(long miliSecond, String format) {

		SimpleDateFormat dayTime = new SimpleDateFormat(format);

		return dayTime.format(new Date(miliSecond));
	}

}
