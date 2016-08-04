package com.shinwootns.ipm.collector.service.infoblox;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shinwootns.common.infoblox.InfobloxWAPIHandler;
import com.shinwootns.common.infoblox.NextPageData;
import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.common.utils.ip.IPAddr;
import com.shinwootns.common.utils.ip.IPNetwork;
import com.shinwootns.data.entity.DhcpFixedIp;
import com.shinwootns.data.entity.DhcpIpStatus;
import com.shinwootns.data.entity.DhcpMacFilter;
import com.shinwootns.data.entity.DhcpNetwork;
import com.shinwootns.data.entity.DhcpRange;
import com.shinwootns.data.status.DhcpCounter;
import com.shinwootns.data.status.DhcpDeviceStatus;
import com.shinwootns.data.status.DhcpVrrpStatus;
import com.shinwootns.data.status.DnsCounter;

public class DhcpHandler {
	
	private final static String OID_sysObjectID = "1.3.6.1.2.1.1.2.0";
	private final static String OID_sysName = "1.3.6.1.2.1.1.5.0";
	
	private final static String OID_ibHardwareType = "1.3.6.1.4.1.7779.3.1.1.2.1.4.0";
	private final static String OID_ibSerialNumber = "1.3.6.1.4.1.7779.3.1.1.2.1.6.0";
	private final static String OID_ibNiosVersion = "1.3.6.1.4.1.7779.3.1.1.2.1.7.0";
	private final static String OID_ibDHCPStatistics = ".1.3.6.1.4.1.7779.3.1.1.4.1.3";				// DHCP Counter
	private final static String OID_ibZoneStatisticsEntry = ".1.3.6.1.4.1.7779.3.1.1.3.1.1.1";		// DNS Counter
	private final static String OID_ibSystemMonitorCpuUsage = "1.3.6.1.4.1.7779.3.1.1.2.1.8.1.1.0";
	private final static String OID_ibSystemMonitorMemUsage = "1.3.6.1.4.1.7779.3.1.1.2.1.8.2.1.0";
	private final static String OID_ibMemberServiceStatusEntry = "1.3.6.1.4.1.7779.3.1.1.2.1.9.1";
	//ibServiceName		1.3.6.1.4.1.7779.3.1.1.2.1.9.1.1
	//ibServiceStatus	1.3.6.1.4.1.7779.3.1.1.2.1.9.1.2
	//ibServiceDesc		s1.3.6.1.4.1.7779.3.1.1.2.1.9.1.3
	private final static String OID_ibMemberNode1ServiceStatusEntry = "1.3.6.1.4.1.7779.3.1.1.2.1.10.1";
	//ibNode1ServiceName	1.3.6.1.4.1.7779.3.1.1.2.1.10.1.1
	//ibNode1ServiceStatus	1.3.6.1.4.1.7779.3.1.1.2.1.10.1.2
	//ibNode1ServiceDesc	1.3.6.1.4.1.7779.3.1.1.2.1.10.1.3
	private final static String OID_ibMemberNode2ServiceStatusEntry = "1.3.6.1.4.1.7779.3.1.1.2.1.11.1";
	//ibNode2ServiceName	1.3.6.1.4.1.7779.3.1.1.2.1.11.1.1
	//ibNode2ServiceStatus	1.3.6.1.4.1.7779.3.1.1.2.1.11.1.2
	//ibNode2ServiceDesc	1.3.6.1.4.1.7779.3.1.1.2.1.11.1.3
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private String host = "";
	private String wapiUser = "";
	private String wapiPasswd = "";
	private String snmpCommunity = "";
	
	private InfobloxWAPIHandler wapiHandler = null;
	
