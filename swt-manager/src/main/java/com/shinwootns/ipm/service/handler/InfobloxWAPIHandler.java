package com.shinwootns.ipm.service.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.apache.http.entity.ContentType;
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.IPv4Range;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.common.utils.StringUtils;

public class InfobloxWAPIHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private HttpClient restClient = new HttpClient();

	private String baseURL = "";
	private String id = "";
	private String pwd = "";
	
	public class NextPageData {
		
		public JSONArray jArrayData = null;
		public String nextPageID = null;
		
		public NextPageData(JSONArray jArrayData, String nextPageID) {
			if (jArrayData != null) {
				this.jArrayData = (JSONArray)jArrayData.clone();
				this.nextPageID = nextPageID;
			}
		}
		
		public boolean IsExistNextPage() {
			if (this.nextPageID == null || this.nextPageID.isEmpty())
				return false;
			else
				return true;
		}
	}
	
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
	
	//region Network Info 
	public JSONArray getNetworkInfo() {
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v1.0/network");
			sb.append("?_return_type=json");
			
			String value = restClient.Get(sb.toString(), null);
			
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
	//endregion

	//region Filter Info
	public JSONArray getFilterInfo() {
		
		try
		{
			StringBuilder sb = new StringBuilder();

			sb.append("/wapi/v2.3/filtermac");
			sb.append("?_return_type=json");
			
			String value = restClient.Get(sb.toString(), null);
			
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
	//endregion

	//region Range Info
	public JSONArray getRangeInfo() {
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v1.0/range");
			sb.append("?_return_type=json");
			
			String value = restClient.Get(sb.toString(), null);
			
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
	//endregion

	//region Grid Info
	public JSONArray getGridInfo() {
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/grid");
			sb.append("?_return_type=json");
			
			String value = restClient.Get(sb.toString(), null);
			
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
	//endregion

	//region Collect MacFilter
	public JSONArray getMacFilter(String macAddr) {

		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v1.0/macfilteraddress");
			sb.append("?_return_type=json");
			sb.append("&mac=").append(macAddr);
			sb.append("&_return_fields=filter,mac,username,is_registered_user,expiration_time,never_expires");
			
			String value = restClient.Get(sb.toString());
			
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
	//endregion
	
	//region Insert MacFilter
	public boolean insertMacFilter(String macAddr, String filterName, String userName) {
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v1.0/macfilteraddress");
			sb.append("?mac=").append(macAddr);
			sb.append("&filter=").append(filterName);
			sb.append("&username=").append(userName);
			sb.append("&_return_type=json");

			String value = restClient.Post(sb.toString(), ContentType.APPLICATION_JSON, null);
			
			if (value != null && value.indexOf("macfilteraddress") == 0 )
				return true;
			
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region Delete MacFilter
	public boolean deleteMacFilter(String macAddr) {

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
				
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/").append(ref);

			// Delete
			String value = restClient.Delete(sb.toString(), null, null);
			
			if (value != null && value.indexOf("macfilteraddress") == 0 )
				return true;
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region Collect IPv4Address
	public NextPageData getIPv4AddressFirst(int splitCount, String network) {
		
		try
		{
			StringBuilder sb = new StringBuilder();

			sb.append("/wapi/v2.3/ipv4address");
			sb.append("?_paging=1");
			sb.append("&_max_results=").append(splitCount);
			sb.append("&_return_as_object=1");
			sb.append("&_return_type=json");
			sb.append("&network=").append(network);							// '192.168.1.0/25'
			sb.append("&_return_fields=")
				.append("ip_address,network,mac_address,names")
				.append(",is_conflict,conflict_types")
				.append(",discover_now_status")
				.append(",lease_state,status")
				.append(",types,usage,fingerprint")
				.append(",discovered_data.os,discovered_data.last_discovered");
			
			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return null;
			
			// next_page_id
			String nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray resultArray = (JSONArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public NextPageData getIPv4AddressNext(int splitCount, String nextPageId) {
		
		if (nextPageId == null || nextPageId.isEmpty())
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();

			sb.append("/wapi/v2.3/ipv4address");
			sb.append("?_return_type=json");
			sb.append("&_max_results=").append(splitCount);
			sb.append("&_page_id=").append(nextPageId);

			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return null;

			// next_page_id
			nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray resultArray = (JSONArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region Collect Ipv6address
	public NextPageData getIPv6AddressFirst(int splitCount, String network) {
		
		/*
		try
		{
			Map params = new HashMap<String, String>();
			
			// First Page
			params.put("_paging", 1);
			params.put("_max_results", splitCount);
			params.put("_return_as_object", 1);
			params.put("_return_type", "json");
			params.put("network", network);							// '192.168.1.0/25'
			params.put("_return_fields", 
					((new StringBuilder())
					.append("ip_address,network,duid,names")
					.append(",is_conflict")
					.append(",discover_now_status")
					//.append(",conflict_types")
					.append(",lease_state,status")
					.append(",types,usage")
					.append(",fingerprint")
					.append(",discovered_data.os,discovered_data.last_discovered")
					).toString()
			);
			
			// type = LEASE, DHCP_RANGE
			// usage = DHCP

			String value = restClient.Get("/wapi/v2.3/ipv6address", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return null;
			
			// next_page_id
			String nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray resultArray = (JSONArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		*/
		return null;
	}
	
	public NextPageData getIPv6AddressNext(int splitCount, String nextPageId) {
		
		if (nextPageId == null || nextPageId.isEmpty())
			return null;
		/*
		try
		{
			Map params = new HashMap<String, String>();
			params.put("_max_results", splitCount);
			params.put("_page_id", nextPageId);
			params.put("_return_type", "json");

			String value = restClient.Get("/wapi/v2.3/ipv6address", params);
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return null;

			// next_page_id
			nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray resultArray = (JSONArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		*/
		return null;
	}
	//endregion
	
	//region Collect Lease IP
	public NextPageData getLeaseIPFirst(int splitCount, String network) {
		
		JSONArray resultArray = new JSONArray(); 
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/lease");
			sb.append("?_paging=1");
			sb.append("&_max_results=").append(splitCount);
			sb.append("&_return_as_object=1");
			sb.append("&_return_type=json");

			IPv4Range range = NetworkUtils.getIPV4Range(network);
			sb.append("&address%3E=").append(range.getStartIPToString());		// > : %3E
			sb.append("&address%3C=").append(range.getEndIPToString());			// < : %3C

			sb.append("&_return_fields=")
				.append("address,network,binding_state")
				//.append(",protocol,client_hostname,hardware,username")
				.append(",starts,ends")
				.append(",never_ends,never_starts")
				.append(",ipv6_duid,ipv6_iaid,ipv6_preferred_lifetime")
				//.append(",discovered_data.last_discovered")
				;
			
			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return null;
			
			// next_page_id
			String nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray jIPAddr = (JSONArray)jObj.get("result");
			
			resultArray.addAll(jIPAddr);

			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public NextPageData getLeaseIPNext(int splitCount, String nextPageId) {
		
		if (nextPageId == null || nextPageId.isEmpty())
			return null;
		
		//System.out.println(nextPageId);
		
		JSONArray resultArray = new JSONArray(); 
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/lease");
			sb.append("?_return_type=json");
			sb.append("_max_results=").append(splitCount);
			sb.append("_page_id=").append(nextPageId);

			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JSONObject Parser
			JSONObject jObj = JsonUtils.parseJSONObject(value);
			
			if (jObj == null)
				return null;

			// next_page_id
			nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JSONArray jIPAddr = (JSONArray)jObj.get("result");
			
			resultArray.addAll(jIPAddr);
			
			return new NextPageData(resultArray, nextPageId);
			
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion

	//region Collect Fixed IP
	public JSONArray getFixedIPList() {

		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/fixedaddress");
			sb.append("?_return_type=json");
			sb.append("&_return_fields=ipv4addr,network,mac,comment,disable,name");
			
			String value = restClient.Get(sb.toString());
			
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
	//endregion
}
