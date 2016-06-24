package com.shinwootns.common.utils;

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
}
