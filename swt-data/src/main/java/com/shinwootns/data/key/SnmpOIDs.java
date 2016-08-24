package com.shinwootns.data.key;

public class SnmpOIDs {
	
	//===================================================================================
	// system
	//===================================================================================
	public final static String OW_system 				= "1.3.6.1.2.1.1";
	public final static String OG_sysDescr 				= "1.3.6.1.2.1.1.1.0";
	public final static String OG_sysObjectID 			= "1.3.6.1.2.1.1.2.0";
	public final static String OG_sysUpTime 			= "1.3.6.1.2.1.1.3.0";
	public final static String OG_sysContact			= "1.3.6.1.2.1.1.4.0";
	public final static String OG_sysName 				= "1.3.6.1.2.1.1.5.0";
	public final static String OG_sysLocation			= "1.3.6.1.2.1.1.6.0";
	public final static String OG_sysServices			= "1.3.6.1.2.1.1.7.0";		//BitSet, 
																					// [0] = Layer1(Physical), [1] = Layer2(DataLink, Sub-net), [2] = Layer3(Internet, GateWay) 
																					// [3] = Layer4(EndToEnd, Hosts), [6] = Layer6(Application)
	
	public final static String OG_snmpEngineTime		= "1.3.6.1.6.3.10.2.1.3";	// snmpEngineTime => 1 sec,  sysUpTime=> 1/100 sec  

	
	//===================================================================================
	// ifEntry
	//===================================================================================
	public final static String OW_ifDescr				= "1.3.6.1.2.1.2.2.1.2";
	public final static String OW_ifType				= "1.3.6.1.2.1.2.2.1.3";
	public final static String OW_ifMtu					= "1.3.6.1.2.1.2.2.1.4";
	public final static String OW_ifSpeed				= "1.3.6.1.2.1.2.2.1.5";
	public final static String OW_ifPhysAddress			= "1.3.6.1.2.1.2.2.1.6";
	public final static String OW_ifAdminStatus			= "1.3.6.1.2.1.2.2.1.7";
	public final static String OW_ifOperStatus			= "1.3.6.1.2.1.2.2.1.8";
	
	public final static String OW_ifInOctets			= "1.3.6.1.2.1.2.2.1.10";
	public final static String OW_ifInUcastPkts			= "1.3.6.1.2.1.2.2.1.11";
	public final static String OW_ifInNUcastPkts		= "1.3.6.1.2.1.2.2.1.12";
	public final static String OW_ifInDiscards			= "1.3.6.1.2.1.2.2.1.13";
	public final static String OW_ifInErrors			= "1.3.6.1.2.1.2.2.1.14";
	public final static String OW_ifOutOctets			= "1.3.6.1.2.1.2.2.1.16";
	public final static String OW_ifOutUcastPkts		= "1.3.6.1.2.1.2.2.1.17";
	public final static String OW_ifOutNUcastPkts		= "1.3.6.1.2.1.2.2.1.18";
	public final static String OW_ifOutDiscards			= "1.3.6.1.2.1.2.2.1.19";
	public final static String OW_ifOutErrors			= "1.3.6.1.2.1.2.2.1.20";
	
	//===================================================================================
	// ifXEntry
	//===================================================================================
	public final static String OW_ifName				= "1.3.6.1.2.1.31.1.1.1.1";
	
	public final static String OW_ifInMulticastPkts		= "1.3.6.1.2.1.31.1.1.1.2";
	public final static String OW_ifInBroadcastPkts		= "1.3.6.1.2.1.31.1.1.1.3";
	public final static String OW_ifOutMulticastPkts	= "1.3.6.1.2.1.31.1.1.1.4";
	public final static String OW_ifOutBroadcastPkts	= "1.3.6.1.2.1.31.1.1.1.5";
	
	public final static String OW_ifHCInOctets			= "1.3.6.1.2.1.31.1.1.1.6";
	public final static String OW_ifHCInUcastPkts		= "1.3.6.1.2.1.31.1.1.1.7";
	public final static String OW_ifHCInMulticastPkts	= "1.3.6.1.2.1.31.1.1.1.8";
	public final static String OW_ifHCInBroadcastPkts	= "1.3.6.1.2.1.31.1.1.1.9";
	
	public final static String OW_ifHCOutOctets			= "1.3.6.1.2.1.31.1.1.1.10";
	public final static String OW_ifHCOutUcastPkts		= "1.3.6.1.2.1.31.1.1.1.11";
	public final static String OW_ifHCOutMulticastPkts	= "1.3.6.1.2.1.31.1.1.1.12";
	public final static String OW_ifHCOutBroadcastPkts	= "1.3.6.1.2.1.31.1.1.1.13";
	public final static String OW_ifHighSpeed			= "1.3.6.1.2.1.31.1.1.1.15";
	public final static String OW_ifAlias				= "1.3.6.1.2.1.31.1.1.1.18";
	
	
	//===================================================================================
	// ipAddrEntry
	//===================================================================================
	public final static String OW_ipAdEntIfIndex		= "1.3.6.1.2.1.4.20.1.2";			// 1.3.6.1.2.1.4.20.1.2.[IP] = [IfIndex]
	public final static String OW_ipAdEntNetMask		= "1.3.6.1.2.1.4.20.1.3";			// 1.3.6.1.2.1.4.20.1.2.[IP] = [NetMask]
	
