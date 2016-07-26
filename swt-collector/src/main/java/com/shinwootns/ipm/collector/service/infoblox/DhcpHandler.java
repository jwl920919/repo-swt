package com.shinwootns.ipm.collector.service.infoblox;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shinwootns.common.infoblox.InfobloxWAPIHandler;
import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.ipm.data.status.DhcpStatus;

public class DhcpHandler {
	
	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private String host = "";
	private String wapiUser = "";
	private String wapiPasswd = "";
	private String snmpCommunity = "";
	
	private SnmpUtil snmpUtil;
	private InfobloxWAPIHandler wapiHandler; 
	
	public DhcpHandler(String host, String wapiId, String wapiPwd, String snmpCommunity) {
		
		this.host = host;
		this.wapiUser = wapiId;
		this.wapiPasswd = wapiPwd;
		this.snmpCommunity = snmpCommunity;
		
		wapiHandler = new InfobloxWAPIHandler(this.host, this.wapiUser, this.wapiPasswd);
		snmpUtil = new SnmpUtil(this.host, this.snmpCommunity);
	}
	
	//region Connect WAPI & SNMP
	public boolean Connect() {
		
		try
		{
			// Connect WAPI
			if (wapiHandler.Connect()) {
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
			_logger.fatal(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion
	
	//region [SNMP] Check snmp
	public boolean checkSnmp() {
		
		SnmpResult sysOid = this.snmpUtil.snmpGet(1, ".1.3.6.1.2.1.1.2.0", 1000, 3);

		if (sysOid != null) {
			return true;
		}
		
		return false;
	}
	//endregion
	
	//region [SNMP] Get sysName
	public String getSysName() {
		
		SnmpResult sysName = this.snmpUtil.snmpGet(1, ".1.3.6.1.2.1.1.5.0", 1000, 3);

		if (sysName != null)
			return sysName.getValueString();
		
		return null;
	}
	//endregion
	
	//region [SNMP] Get SysUptime
	public Long getSysUptime() {

		SnmpResult sysUptime = this.snmpUtil.snmpGet(2, ".1.3.6.1.2.1.1.3.0", 1000, 3);
		
		if (sysUptime != null)
			return (long)(sysUptime.getValueNumber()/1000);
		
		return null;
	}
	//endregion
	
	//region Get Device Status
	public DhcpStatus getHWStatus() {
		
		DhcpStatus dhcpStatus = new DhcpStatus();
		dhcpStatus.host = this.host;
		dhcpStatus.host_name = getSysName();
		dhcpStatus.sys_uptime = getSysUptime();
		
		// Get Node Info
		JsonArray jArray = wapiHandler.getNodeInfo(dhcpStatus.host_name);
			
		if (jArray == null || jArray.size() == 0)
			return null;
		
		JsonElement rootEle = jArray.get(0);

		try {
			
			// get node info
			JsonArray jNodeArray = rootEle.getAsJsonObject().get("node_info").getAsJsonArray();
			
			if (jNodeArray != null)
				getHWNodeInfo(dhcpStatus, jNodeArray);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return dhcpStatus;
	}
	
	private void getHWNodeInfo(DhcpStatus dhcpStatus, JsonArray jNodeArray) {
		
		if (dhcpStatus == null || jNodeArray == null)
			return;
		
		for(JsonElement nodeEle : jNodeArray) {
			
			JsonObject nodeObj = nodeEle.getAsJsonObject();
			
			// hwid
			dhcpStatus.hwid = nodeObj.get("hwid").getAsString();
			
			// Service Status
			JsonArray jServiceArray = nodeObj.get("service_status").getAsJsonArray();
			for(JsonElement serviceEle : jServiceArray) {
				
				String service = JsonUtils.getValueToString(serviceEle, "service", "");
				String desc = JsonUtils.getValueToString(serviceEle, "description", "");
				String status = JsonUtils.getValueToString(serviceEle, "status", "");
				
				// CPU Usage
				if (service.toUpperCase().equals("CPU_USAGE") && status.toUpperCase().equals("WORKING")) {

					// CPU Usage: 5%
					int index1 = desc.indexOf(":");
					int index2 = desc.indexOf("%");
					
					if (index1 > 0 && index2 > 0)
						dhcpStatus.cpu_usage = Integer.parseInt(desc.substring(index1+1, index2).trim());
				}
				// Memory Usage
				else if (service.toUpperCase().equals("MEMORY") && status.toUpperCase().equals("WORKING")) {
					
					// 28% - System memory usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.memory_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// Swap Usage
				else if (service.toUpperCase().equals("SWAP_USAGE") && status.toUpperCase().equals("WORKING")) {
					
					// 0% - System swap space usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.swap_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// DB capacity used
				else if (service.toUpperCase().equals("DB_OBJECT") && status.toUpperCase().equals("WORKING")) {
					
					// 0% - Database capacity usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.db_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// Disk usage
				else if (service.toUpperCase().equals("DISK_USAGE") && status.toUpperCase().equals("WORKING")) {
					
					// 9% - Primary drive usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.disk_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// Capacity usage
				else if (service.toUpperCase().equals("DISCOVERY_CAPACITY") && status.toUpperCase().equals("WORKING")) {
					
					// 0% - Discovery capacity usage is OK.
					int index = desc.indexOf("%");
					if (index > 0) {
						dhcpStatus.capacity_usage = Integer.parseInt( desc.substring(0, index).trim() );
					}
				}
				// CPU Temp.
				else if (service.toUpperCase().equals("CPU1_TEMP") && status.toUpperCase().equals("WORKING")) {
					
					// CPU_TEMP: +34.00 C
					int index1 = desc.indexOf("+");
					int index2 = desc.indexOf(".");
					
					if (index1 > 0 && index2 > 0)
						dhcpStatus.cpu_temp = Integer.parseInt(desc.substring(index1+1, index2).trim());
				}
				// Sys Temp.
				else if (service.toUpperCase().equals("SYS_TEMP") && status.toUpperCase().equals("WORKING")) {
					
					// SYS_TEMP: +37.00 C
					int index1 = desc.indexOf("+");
					int index2 = desc.indexOf(".");
					
					if (index1 > 0 && index2 > 0)
						dhcpStatus.sys_temp = Integer.parseInt(desc.substring(index1+1, index2).trim());
				}
				// Power1
				else if (service.toUpperCase().equals("POWER1")) {
					
					// Power1 (AC) OK
					if (status.toUpperCase().equals("INACTIVE"))
						dhcpStatus.power1_status = "empty";
					else if (desc.indexOf("OK") > 0)
						dhcpStatus.power1_status = "OK";
				}
				// Power2
				else if (service.toUpperCase().equals("POWER2")) {
					
					// Power2 [empty]
					if (status.toUpperCase().equals("INACTIVE") || desc.indexOf("empty") > 0 )
						dhcpStatus.power2_status = "empty";
					else if (desc.indexOf("OK") > 0)
						dhcpStatus.power2_status = "OK";
				}
				// NTP
				else if (service.toUpperCase().equals("NTP_SYNC")) {
					
					// Power2 [empty]
					if (status.toUpperCase().equals("INACTIVE") )
						dhcpStatus.ntp_status = "Inactive";
					else if (desc.indexOf("OK") > 0)
						dhcpStatus.ntp_status = "OK";
				}
			}
		}
	}
	
	//endregion
}
