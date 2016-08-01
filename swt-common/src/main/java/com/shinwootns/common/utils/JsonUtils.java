package com.shinwootns.common.utils;

import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	public static String getValueToString(JsonElement jEle, String key, String defaultValue) {
		
		if (jEle == null || jEle instanceof JsonObject == false)
			return defaultValue;
		
		JsonElement dataEle = jEle.getAsJsonObject().get(key);
		
		if (dataEle == null)
			return defaultValue;

		return dataEle.getAsString();
	}

	public static String getMergeValueToString(JsonElement jEle, String key, String defaultValue) {
		
		if (jEle == null || jEle instanceof JsonObject == false)
			return defaultValue;
		
		JsonElement dataEle = jEle.getAsJsonObject().get(key);
		
		if (dataEle == null)
			return defaultValue;
		
		if (dataEle instanceof JsonArray) {
			
			StringBuilder sb = new StringBuilder();
			
			JsonArray jArray = dataEle.getAsJsonArray();
			
			Iterator<JsonElement> iter = jArray.iterator();

			while(iter != null && iter.hasNext()) {
				
				JsonElement child = iter.next();
				
				if (sb.length() > 0)
 					sb.append(",");
				
				sb.append( child.getAsString() );
			}
			
			return sb.toString();
		}
		else {
			return dataEle.getAsString();
		}
	}
	
	public static Long getValueToLong(JsonObject jObj, String key, Long defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		JsonElement ele = jObj.get(key);
		
		if (ele == null)
			return defaultValue;
		
		return ele.getAsNumber().longValue();
	}
	
	public static Integer getValueToInteger(JsonObject jObj, String key, Integer defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		JsonElement ele = jObj.get(key);
		
		if (ele == null)
			return defaultValue;
		
		return ele.getAsNumber().intValue();
	}
	
	public static Boolean getValueToBoolean(JsonObject jObj, String key, Boolean defaultValue) {
		
		if (jObj == null)
			return defaultValue;
		
		JsonElement ele = jObj.get(key);
		
		if (ele == null)
			return defaultValue;
		
		return ele.getAsBoolean();
	}
	
	public static Timestamp getValueToTimestamp(JsonObject jObj, String key, Long defaultValue) {
		
		
		if (jObj == null) {
			if (defaultValue == null)
				return null;
			else
				return new Timestamp(defaultValue);
		}
		
		JsonElement ele = jObj.get(key);

		return new Timestamp(ele.getAsNumber().longValue());
	}
	//endregion
	
	public static String serialize(Object obj){
		return (new Gson()).toJson(obj);
	}
	
	public Object deserialize(String json, Type typeof){
		return (new Gson()).fromJson(json, typeof);
	}

	public static String toPrettyString(JsonElement ele) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(ele);
	}
	
	/*
	public static LinkedHashMap<Integer, String> getValueToString(JsonElement ele, String[] keys) {
		
		LinkedHashMap<Integer, String> mapResult = new LinkedHashMap<Integer, String>(); 
		
		LinkedList<JsonElement> listEle = new LinkedList<JsonElement>();
		listEle.add(ele);
		
		for(String key : keys) {
			
			listEle = lookupChild(listEle, key);
		}
		
		if (listEle != null) {
			for(JsonElement result : listEle) {
				System.out.println(result.toString());
			}
		}
		
		return mapResult;
	}
	
	private static LinkedList<JsonElement> lookupChild(LinkedList<JsonElement> listEle, String key) {

		LinkedList<JsonElement> listResult = new LinkedList<JsonElement>();
		
		for(JsonElement ele : listEle) {
		
			if (ele instanceof JsonArray) {
				JsonArray jArray = ele.getAsJsonArray();
				
				for(int i=0; i<jArray.size(); i++) {
					JsonElement childEle = jArray.get(i);
					
					if (childEle instanceof JsonArray) {
						//LinkedList<JsonElement> listEle = lookupChild(childEle, key);
						//listResult.addAll(listEle);
					}
					else if (childEle instanceof JsonObject) {
						JsonElement result = childEle.getAsJsonObject().get(key);
						
						if (result != null)
							listResult.add(result);
					}
				}
			}
			else if (ele instanceof JsonObject) {
				
				JsonObject jObj = ele.getAsJsonObject();
				
				JsonElement result = jObj.get(key);
				
				if (result != null)
					listResult.add(result);
				
				return listResult;
			}
		}
		
		return listResult;
	}*/
}
