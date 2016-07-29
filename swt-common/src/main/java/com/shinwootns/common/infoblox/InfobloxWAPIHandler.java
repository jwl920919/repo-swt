package com.shinwootns.common.infoblox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.entity.ContentType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.common.utils.StringUtils;
import com.shinwootns.common.utils.ip.IPNetwork;

public class InfobloxWAPIHandler {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private HttpClient restClient = null;

	private String baseURL = "";
	private String host = "";
	private String id = "";
	private String pwd = "";
	
	//region connect / close
	public boolean connect(String host, String id, String pwd) {

		this.host = host;
		this.baseURL = ((new StringBuilder()).append("https://").append(host)).toString();
		this.id = id;
		this.pwd = pwd;
		
		try
		{
			if (restClient == null)
				restClient = new HttpClient();
			
			// Connect WAPI
			if (restClient.Connect_Https(baseURL, id, pwd) == false) {
				return false;
			}
			
			return true;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public void close() {
		try {
			if (restClient != null)
				restClient.Close();
		}
		catch(Exception ex) {}
		finally {
			restClient = null;
		}
	}
	//endregion
	
	//region [WAPI] Get Network Info 
	public JsonArray getNetworkInfo() {
		
		if (restClient == null)
			return null;

		JsonArray arrayIPv4 = null;
		JsonArray arrayIPv6 = null;
		
		try
		{
			// IPv4
			{
				StringBuilder sb = new StringBuilder();
				sb.append("/wapi/v1.0/network");
				sb.append("?_return_type=json");
				
				String value = restClient.Get(sb.toString(), null);
				
				if (value != null) {
	
					value = StringUtils.unescapeUnicodeString(value);
					
					arrayIPv4 = JsonUtils.parseJsonArray(value);
				}
			}
			
			// IPv6
			{
				StringBuilder sb = new StringBuilder();
				sb.append("/wapi/v1.0/ipv6network");
				sb.append("?_return_type=json");
				
				String value = restClient.Get(sb.toString(), null);
				
				if (value != null) {
	
					value = StringUtils.unescapeUnicodeString(value);
					
					arrayIPv6 = JsonUtils.parseJsonArray(value);
				}
			}
			
			// Merge
			JsonArray array = new JsonArray();
			
			if (arrayIPv4 != null)
				array.addAll(arrayIPv4);
			
			if (arrayIPv6 != null)
				array.addAll(arrayIPv6);
			
			return array;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion

	//region [WAPI] Get Filter Info
	public JsonArray getFilterInfo() {
		
		if (restClient == null)
			return null;
		
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
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion

	//region [WAPI] Get Range Info
	public JsonArray getRangeInfo() {
		
		if (restClient == null)
			return null;
		
		
		JsonArray arrayIPv4 = null;
		JsonArray arrayIPv6 = null;
		
		try
		{
			
			// IPv4 Range
			{
				StringBuilder sb = new StringBuilder();
				
				sb.append("/wapi/v1.0/range");
				sb.append("?_return_type=json");
				
				String value = restClient.Get(sb.toString(), null);
				
				if (value == null)
					return null;
				
				// Change unescape-unicode
				value = StringUtils.unescapeUnicodeString(value);
				
				if (value != null) {
					value = StringUtils.unescapeUnicodeString(value);
					arrayIPv4 = JsonUtils.parseJsonArray(value);
				}
			}
			
			// IPv6 Range
			{
				StringBuilder sb = new StringBuilder();
				
				sb.append("/wapi/v1.0/ipv6range");
				sb.append("?_return_type=json");
				
				String value = restClient.Get(sb.toString(), null);
				
				if (value == null)
					return null;
				
				// Change unescape-unicode
				value = StringUtils.unescapeUnicodeString(value);
				
				if (value != null) {
					value = StringUtils.unescapeUnicodeString(value);
					arrayIPv6 = JsonUtils.parseJsonArray(value);
				}
			}
			
			// Merge
			JsonArray array = new JsonArray();
			
			if (arrayIPv4 != null)
				array.addAll(arrayIPv4);
			
			if (arrayIPv6 != null)
				array.addAll(arrayIPv6);
			
			return array;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion

	//region [WAPI] Get Grid Info
	public JsonArray getGridInfo() {
		
		if (restClient == null)
			return null;
		
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
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion

	//region [WAPI] Get MacFilter
	public JsonArray getMacFilter(String macAddr) {

		if (restClient == null)
			return null;
		
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
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [WAPI] Insert MacFilter
	public boolean insertMacFilter(String macAddr, String filterName, String userName) {
		
		if (restClient == null)
			return false;
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v1.0/macfilteraddress");
			sb.append("?mac=").append(macAddr);
			sb.append("&filter=").append(filterName);
			sb.append("&username=").append(userName);
			sb.append("&_return_type=json");
			
			String value = restClient.Post(sb.toString(), ContentType.APPLICATION_JSON, null);
			
			if (value != null && value.indexOf("macfilteraddress") >= 0 )
				return true;
			
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region [WAPI] Delete MacFilter
	public boolean deleteMacFilter(String macAddr) {

		if (restClient == null)
			return false;
		
		try
		{
			// 1. Get MacFilter
			JsonArray jArray = getMacFilter(macAddr);
			
			if (jArray == null || jArray.size() == 0)
				return false;
			
			JsonObject jObj = (JsonObject)jArray.get(0);
			
			if (jObj.has("_ref") == false)
				return false;
				
			// Get ref
			String ref = (String)jObj.get("_ref").getAsString();
				
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/").append(ref);

			// Delete
			String value = restClient.Delete(sb.toString(), null, null);
			
			if (value != null && value.indexOf("macfilteraddress") >= 0 )
				return true;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region [WAPI] Get IPv4Address
	public NextPageData getIPv4AddressFirst(int splitCount, String network) {
		
		if (restClient == null)
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();

			sb.append("/wapi/v2.3/ipv4address")
			.append("?_paging=1")
			.append("&_max_results=").append(splitCount)
			.append("&_return_as_object=1")
			.append("&_return_type=json")
			.append("&network=").append(network)							// '192.168.1.0/25'
			.append("&_return_fields=")
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
			
			// JsonObject Parser
			JsonObject jObj = JsonUtils.parseJsonObject(value);
			
			if (jObj == null)
				return null;
			
			// next_page_id
			String nextPageId = JsonUtils.getValueToString(jObj, "next_page_id", "");
			
			// result
			JsonArray resultArray = (JsonArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public NextPageData getIPv4AddressNext(int splitCount, String nextPageId) {
		
		if (restClient == null)
			return null;
		
		if (nextPageId == null || nextPageId.isEmpty())
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();

			sb.append("/wapi/v2.3/ipv4address")
			.append("?_return_type=json")
			.append("&_max_results=").append(splitCount)
			.append("&_page_id=").append(nextPageId);

			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JsonObject Parser
			JsonObject jObj = JsonUtils.parseJsonObject(value);
			
			if (jObj == null)
				return null;

			// next_page_id
			nextPageId = JsonUtils.getValueToString(jObj, "next_page_id", "");
			
			// result
			JsonArray resultArray = (JsonArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [WAPI] Get Ipv6address
	public NextPageData getIPv6AddressFirst(int splitCount, String network) {
		
		if (restClient == null)
			return null;
		
		/*
		try
		{
			StringBuilder sb = new StringBuilder();
			
			// First Page
			sb.append("/wapi/v2.3/ipv6address")
			.append("?_paging=1")
			.append("&_max_results=").append(splitCount)
			.append("&_return_as_object=1")
			.append("&_return_type=json")
			.append("&network=").append(network)
			.append("&_return_fields=")
				.append("ip_address,network,duid,names")
				.append(",is_conflict")
				.append(",discover_now_status")
				//.append(",conflict_types")
				.append(",lease_state,status")
				.append(",types,usage")
				.append(",fingerprint")
				.append(",discovered_data.os,discovered_data.last_discovered");
			
			// type = LEASE, DHCP_RANGE
			// usage = DHCP

			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JsonObject Parser
			JsonObject jObj = JsonUtils.parseJsonObject(value);
			
			if (jObj == null)
				return null;
			
			// next_page_id
			String nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JsonArray resultArray = (JsonArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		*/
		return null;
	}
	
	public NextPageData getIPv6AddressNext(int splitCount, String nextPageId) {
		
		if (restClient == null)
			return null;
		
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
			
			// JsonObject Parser
			JsonObject jObj = JsonUtils.parseJsonObject(value);
			
			if (jObj == null)
				return null;

			// next_page_id
			nextPageId = (String)jObj.get("next_page_id");
			
			// result
			JsonArray resultArray = (JsonArray)jObj.get("result");
			
			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.fatal(ex.getMessage(), ex);
		}
		*/
		return null;
	}
	//endregion
	
	//region [WAPI] Get Lease IP
	public NextPageData getLeaseIPFirst(int splitCount, String network) {
		
		if (restClient == null)
			return null;
		
		JsonArray resultArray = new JsonArray(); 
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/lease");
			sb.append("?_paging=1");
			sb.append("&_max_results=").append(splitCount);
			sb.append("&_return_as_object=1");
			sb.append("&_return_type=json");
			
			//IPv4Range range = NetworkUtils.getIPV4Range(network);
			//sb.append("&address%3E=").append(range.getStartIPToString());		// > : %3E
			//sb.append("&address%3C=").append(range.getEndIPToString());			// < : %3C
			
			IPNetwork ipNetwork = new IPNetwork(network);
			sb.append("&address%3E=").append(ipNetwork.getStartIP().toString());		// > = %3E
			sb.append("&address%3C=").append(ipNetwork.getEndIP().toString());			// < = %3C

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
			
			// JsonObject Parser
			JsonObject jObj = JsonUtils.parseJsonObject(value);
			
			if (jObj == null)
				return null;
			
			// next_page_id
			String nextPageId = JsonUtils.getValueToString(jObj, "next_page_id", "");
			
			// result
			JsonArray jIPAddr = (JsonArray)jObj.get("result");
			
			if (jIPAddr.size() > 0)
				resultArray.addAll(jIPAddr);

			return new NextPageData(resultArray, nextPageId);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	public NextPageData getLeaseIPNext(int splitCount, String nextPageId) {
		
		if (restClient == null)
			return null;
		
		if (nextPageId == null || nextPageId.isEmpty())
			return null;
		
		//System.out.println(nextPageId);
		
		JsonArray resultArray = new JsonArray(); 
		
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
			
			// JsonObject Parser
			JsonObject jObj = JsonUtils.parseJsonObject(value);
			
			if (jObj == null)
				return null;

			// next_page_id
			nextPageId = JsonUtils.getValueToString(jObj, "next_page_id", "");
			
			// result
			JsonArray jIPAddr = (JsonArray)jObj.get("result");
			
			resultArray.addAll(jIPAddr);
			
			return new NextPageData(resultArray, nextPageId);
			
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion

	//region [WAPI] Get Fixed IP
	public JsonArray getFixedIPList() {

		if (restClient == null)
			return null;
		
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
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [WAPI] Get Device Status
	public JsonArray getNodeInfo(String hostName) {
		
		if (restClient == null)
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/member");
			sb.append("?_return_type=json");
			sb.append("&_return_fields=node_info");
			sb.append("&host_name=").append(hostName);
			
			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		return null;
	}
	//endregion
	
	//region [WAPI] Get License info
	public JsonArray getLicenseInfo(String hwid) {
		
		if (restClient == null)
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/member:license");
			sb.append("?_return_type=json");
			sb.append("&_return_fields=expiry_date,hwid,kind,type");
			sb.append("&hwid=").append(hwid);
			
			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		return null;
	}
	//endregion

	//region [WAPI] Get Service enable
	public JsonArray getServiceEnableInfo(String hostname) {
		
		if (restClient == null)
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/member:dhcpproperties");
			sb.append("?_return_type=json");
			sb.append("&_return_fields=enable_ddns,enable_dhcp,enable_fingerprint,enable_dhcpv6_service,enable_dhcp_on_lan2,enable_dhcp_on_ipv6_lan2");
			sb.append("&host_name=").append(hostname);
			
			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		return null;
	}
	//endregion
	
	//region [WAPI] Get HA Info
	public JsonArray getHAInfo(String hostname) {
		
		if (restClient == null)
			return null;
		
		try
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("/wapi/v2.3/member");
			sb.append("?_return_type=json");
			sb.append("&_return_fields=lan2_port_setting,enable_ha,vip_setting,master_candidate,upgrade_group,");
			sb.append("&host_name=").append(hostname);
			
			String value = restClient.Get(sb.toString());
			
			if (value == null)
				return null;
			
			// Change unescape-unicode
			value = StringUtils.unescapeUnicodeString(value);
			
			// JsonArray Parser
			return JsonUtils.parseJsonArray(value);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		return null;
	}
	//endregion

}