	//===================================================================================
	// dot1dBridge
	//===================================================================================
	// CAM
	public final static String OW_dot1dBasePortIfIndex	= "1.3.6.1.2.1.17.1.4.1.2";			// 1.3.6.1.2.1.17.1.4.1.2.[dod1d] = [IfIndex]
	public final static String OW_dot1dTpFdbPort		= "1.3.6.1.2.1.17.4.3.1.2";			// 1.3.6.1.2.1.17.4.3.1.2.[MacAddr] = [dot1d]
	public final static String OW_dot1dStpRootPort		= "1.3.6.1.2.1.17.2.7";				// 1.3.6.1.2.1.17.2.7.0 = [dot1d]
	
	public final static String OW_dot1dStpPortState		= "1.3.6.1.2.1.17.2.15.1.3";		// ( disabled(1), blocking(2), listening(3), learning(4), forwarding(5), broken(6) )
	
	
	//===================================================================================
	// Cisco
	//===================================================================================
	// CDP
	public final static String OW_cdpCacheDeviceId		= "1.3.6.1.4.1.9.9.23.1.2.1.1.6";	// cdpCacheDeviceId.[IfIndex].[nNBRIfIndex] : [Neighbor Name] 
	public final static String OW_cdpCacheDevicePort	= "1.3.6.1.4.1.9.9.23.1.2.1.1.7";	// cdpCacheDeviceId.[IfIndex].[nNBRIfIndex] : [Neighbor IfName]
	
	public final static String OW_vtpVlanIfIndex		= "1.3.6.1.4.1.9.9.46.1.3.1.1.18";	// 1.3.6.1.4.1.9.9.46.1.3.1.1.18.[?].[vlanNum] = [?]
	
	//===================================================================================
	// Infoblox
	//===================================================================================
	public final static String OG_ibHardwareType 		= "1.3.6.1.4.1.7779.3.1.1.2.1.4.0";
	public final static String OG_ibSerialNumber 		= "1.3.6.1.4.1.7779.3.1.1.2.1.6.0";
	public final static String OG_ibNiosVersion 		= "1.3.6.1.4.1.7779.3.1.1.2.1.7.0";
	public final static String OW_ibDHCPStatistics 		= "1.3.6.1.4.1.7779.3.1.1.4.1.3";		// DHCP Counter
	public final static String OW_ibZoneStatisticsEntry = "1.3.6.1.4.1.7779.3.1.1.3.1.1.1";		// DNS Counter
	public final static String OG_ibSystemMonitorCpuUsage 	= "1.3.6.1.4.1.7779.3.1.1.2.1.8.1.1.0";
	public final static String OG_ibSystemMonitorMemUsage 	= "1.3.6.1.4.1.7779.3.1.1.2.1.8.2.1.0";
	
	public final static String OW_ibMemberServiceStatusEntry	= "1.3.6.1.4.1.7779.3.1.1.2.1.9.1";
	public final static String OW_ibServiceName			= "1.3.6.1.4.1.7779.3.1.1.2.1.9.1.1";
	public final static String OW_ibServiceStatus		= "1.3.6.1.4.1.7779.3.1.1.2.1.9.1.2";
	public final static String OW_ibServiceDesc			= "1.3.6.1.4.1.7779.3.1.1.2.1.9.1.3";
	
	public final static String OW_ibMemberNode1ServiceStatusEntry = "1.3.6.1.4.1.7779.3.1.1.2.1.10.1";
	public final static String OW_ibNode1ServiceName	= "1.3.6.1.4.1.7779.3.1.1.2.1.10.1.1";
	public final static String OW_ibNode1ServiceStatus	= "1.3.6.1.4.1.7779.3.1.1.2.1.10.1.2";
	public final static String OW_ibNode1ServiceDesc	= "1.3.6.1.4.1.7779.3.1.1.2.1.10.1.3";
	
	public final static String OW_ibMemberNode2ServiceStatusEntry = "1.3.6.1.4.1.7779.3.1.1.2.1.11.1";
	public final static String OW_ibNode2ServiceName	= "1.3.6.1.4.1.7779.3.1.1.2.1.11.1.1";
	public final static String OW_ibNode2ServiceStatus	= "1.3.6.1.4.1.7779.3.1.1.2.1.11.1.2";
	public final static String OW_ibNode2ServiceDesc	= "1.3.6.1.4.1.7779.3.1.1.2.1.11.1.3";
}
