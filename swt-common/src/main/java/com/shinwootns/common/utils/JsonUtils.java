package com.shinwootns.common.utils;

import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.rabbitmq.tools.json.JSONSerializable;

public class JsonUtils {
	
	//region Parse JSONArray
	public static JsonArray parseJsonArray(String data) {
		
		JsonElement ele = (new JsonParser()).parse(data);
		
		return ele.getAsJsonArray();
	}
	//endregion
	
	//region Parse JSONObject
	public static JsonObject parseJsonObject(String data) {
		
		JsonElement ele = (new JsonParser()).parse(data);
		
		return ele.getAsJsonObject();
	}
	//endregion
	
	//region getValueFromJson
	public static String getValueToString(JsonObject jObj, String key, String defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		JsonElement ele = jObj.get(key);
		
		if (ele == null)
			return defaultValue;
		
		
		if (ele instanceof JsonArray) {
			
			StringBuilder sb = new StringBuilder();
			
			JsonArray jArray = ele.getAsJsonArray();
			
			Iterator<JsonElement> iter = jArray.iterator();

			while(iter != null && iter.hasNext()) {
				
				JsonElement child = iter.next();
				
				if (sb.length() > 0)
 					sb.append(",");
				
				sb.append( child.getAsString() );
				
				/*
				if (child instanceof JsonObject) {
					sb.append( child.getAsString() );
				}
				else {
					sb.append( child.getAsString() );
				}*/
			}
			
			return sb.toString();
		}
		else {
			return ele.getAsString();
		}
	}
	
	public static long getValueToNumber(JsonObject jObj, String key, long defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		JsonElement ele = jObj.get(key);
		
		if (ele == null)
			return defaultValue;
		
		return ele.getAsNumber().longValue();
		
		/*
		if (value instanceof Integer)
			return (Long)value;
		else if (value instanceof String)
			return Long.parseLong((String)value);

		return (Long)value;
		*/
	}
	
	public static boolean getValueToBoolean(JsonObject jObj, String key, boolean defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		JsonElement ele = jObj.get(key);
		
		if (ele == null)
			return defaultValue;
		
		return ele.getAsBoolean();
		
		/*
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
		*/
	}
	
	public static Timestamp getValueToTimestamp(JsonObject jObj, String key, long defaultValue) {
		
		if (jObj == null)
			return new Timestamp(defaultValue);
		
		JsonElement ele = jObj.get(key);

		return new Timestamp(ele.getAsNumber().longValue());
		
		/*
		if (value == null)
			return new Timestamp(defaultValue);
		
		long time = defaultValue; 
		
		if (value instanceof Long)
			time = (Long)value;
		else if (value instanceof String)
			time = Long.parseLong((String)value);

		return new Timestamp(time);
		*/
	}
	//endregion
	
	public static String serializeFromObject(Object obj){
		Gson gson = new Gson();
		
		return gson.toJson(obj);
	}
	
	public Object deserializeToObject(String json, Type typeof){
		Gson gson = new Gson();
		return gson.fromJson(json, typeof);
	}

}
