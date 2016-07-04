package com.shinwootns.common.utils;

import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {
	
	public static JSONArray parseJSONArray(String data) throws ParseException {
		
		Object obj = (new JSONParser()).parse(data);
		
		if (obj != null && obj instanceof JSONArray)
			return (JSONArray)obj;
		
		return null;
	}
	
	public static JSONObject parseJSONObject(String data) throws ParseException {
		
		Object obj = (new JSONParser()).parse(data);
		
		if (obj != null && obj instanceof JSONObject)
			return (JSONObject)obj;
		
		return null;
	}
	
	public static String getValueToString(JSONObject jObj, String key, String defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		Object value = jObj.get(key);
		
		if (value == null)
			return defaultValue;
		
		return (String)value;
	}
	
	public static long getValueToNumber(JSONObject jObj, String key, long defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		Object value = jObj.get(key);
		
		if (value == null)
			return defaultValue;
		
		if (value instanceof Integer)
			return (Long)value;
		else if (value instanceof String)
			return Long.parseLong((String)value);
		
		return (Long)value;
	}
	
	public static Timestamp getValueToTimestamp(JSONObject jObj, String key, long defaultValue) {
		
		if (jObj == null)
			return new Timestamp(defaultValue);
		
		Object value = jObj.get(key);

		if (value == null)
			return new Timestamp(defaultValue);
		
		long time = defaultValue; 
		
		if (value instanceof Long)
			time = (Long)value;
		else if (value instanceof String)
			time = Long.parseLong((String)value);

		return new Timestamp(time);
	}
}
