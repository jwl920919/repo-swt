package com.shinwootns.ipm.service.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.apache.http.entity.ContentType;
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.StringUtils;

public class InfobloxWAPIHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private HttpClient restClient = new HttpClient();

	private String baseURL = "";
	private String id = "";
	private String pwd = "";
	
	public InfobloxWAPIHandler(String ipAddr, String id, String pwd) {
		this.baseURL = String.format("https://%s", ipAddr);
		this.id = id;
		this.pwd = pwd;
	}
	
	public boolean Connect() {
		
		try
		{
			// Connect
			if (restClient.Connect_Https(baseURL, id, pwd)) {
				return true;
			}
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public JSONArray getNetworkInfo() {
		
		/*[	{
				_ref: "network/ZG5zLm5ldHdvcmskMTcyLjE2LjEuMC8yNC8w:172.16.1.0/24/default",
				network: "172.16.1.0/24",
				network_view: "default"
			} 
		]*/
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("_return_type", "json");
			
			String value = restClient.Get("/wapi/v1.0/network", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONArray Parser
			return JsonUtils.parseJSONArray(value);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public JSONArray getFilterInfo() {
		
		/*[	{
				_ref: "filtermac/ZG5zLmRoY3BfbWFjX2ZpbHRlciRncm91cDE:group1",
				comment: "사용자그룹(AD_Office)",
				name: "group1"
			}
		]*/
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("_return_type", "json");
			
			String value = restClient.Get("/wapi/v2.3/filtermac", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONArray Parser
			return JsonUtils.parseJSONArray(value);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public JSONArray getRangeInfo() {
		
		/*[	{
				"_ref": "range/ZG5zLmRoY3BfcmFuZ2UkMTkyLjE2OC4xLjEwLzE5Mi4xNjguMS4yMC8vLzAv:192.168.1.10/192.168.1.20/default", 
		        "end_addr": "192.168.1.20", 
		        "network": "192.168.1.0/24", 
		        "network_view": "default", 
		        "start_addr": "192.168.1.10"
			} 
		]*/
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("_return_type", "json");
			
			String value = restClient.Get("/wapi/v1.0/range", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONArray Parser
			return JsonUtils.parseJSONArray(value);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public JSONArray getGridInfo() {
		
		/*
			[{"_ref": "grid/b25lLmNsdXN0ZXIkMA:Infoblox"}]
		 */
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("_return_type", "json");
			
			String value = restClient.Get("/wapi/v2.3/grid", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONArray Parser
			return JsonUtils.parseJSONArray(value);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public JSONArray getMacFilter(String macAddr) {
		/*[	{
				_ref: "macfilteraddress/ZG5zLmNsdXN0ZXJfbWFjX2ZpbHRlcnNldF9pdGVtJGdyb3VwMUA0MDo4ZDo1Yzo3Yjo1MDo3ZQ:40%3A8d%3A5c%3A7b%3A50%3A7e/group1",
				expiration_time: 0,
				filter: "group1",
				is_registered_user: false,
				mac: "40:8d:5c:7b:50:7e",
				never_expires: false
			}
		]*/
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("mac", macAddr);
			params.put("_return_fields", "filter,mac,username,is_registered_user,expiration_time,never_expires");
			params.put("_return_type", "json");

			String value = restClient.Get("/wapi/v1.0/macfilteraddress", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONArray Parser
			return JsonUtils.parseJSONArray(value);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public boolean insertMacFilter(String macAddr, String filterName, String userName) {
		
		// macfilteraddress/ZG5zLmNsdXN0ZXJfbWFjX2ZpbHRlcnNldF9pdGVtJGlwdF90ZXN0QDEwOjQxOjdmOjU0OjM5Ojhm:10%3A41%3A7f%3A54%3A39%3A8f/ipt_test
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("mac", macAddr);
			params.put("filter", filterName);
			params.put("username", userName);
			params.put("_return_type", "json");

			String value = restClient.Post("/wapi/v1.0/macfilteraddress", ContentType.APPLICATION_JSON, params);
			
			if (value != null && value.indexOf("macfilteraddress") == 0 )
				return true;
			
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public boolean deleteMacFilter(String macAddr) {

		// macfilteraddress/ZG5zLmNsdXN0ZXJfbWFjX2ZpbHRlcnNldF9pdGVtJGlwdF90ZXN0QDEwOjQxOjdmOjU0OjM5Ojhm:10%3A41%3A7f%3A54%3A39%3A8f/ipt_test
		
		try
		{
			// 1. Get MacFilter
			JSONArray jArray = getMacFilter(macAddr);
			
			if (jArray == null || jArray.size() == 0)
				return false;
			
			JSONObject jObj = (JSONObject)jArray.get(0);
			
			if (jObj.containsKey("_ref") == false)
				return false;
				
			// Get ref
			String ref = (String)jObj.get("_ref");
				
			// Make URL
			String deleteURL = String.format("/wapi/v1.0/%s", ref);

			// Delete
			String value = restClient.Delete(deleteURL, null, null);
			
			if (value != null && value.indexOf("macfilteraddress") == 0 )
				return true;
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public JSONArray getLeaseIPList(int splitCount) {
		/*[
		 	next_page_id: "789c55904b6ec3300c44f7bc88b3a9113949931cc19b206..."	
		 
		  result: [
					{
					"_ref": "lease/ZG5zLmxlYXNlJDAvMTkyLjE2OC4xLjIxOC8wLw:192.168.1.218/default",
					"address": "192.168.1.218",
					"binding_state": "ACTIVE",
					"client_hostname": "android-f10498439ff2a2af",
					"ends": 1468940548,
					"hardware": "94:d7:71:fc:92:19",
					"network": "192.168.1.0/24",
					"never_ends": false,
					"never_starts": false,
					"protocol": "IPV4",
					"starts": 1468854148
					},
			]
		]*/
		
		JSONArray resultArray = new JSONArray(); 
		
		try
		{
			Map params = new HashMap<String, String>();
			
			// 1. First Page
			params.put("_paging", 1);
			params.put("_max_results", splitCount);
			params.put("_return_as_object", 1);
			params.put("_return_type", "json");
			
			// return fields (address,network,binding_state,.....)
			StringBuilder sb = new StringBuilder();
			sb.append("address,network,binding_state,protocol");
			sb.append(",client_hostname,hardware,username");
			sb.append(",starts,ends");
			sb.append(",never_ends,never_starts");
			sb.append(",ipv6_duid,ipv6_iaid,ipv6_preferred_lifetime");
			sb.append(",discovered_data.last_discovered");
			//sb.append(",discovered_data.os");	// Search Only
			
			params.put("_return_fields", sb.toString());

			String value = restClient.Get("/wapi/v2.3/lease", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return resultArray;

			// next_page_id
			String nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray jIPAddr = (JSONArray)jObj.get("result");
			
			resultArray.addAll(jIPAddr);
			
			while(nextPageId != null) {
				
				params.clear();
				params.put("_max_results", splitCount);
				params.put("_page_id", nextPageId);
				params.put("_return_type", "json");

				value = restClient.Get("/wapi/v2.3/lease", params);
				
				if (value == null)
					return null;
				
				// Change unescape-unicode
				value = StringUtils.unescapeUnicodeString(value);
				
				// JSONObject Parser
				jObj = JsonUtils.parseJSONObject(value);
				
				if (jObj == null)
					return resultArray;

				// next_page_id
				nextPageId = (String)jObj.get("next_page_id");
				
				// result
				jIPAddr = (JSONArray)jObj.get("result");
				
				resultArray.addAll(jIPAddr);
			}
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return resultArray;
	}
	
	public JSONArray getFixedIPList() {
		/*[
		  	{
				"_ref": "fixedaddress/ZG5zLmZpeGVkX2FkZHJlc3MkMTkyLjE2OC4xLjExMi4wLi4:192.168.1.112/default",
				"comment": "jwlee-pc",
				"disable": false,
				"ipv4addr": "192.168.1.112",
				"mac": "40:8d:5c:7b:50:7e",
				"network": "192.168.1.0/24"
			}
		]*/
		
		try
		{
			Map params = new HashMap<String, String>();
			params.put("_return_fields", "ipv4addr,network,mac,comment,disable,name");
			params.put("_return_type", "json");
		
			String value = restClient.Get("/wapi/v2.3/fixedaddress", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONArray Parser
			return JsonUtils.parseJSONArray(value);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
}