	//region Connect WAPI & SNMP
	public boolean Connect(String host, String wapiId, String wapiPwd, String snmpCommunity) {
		
		this.host = host;
		this.wapiUser = wapiId;
		this.wapiPasswd = wapiPwd;
		this.snmpCommunity = snmpCommunity;
		
		try
		{
			//WAPI Handler
			if (wapiHandler == null)
				wapiHandler = new InfobloxWAPIHandler();
			
			// Connect WAPI
			if (wapiHandler.connect(this.host, this.wapiUser, this.wapiPasswd) == false) {
				_logger.error( (new StringBuilder().append(host).append(", Connect WAPI... failed")).toString() );
				return false;
			}
			_logger.info( (new StringBuilder().append(host).append(", Connect WAPI... OK")).toString() );
			
			// Check SNMP
			if ( checkSnmp() == false ) {
				_logger.error( (new StringBuilder().append(host).append(", Connect SNMP... failed")).toString() );
				return false;
			}
			_logger.info( (new StringBuilder().append(host).append(", Connect SNMP... OK")).toString() );
			
			return true;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	public void close() {
		try {
			wapiHandler.close();
		}
		catch(Exception ex) {}
		finally {
			wapiHandler = null;
		}
	}
	//endregion
	
	//region [SNMP] Check snmp
	public boolean checkSnmp() {

		try {
			//SNMP Handler
			SnmpUtil snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
			
			SnmpResult result = snmpUtil.snmpGet(1, OID_sysObjectID, 1000, 3);
	
			if (result != null) {
				return true;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region [SNMP] Get sysName
	public String getSysName() {
		
		try {
			SnmpUtil snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
			SnmpResult result = snmpUtil.snmpGet(1, OID_sysName, 1000, 3);
	
			if (result != null)
				return result.getValueString();
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [SNMP] Get SysUptime
	public Long getSysUptime() {

		try {
			SnmpUtil snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
			SnmpResult sysUptime = snmpUtil.snmpGet(2, ".1.3.6.1.2.1.1.3.0", 1000, 3);
			
			if (sysUptime != null)
				return (long)(sysUptime.getValueNumber()/100);
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [SNMP] Get NioVersion
	public String getNiosVersion() {
		
		try {
			SnmpUtil snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
			SnmpResult result = snmpUtil.snmpGet(1, OID_ibNiosVersion, 1000, 3);
	
			if (result != null)
				return result.getValueString();
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] Get Network
	public LinkedList<DhcpNetwork> getDhcpNetwork(int site_id) {
		
		if (wapiHandler == null)
			return null;

		try {
			
			LinkedList<DhcpNetwork> listNetwork = new LinkedList<DhcpNetwork>();
		
			// Collect Network
			JsonArray jArray = wapiHandler.getNetworkInfo();
			
			if (jArray != null)
			for(JsonElement obj : jArray) {
				
				String network = JsonUtils.getValueToString((JsonObject)obj, "network", "");
				
				if (network != null && network.isEmpty() == false) {
					
					DhcpNetwork dhcpNetwork = new DhcpNetwork();
					dhcpNetwork.setSiteId(site_id);
					dhcpNetwork.setNetwork(network);
					dhcpNetwork.setComment(JsonUtils.getValueToString(obj, "comment", ""));
					
					IPNetwork ipNetwork = new IPNetwork(network);
					dhcpNetwork.setStartIp(ipNetwork.getStartIP().toString());
					dhcpNetwork.setEndIp(ipNetwork.getEndIP().toString());
					// IP Num
					dhcpNetwork.setStartNum(ipNetwork.getStartIP().getNumberToBigInteger());
					dhcpNetwork.setEndNum(ipNetwork.getEndIP().getNumberToBigInteger());
					// IP Counts
					dhcpNetwork.setIpCount(ipNetwork.getIPCount());
					
					// IPv6
					if (network.indexOf(":") >= 0 ) {
						dhcpNetwork.setIpType("IPV6");
					}
					// IPv4
					else if (network.indexOf(".") >= 0 ) {
						dhcpNetwork.setIpType("IPV4");
					}
										
					listNetwork.add(dhcpNetwork);
				}
			}
			
			return listNetwork;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}

		return null;
	}
	//endregion
	
	//region [FUNC] Get Range
	public LinkedList<DhcpRange> getDhcpRange(int site_id) {
		
		if (wapiHandler == null)
			return null;

		try {
			
			LinkedList<DhcpRange> listRange = new LinkedList<DhcpRange>();
			
			// Collect Range
			JsonArray jArray = wapiHandler.getRangeInfo();
			
			if (jArray == null)
				return null;
				
			for(JsonElement obj : jArray) {
				
				String network = JsonUtils.getValueToString((JsonObject)obj, "network", "");
				
				if (network != null && network.isEmpty() == false) {
					
					DhcpRange dhcpRange = new DhcpRange();
					dhcpRange.setSiteId(site_id);
					dhcpRange.setNetwork(network);
					dhcpRange.setStartIp(JsonUtils.getValueToString((JsonObject)obj, "start_addr", ""));
					dhcpRange.setEndIp(JsonUtils.getValueToString((JsonObject)obj, "end_addr", ""));
					
					IPAddr startIPAddr = new IPAddr(dhcpRange.getStartIp());
					IPAddr endIPAddr = new IPAddr(dhcpRange.getEndIp());
					
					// IP Num
					dhcpRange.setStartNum(startIPAddr.getNumberToBigInteger());
					dhcpRange.setEndNum(endIPAddr.getNumberToBigInteger());
					// IP Count
					dhcpRange.setIpCount(endIPAddr.getNumberToBigInteger()
							.subtract(startIPAddr.getNumberToBigInteger())
							.add(new BigInteger("1")));
					
					// IPv6
					if (network.indexOf(":") >= 0 ) {
						dhcpRange.setIpType("IPV6");
					}
					// IPv4
					else if (network.indexOf(".") >= 0 ) {
						dhcpRange.setIpType("IPV4");
					}
					
					
					listRange.add(dhcpRange);
				}
			}
			return listRange;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}

		return null;
	}
	//endregion
	
	//region [FUNC] get Mac Filter
	public LinkedList<DhcpMacFilter> getDhcpMacFilter(int site_id) {
		
		if (wapiHandler == null)
			return null;

		try {
			LinkedList<DhcpMacFilter> listFilter = new LinkedList<DhcpMacFilter>();
			
			JsonArray jArray = wapiHandler.getFilterInfo();
		
			for(JsonElement jEle : jArray) {
				
				if (jEle != null && jEle.isJsonObject()) {
					
					JsonObject jObj = jEle.getAsJsonObject();
					
					DhcpMacFilter filter = new DhcpMacFilter();
					filter.setSiteId(site_id);
					filter.setFilterName(JsonUtils.getValueToString(jObj, "name", ""));
					filter.setFilterDesc(JsonUtils.getValueToString(jObj, "comment", ""));
					filter.setDisable(JsonUtils.getValueToBoolean(jObj, "disable", null));
					filter.setNeverExpires(JsonUtils.getValueToBoolean(jObj, "never_expires", null));
					filter.setDefaultExpiration_time(JsonUtils.getValueToInteger(jObj, "default_mac_address_expiration", null));
					filter.setEnforceExpiration_times(JsonUtils.getValueToBoolean(jObj, "enforce_expiration_times", null));
					
					listFilter.add(filter);
				}
			}
			
			return listFilter;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] get Fixed IP
	public LinkedList<DhcpFixedIp> getDhcpFixedIP(int site_id) {
		
		if (wapiHandler == null)
			return null;

		try {
			LinkedList<DhcpFixedIp> listFixedIp = new LinkedList<DhcpFixedIp>();
			
			// IPv4
			{
				JsonArray jArray = wapiHandler.getFixedIPList();
			
				for(JsonElement jEle : jArray) {
					
					if (jEle != null && jEle.isJsonObject()) {
						
						JsonObject jObj = jEle.getAsJsonObject();
						
						DhcpFixedIp fixedIp = new DhcpFixedIp();
						fixedIp.setSiteId(site_id);
						fixedIp.setIpType("IPV4");
						fixedIp.setNetwork(JsonUtils.getValueToString(jObj, "network", ""));
						fixedIp.setIpaddr(JsonUtils.getValueToString(jObj, "ipv4addr", ""));
						fixedIp.setIpNum(new IPAddr(fixedIp.getIpaddr()).getNumberToBigInteger());
						fixedIp.setComment(JsonUtils.getValueToString(jObj, "comment", ""));
						fixedIp.setMacaddr(JsonUtils.getValueToString(jObj, "mac", ""));
						fixedIp.setDisable(JsonUtils.getValueToBoolean(jObj, "disable", false));
						fixedIp.setMatchClient(JsonUtils.getValueToString(jObj, "match_client", ""));
											
						listFixedIp.add(fixedIp);
					}
				}
			}
			// IPv6 
			{
				JsonArray jArray = wapiHandler.getFixedIPv6List();
				
				for(JsonElement jEle : jArray) {
					
					if (jEle != null && jEle.isJsonObject()) {
						
						JsonObject jObj = jEle.getAsJsonObject();
						
						DhcpFixedIp fixedIp = new DhcpFixedIp();
						fixedIp.setSiteId(site_id);
						fixedIp.setIpType("IPV6");
						fixedIp.setNetwork(JsonUtils.getValueToString(jObj, "network", ""));
						fixedIp.setIpaddr(JsonUtils.getValueToString(jObj, "ipv6addr", ""));
						fixedIp.setIpNum(new IPAddr(fixedIp.getIpaddr()).getNumberToBigInteger());
						fixedIp.setComment(JsonUtils.getValueToString(jObj, "comment", ""));
						fixedIp.setIpv6Duid(JsonUtils.getValueToString(jObj, "duid", ""));
						fixedIp.setDisable(JsonUtils.getValueToBoolean(jObj, "disable", false));
						fixedIp.setIpv6AddressType(JsonUtils.getValueToString(jObj, "address_type", ""));
						fixedIp.setIpv6Prefix(JsonUtils.getValueToString(jObj, "ipv6prefix", ""));
						fixedIp.setIpv6PrefixBits(JsonUtils.getValueToInteger(jObj, "ipv6prefix_bits", null));
											
						listFixedIp.add(fixedIp);
					}
				}
			}
			
			
			return listFixedIp;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] Get IP Status
	public LinkedList<DhcpIpStatus> getDhcpIpStatus(int site_id, DhcpNetwork network) {

		if (wapiHandler == null)
			return null;
		
		try {
			
			LinkedList<DhcpIpStatus> listIPStatus = new LinkedList<DhcpIpStatus>();
			
			int splitCount = 100;
			int ipCount = 0;
			
			HashMap<String, DhcpIpStatus> mapIp = new HashMap<String, DhcpIpStatus>();
			
			if (network.getIpType().equals("IPV4")) {
				// Collect IPv4
				NextPageData nextData1 = wapiHandler.getIPv4AddressFirst(splitCount, network.getNetwork());
				extractIpv4Data(site_id, nextData1, mapIp);
				
				while(nextData1 != null && nextData1.IsExistNextPage()) {
					
					nextData1 = wapiHandler.getIPv4AddressNext(splitCount, nextData1.nextPageID);
					extractIpv4Data(site_id, nextData1, mapIp);
				}
			}
			else if (network.getIpType().equals("IPV6")) {
				// Collect IPv6
				NextPageData nextData2 = wapiHandler.getIPv6AddressFirst(splitCount, network.getNetwork());
				extractIpv6Data(site_id, nextData2, mapIp);
					
				while(nextData2 != null && nextData2.IsExistNextPage()) {
	
					nextData2 = wapiHandler.getIPv6AddressNext(splitCount, nextData2.nextPageID);
					extractIpv6Data(site_id, nextData2, mapIp);
			 	}
			}
			
			// Collect Lease IP
			NextPageData nextData3 = wapiHandler.getLeaseIPFirst(splitCount, network.getNetwork());
			extractLeaseIpData(nextData3, mapIp);
			
			while(nextData3 != null && nextData3.IsExistNextPage()) {
				
				nextData3 = wapiHandler.getLeaseIPNext(splitCount, nextData3.nextPageID);
				extractLeaseIpData(nextData3, mapIp);
			}
			
			listIPStatus.addAll(mapIp.values());
		
			return listIPStatus;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] Extract IPv4Data
	private void extractIpv4Data(int site_id, NextPageData nextData, HashMap<String, DhcpIpStatus> mapIp) {
		
		if (nextData == null || nextData.jArrayData == null)
			return;
		
		for(JsonElement ele : nextData.jArrayData) {
			
			JsonObject jObj = ele.getAsJsonObject();
			
			try
			{
				DhcpIpStatus ip = new DhcpIpStatus();
				ip.setSiteId(site_id);
				ip.setIpaddr(JsonUtils.getValueToString(jObj, "ip_address", ""));
				ip.setIpNum( (new IPAddr(ip.getIpaddr()).getNumberToBigInteger()) );
				ip.setIpType("IPV4");
				ip.setNetwork(JsonUtils.getValueToString(jObj, "network", ""));
				ip.setMacaddr(JsonUtils.getValueToString(jObj, "mac_address", "").toUpperCase());
				ip.setIsConflict(JsonUtils.getValueToBoolean(jObj, "is_conflict", false));
				ip.setStatus(JsonUtils.getValueToString(jObj, "status", ""));
				ip.setLeaseState(JsonUtils.getValueToString(jObj, "lease_state", ""));
				ip.setDiscoverStatus(JsonUtils.getValueToString(jObj, "discover_now_status", ""));
				ip.setFingerprint(JsonUtils.getValueToString(jObj, "fingerprint", ""));
				
				// merge value
				ip.setHostname(JsonUtils.getMergeValueToString(jObj, "names", ""));
				ip.setConflictTypes(JsonUtils.getMergeValueToString(jObj, "conflict_types", ""));
				ip.setObjTypes(JsonUtils.getMergeValueToString(jObj, "types", ""));
				ip.setUsage(JsonUtils.getMergeValueToString(jObj, "usage", ""));
				ip.setHostOs(JsonUtils.getMergeValueToString(jObj, "discovered_data.os", ""));
				
				
				Long lastDiscoverd = JsonUtils.getValueToLong(jObj, "discovered_data.last_discovered", 0L);
				if (lastDiscoverd != null && lastDiscoverd > 0)
					ip.setLastDiscovered(TimeUtils.convertLongToTimestamp(lastDiscoverd * 1000));

				// Put Data
				if (mapIp.containsKey(ip.getIpaddr()) == false)
					mapIp.put(ip.getIpaddr(), ip);
				else
					_logger.warn("Check duplicated ipv4 address :" + ip.getIpaddr());
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion

	//region [FUNC] Extract IPv6 Data
	private void extractIpv6Data(int site_id, NextPageData nextData, HashMap<String, DhcpIpStatus> mapIp) {
		
		if (nextData == null || nextData.jArrayData == null)
			return;
		
		for(JsonElement ele : nextData.jArrayData) {
			
			JsonObject jObj = ele.getAsJsonObject();
			
			try
			{
				DhcpIpStatus ip = new DhcpIpStatus();
				ip.setSiteId(site_id);
				ip.setIpaddr(JsonUtils.getValueToString(jObj, "ip_address", ""));
				ip.setIpNum( (new IPAddr(ip.getIpaddr()).getNumberToBigInteger()) );
				ip.setIpType("IPV6");
				ip.setNetwork(JsonUtils.getValueToString(jObj, "network", ""));
				ip.setDuid(JsonUtils.getValueToString(jObj, "duid", "").toUpperCase());
				
				if (ip.getDuid() != null && ip.getDuid().length() == 41)
					ip.setMacaddr( ip.getDuid().substring(24) );
				
				ip.setIsConflict(JsonUtils.getValueToBoolean(jObj, "is_conflict", false));
				ip.setStatus(JsonUtils.getValueToString(jObj, "status", ""));
				ip.setLeaseState(JsonUtils.getValueToString(jObj, "lease_state", ""));
				ip.setDiscoverStatus(JsonUtils.getValueToString(jObj, "discover_now_status", ""));
				ip.setFingerprint(JsonUtils.getValueToString(jObj, "fingerprint", ""));
				
				// merge value
				ip.setHostname(JsonUtils.getMergeValueToString(jObj, "names", ""));
				ip.setConflictTypes(JsonUtils.getMergeValueToString(jObj, "conflict_types", ""));
				ip.setObjTypes(JsonUtils.getMergeValueToString(jObj, "types", ""));
				ip.setUsage(JsonUtils.getMergeValueToString(jObj, "usage", ""));
				ip.setHostOs(JsonUtils.getMergeValueToString(jObj, "discovered_data.os", ""));
				
				
				Long lastDiscoverd = JsonUtils.getValueToLong(jObj, "discovered_data.last_discovered", 0L);
				if (lastDiscoverd != null && lastDiscoverd > 0)
					ip.setLastDiscovered(TimeUtils.convertLongToTimestamp(lastDiscoverd * 1000));

				// Put Data
				if (mapIp.containsKey(ip.getIpaddr()) == false)
					mapIp.put(ip.getIpaddr(), ip);
				else
					_logger.warn("Check duplicated ipv4 address :" + ip.getIpaddr());
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion

	//region [FUNC] Extract Lease IP
	private void extractLeaseIpData(NextPageData nextData, HashMap<String, DhcpIpStatus> mapIp) {
		
		if (nextData == null || nextData.jArrayData == null)
			return;
		
		for(Object obj : nextData.jArrayData) {
			
			try
			{
				JsonObject jObj = (JsonObject)obj; 
				
				String ipAddr = JsonUtils.getValueToString(jObj, "address", "");
				if (ipAddr == null || ipAddr.isEmpty())
					continue;
				
				if (mapIp.containsKey(ipAddr))
				{
					DhcpIpStatus ip = mapIp.get(ipAddr);
					
					ip.setIsNeverEnds(JsonUtils.getValueToBoolean(jObj, "never_ends", false));
					ip.setIsNeverStart(JsonUtils.getValueToBoolean(jObj, "never_starts", false));
					
					Long startTime = JsonUtils.getValueToLong((JsonObject)obj, "starts", null);
					if (startTime != null && startTime > 0)
						ip.setLeaseStartTime(TimeUtils.convertLongToTimestamp(startTime * 1000));
					
					Long endTime = JsonUtils.getValueToLong((JsonObject)obj, "ends", null);
					if (endTime != null && endTime > 0)
						ip.setLeaseEndTime(TimeUtils.convertLongToTimestamp(endTime * 1000));
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	//endregion
	
	//region [FUNC] Get Device Status
	public DhcpDeviceStatus getDeviceStatus() {
		
		if (wapiHandler == null)
			return null;
		
		try {
			
			DhcpDeviceStatus dhcpStatus = new DhcpDeviceStatus();
			dhcpStatus.host = this.host;
			dhcpStatus.host_name = getSysName();
			dhcpStatus.sys_uptime = getSysUptime();
			dhcpStatus.os = this.getNiosVersion();
			dhcpStatus.collect_time = TimeUtils.currentTimeMilis() / 1000;
			
			if (dhcpStatus.host_name == null || dhcpStatus.host_name.isEmpty()) {
				_logger.error("hostname is null");
				return null;
			}

			// Get Node Info
			JsonArray jArray = wapiHandler.getNodeInfo(dhcpStatus.host_name);

			if (jArray == null || jArray.size() == 0)
				return null;
			
			JsonArray jNodeArray = jArray.get(0).getAsJsonObject().get("node_info").getAsJsonArray();
			if (jNodeArray != null)
				extractNodeInfo(dhcpStatus, jNodeArray);
			
			// Get Service enable info
			if (dhcpStatus.hwid != null && dhcpStatus.hwid.isEmpty() == false) {
				JsonArray jServiceArray = wapiHandler.getServiceEnableInfo(dhcpStatus.host_name);
				
				if (jServiceArray != null)
					extractServiceEnableInfo(dhcpStatus, jServiceArray);
			}
			
			// Get License Info
			if (dhcpStatus.hwid != null && dhcpStatus.hwid.isEmpty() == false) {
				JsonArray jLicenseArray = wapiHandler.getLicenseInfo(dhcpStatus.hwid);

				if (jLicenseArray != null)
					extractLicenseInfo(dhcpStatus, jLicenseArray);
			}
			
			return dhcpStatus;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] getVRRPStatus
	public DhcpVrrpStatus getVRRPStatus() {
		
		if (wapiHandler == null)
			return null;
		
		try {
			
			DhcpVrrpStatus vrrp = new DhcpVrrpStatus();
			vrrp.collect_time = TimeUtils.currentTimeMilis() / 1000;
			
			String hostName = getSysName();

			// Get Node Info
			JsonArray jArray = wapiHandler.getHAInfo(hostName);

			if (jArray == null || jArray.size() == 0)
				return null;
			
			JsonObject jObj = jArray.get(0).getAsJsonObject();
			
			vrrp.enable_ha = JsonUtils.getValueToBoolean(jObj, "enable_ha", false);
			vrrp.master_candidate = JsonUtils.getValueToBoolean(jObj, "master_candidate", false);
			vrrp.group_position = JsonUtils.getValueToString(jObj, "upgrade_group", "");
			
			// Lan2 Port Setting
			JsonElement lan2Setting = jObj.get("lan2_port_setting");
			if (lan2Setting != null && lan2Setting instanceof JsonObject) {
				vrrp.lan2_port_setting = JsonUtils.getValueToBoolean(lan2Setting.getAsJsonObject(), "enabled", false);
				vrrp.nic_failover_enabled = JsonUtils.getValueToBoolean(lan2Setting.getAsJsonObject(), "nic_failover_enabled", false);
			}
			
			// VIP Setting
			JsonElement vipSetting = jObj.get("vip_setting");
			if (vipSetting != null && vipSetting instanceof JsonObject) {
				vrrp.vrrp_address = JsonUtils.getValueToString(vipSetting, "address", "");
				vrrp.vrrp_gateway = JsonUtils.getValueToString(vipSetting, "gateway", "");
				vrrp.vrrp_subnet = JsonUtils.getValueToString(vipSetting, "subnet_mask", "");
			}
			
			/*
			JsonElement svcComm = jArray.get(0).getAsJsonObject().get("member_service_communication");
			if (svcComm != null && svcComm instanceof JsonArray) {
				
				JsonArray array = svcComm.getAsJsonArray();
				
				//extractNodeInfo(dhcpStatus, jNodeArray);
			}*/

			return vrrp;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] getDhcpCounter
	public DhcpCounter getDhcpCounter() {
		
		try {
		
			DhcpCounter counter = new DhcpCounter();
			counter.collect_time = TimeUtils.currentTimeMilis() / 1000;
			
			SnmpUtil snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
			LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, OID_ibDHCPStatistics, 1000, 3);
			
			if (listResult != null) {
				for(SnmpResult result : listResult) {
					
					int index = result.getOid().get(13);
					
					if ( index == 1 )
						counter.discovers = (int)result.getValueNumber().doubleValue();
					else if ( index == 2 )
						counter.requests = (int)result.getValueNumber().doubleValue();
					else if ( index == 3 )
						counter.releases = (int)result.getValueNumber().doubleValue();
					else if ( index == 4 )
						counter.offers = (int)result.getValueNumber().doubleValue();
					else if ( index == 5 )
						counter.acks = (int)result.getValueNumber().doubleValue();
					else if ( index == 6 )
						counter.nacks = (int)result.getValueNumber().doubleValue();
					else if ( index == 7 )
						counter.declines = (int)result.getValueNumber().doubleValue();
					else if ( index == 8 )
						counter.informs = (int)result.getValueNumber().doubleValue();
				}
			}
			
			return counter;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] getDnsCounter
	public DnsCounter getDnsCounter() {
		
		try {
			DnsCounter counter = new DnsCounter();
			counter.collect_time = TimeUtils.currentTimeMilis() / 1000;
			
			SnmpUtil snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
			LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, OID_ibZoneStatisticsEntry, 1000, 3);
			
			if (listResult != null) {
				for(SnmpResult result : listResult) {
					
					int index = result.getOid().get( 14 ); 
					
					if (index == 2)
						counter.success = (int)result.getValueNumber().doubleValue();
					else if (index == 3)
						counter.referral = (int)result.getValueNumber().doubleValue();
					else if (index == 4)
						counter.nxrrset = (int)result.getValueNumber().doubleValue();
					else if (index == 5)
						counter.nxdomain = (int)result.getValueNumber().doubleValue();
					else if (index == 6)
						counter.recursion = (int)result.getValueNumber().doubleValue();
					else if (index == 7)
						counter.failure = (int)result.getValueNumber().doubleValue();
				}
			}
			
			return counter;
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region Extract Node Info
	private void extractNodeInfo(DhcpDeviceStatus dhcpStatus, JsonArray jNodeArray) {
		
		if (dhcpStatus == null || jNodeArray == null)
			return;
		
		for(JsonElement nodeEle : jNodeArray) {
			
			JsonObject nodeObj = nodeEle.getAsJsonObject();
			
			// hwid
			dhcpStatus.hwid = nodeObj.get("hwid").getAsString();
			
			// Service Status
			JsonArray jServiceArray = nodeObj.get("service_status").getAsJsonArray();
			for(JsonElement serviceEle : jServiceArray) {
				
				String service = JsonUtils.getValueToString(serviceEle, "service", "").toUpperCase();
				String desc = JsonUtils.getValueToString(serviceEle, "description", "").toUpperCase();
				String status = JsonUtils.getValueToString(serviceEle, "status", "").toUpperCase();
				
				// CPU Usage
				if (service.equals("CPU_USAGE") && status.equals("WORKING")) {

					// CPU Usage: 5%
					int index1 = desc.indexOf(":");
					int index2 = desc.indexOf("%");
					
					if (index1 > 0 && index2 > 0)
						dhcpStatus.cpu_usage = Integer.parseInt(desc.substring(index1+1, index2).trim());
				}
				// Memory Usage
				else if (service.equals("MEMORY") && status.equals("WORKING")) {
					
					// 28% - System memory usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.memory_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// Swap Usage
				else if (service.equals("SWAP_USAGE") && status.equals("WORKING")) {
					
					// 0% - System swap space usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.swap_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// DB capacity used
				else if (service.equals("DB_OBJECT") && status.equals("WORKING")) {
					
					// 0% - Database capacity usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.db_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// Disk usage
				else if (service.equals("DISK_USAGE") && status.equals("WORKING")) {
					
					// 9% - Primary drive usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.disk_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// Capacity usage
				else if (service.equals("DISCOVERY_CAPACITY") && status.equals("WORKING")) {
					
					// 0% - Discovery capacity usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.capacity_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// CPU Temp.
				else if (service.equals("CPU1_TEMP") && status.equals("WORKING")) {
					
					// CPU_TEMP: +34.00 C
					int index1 = desc.indexOf("+");
					int index2 = desc.indexOf(".");
					
					if (index1 > 0 && index2 > 0)
						dhcpStatus.cpu_temp = Integer.parseInt(desc.substring(index1+1, index2).trim());
				}
				// Sys Temp.
				else if (service.equals("SYS_TEMP") && status.equals("WORKING")) {
					
					// SYS_TEMP: +37.00 C
					int index1 = desc.indexOf("+");
					int index2 = desc.indexOf(".");
					
					if (index1 > 0 && index2 > 0)
						dhcpStatus.sys_temp = Integer.parseInt(desc.substring(index1+1, index2).trim());
				}
				// Power1
				else if (service.equals("POWER1")) {
					
					// Power1 (AC) OK
					if (desc.indexOf("EMPTY") > 0 )
						dhcpStatus.power1_status = "EMPTY";
					else if (status.equals("INACTIVE"))
						dhcpStatus.power1_status = "INACTIVE";
					else if (desc.indexOf("OK") > 0)
						dhcpStatus.power1_status = "OK";
					else if (desc.indexOf("FAILED") > 0)
						dhcpStatus.power1_status = "FAILED";
				}
				// Power2
				else if (service.equals("POWER2")) {
					
					// Power2 [empty]
					if (desc.indexOf("EMPTY") > 0 )
						dhcpStatus.power2_status = "EMPTY";
					else if (status.equals("INACTIVE"))
						dhcpStatus.power2_status = "INACTIVE";
					else if (desc.indexOf("OK") > 0)
						dhcpStatus.power2_status = "OK";
					else if (desc.indexOf("FAILED") > 0)
						dhcpStatus.power2_status = "FAILED";
				}
				// NTP
				else if (service.equals("NTP_SYNC")) {
					
					// Power2 [empty]
					if (status.toUpperCase().equals("INACTIVE") )
						dhcpStatus.ntp_status = "INACTIVE";
					else if (desc.indexOf("OK") > 0)
						dhcpStatus.ntp_status = "OK";
					else if (desc.indexOf("FAILED") > 0)
						dhcpStatus.ntp_status = "FAILED";
				}
				// FAN
				else if (service.indexOf("FAN") == 0 && service.length() > 3) {
					try {
						int fanIndex = Integer.parseInt(service.substring(3));
						
						int index1 = desc.indexOf(":");
						int index2 = desc.indexOf("RPM");
						
						int rpm = 0;
						if (index1 > 0 && index2 > 0) {
							String temp = desc.substring(index1+1, index2).trim();
							rpm = Integer.parseInt(temp);
						}
						
						dhcpStatus.addFan(fanIndex, status, rpm);
					}
					catch(Exception ex) {
						//ex.printStackTrace();
					}
				}
			}
		}
	}
	//endregion

	//region Extract License Info
	private void extractLicenseInfo(DhcpDeviceStatus dhcpStatus, JsonArray jLicenseArray) {
		
		if (dhcpStatus == null || jLicenseArray == null)
			return;
		
		for(JsonElement ele : jLicenseArray) {
			JsonObject jLicense = ele.getAsJsonObject();
			
			String type = JsonUtils.getValueToString(jLicense, "type", ""); 
			Long expireDate = JsonUtils.getValueToLong(jLicense, "expiry_date", null);
			
			if (type != null && type.isEmpty() == false)
				dhcpStatus.addLicense(type, expireDate);
		}
	}
	//endregion
	
	//region Extract Service Enabled
	private void extractServiceEnableInfo(DhcpDeviceStatus dhcpStatus, JsonArray jServiceArray) {
		
		if (dhcpStatus == null || jServiceArray == null)
			return;
		
		for(JsonElement ele : jServiceArray) {
			
			JsonObject jService = ele.getAsJsonObject();
			
			boolean enable_ddns = JsonUtils.getValueToBoolean(jService, "enable_ddns", false); 
			boolean enable_dhcp = JsonUtils.getValueToBoolean(jService, "enable_dhcp", false);
			boolean enable_fingerprint = JsonUtils.getValueToBoolean(jService, "enable_fingerprint", false);
			boolean enable_dhcpv6_service = JsonUtils.getValueToBoolean(jService, "enable_dhcpv6_service", false);
			boolean enable_dhcp_on_lan2 = JsonUtils.getValueToBoolean(jService, "enable_dhcp_on_lan2", false);
			boolean enable_dhcp_on_ipv6_lan2 = JsonUtils.getValueToBoolean(jService, "enable_dhcp_on_ipv6_lan2", false);
			
			dhcpStatus.service_status.enable_dns = enable_ddns;
			dhcpStatus.service_status.enable_dhcp = enable_dhcp;
			dhcpStatus.service_status.enable_fingerprint = enable_fingerprint;
			dhcpStatus.service_status.enable_dhcpv6_service = enable_dhcpv6_service;
			dhcpStatus.service_status.enable_dhcp_on_lan2 = enable_dhcp_on_lan2;
			dhcpStatus.service_status.enable_dhcp_on_ipv6_lan2 = enable_dhcp_on_ipv6_lan2;
		}
	}
	//endregion

}
