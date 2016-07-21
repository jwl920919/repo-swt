package com.shinwootns.common.utils;

import java.sql.Timestamp;
import java.util.Iterator;

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
		
		
		if (value instanceof JSONArray) {
			
			StringBuilder sb = new StringBuilder();
			
			Iterator iter = ((JSONArray)value).iterator();

			while(iter != null && iter.hasNext()) {
				
				Object data = (String)iter.next();
				
				if (sb.length() > 0)
 					sb.append(",");
				
				if (data instanceof JSONObject) {
					sb.append( ((JSONObject)data).values().toString() );
				}
				else {
					sb.append(data.toString());
				}
			}
			
			return sb.toString();
		}
		else {
			return (String)value;
		}
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
	
	public static boolean getValueToBoolean(JSONObject jObj, String key, boolean defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		Object value = jObj.get(key);
		
		if (value == null)
			return defaultValue;
		
		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		else if (value instanceof Integer) {
			return ((Integer)value) == 0 ? false : true;
		}
		else if (value instanceof String) {
			
			String tempValue = (String)value;
			if ( tempValue.toUpperCase().equals("TRUE"))
				return true;
			else if ( ((String)value).toUpperCase().equals("FALSE") )
				return false;
			else
				return defaultValue;
		}
		
		return defaultValue;
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
