package com.shinwootns.ipm.insight.service.snmp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;

import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;
import com.shinwootns.common.utils.RegexUtils;
import com.shinwootns.common.utils.ip.IPAddr;
import com.shinwootns.common.utils.ip.IPNetwork;
import com.shinwootns.data.entity.InterfaceCam;
import com.shinwootns.data.entity.InterfaceInfo;
import com.shinwootns.data.entity.InterfaceIp;
import com.shinwootns.data.key.SnmpOIDs;

public class SnmpHandler {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private int _device_id;
	private String _host;
	private String _snmpCommunity;
	
	private String _sysOid = null;
	
	public SnmpHandler(int device_id, String host, String snmpCommunity) {
		this._device_id = device_id;
		this._host = host;
		this._snmpCommunity = snmpCommunity;
	}
	
	//region [static] Check snmp
	public static boolean checkSnmp(String host, String snmpCommunity) {

		try {
			//SNMP Handler
			SnmpUtil snmpUtil = new SnmpUtil(host, snmpCommunity);
			
			SnmpResult result = snmpUtil.snmpGet(1, SnmpOIDs.OG_sysObjectID, 1000, 3);
	
			if (result != null) {
				return true;
			}
		}
		catch(Exception ex) {
		}
		
		return false;
	}
	//endregion
	
	//region [public] Check snmp
	public boolean checkSnmp() {

		try {
			//SNMP Handler
			SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);
			
			SnmpResult result = snmpUtil.snmpGet(1, SnmpOIDs.OG_sysObjectID, 1000, 3);
	
			if (result != null) {
				
				this._sysOid = result.getValueString();
				
				return true;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return false;
	}
	//endregion

	//region [public] Collect System
	public SystemEntry collectSystem() {
		
		try {
			SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);

			LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_system, 1000, 3);
			
			if (listResult != null) {
				
				SystemEntry system = new SystemEntry(); 
				
				for(SnmpResult snmpResult : listResult) {
					
					OID oid = snmpResult.getOid();
					int index = oid.get(oid.size()-2);
					
					if ( index == 1 ) {
						system.setSysDescr(snmpResult.getValueString());
					}
					else if ( index == 2 ) {
						system.setSysObjectID(snmpResult.getValueString());
					}
					else if ( index == 3 ) {
						system.setSysUpTimeSec(snmpResult.getValueNumber().longValue()/100);	// 1/100 -> 1 Sec
					}
					else if ( index == 4 ) {
						system.setSysContact(snmpResult.getValueString());
					}
					else if ( index == 5 ) {
						system.setSysName(snmpResult.getValueString());
					}
					else if ( index == 6 ) {
						system.setSysLocation(snmpResult.getValueString());
					}
					else if ( index == 7 ) {
						system.setSysServices(snmpResult.getValueNumber().intValue());
					}
					else
						break;
				}
				
				// snmpEngineTime (1 sec)
				SnmpResult snmpResult = snmpUtil.snmpGet(1, SnmpOIDs.OG_snmpEngineTime, 1000, 3);
				if (snmpResult != null) {
					system.setSysUpTimeSec( snmpResult.getValueNumber().longValue() );
				}

				return system;
			}
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	//endregion
	
	//region [public] Collect Interface
	public HashMap<Integer, InterfaceInfo> collectInterface() {
	
		HashMap<Integer, InterfaceInfo> mapInf = new HashMap<Integer, InterfaceInfo>();  
		
		try {
			
			SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);
			
			String sOID = "";
			
			// 1. ifTable
			for (int i = 2; i <= 10; i++) {
				
                if (i == 3 || i == 4 || i == 9) continue;		//[Skip] 3:ifType, 4:ifMtu, 9:ifLastChange

                sOID = String.format(".1.3.6.1.2.1.2.2.1.%d", i);
                
                LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, sOID, 1000, 3);
				
				if (listResult == null)
					continue;
				
				for(SnmpResult snmpResult : listResult) {
					
					OID oid = snmpResult.getOid();
					int index = oid.get(oid.size()-1);
					
					// [2] ifDescr
					if ( i == 2 ) {
						if ( mapInf.containsKey(index) == false )
						{
							InterfaceInfo inf = new InterfaceInfo();
							inf.setDeviceId(this._device_id);
							inf.setIfNumber(index);
							inf.setIfDesc(snmpResult.getValueString("UTF-8"));
							
							mapInf.put(index, inf);
						}
					}
					// [5] ifSpeed
					else if ( i == 5 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
						{
							if (snmpResult.getValueNumber() == 4294967295L)
								inf.setIfSpeed(10000);
							else {
								inf.setIfSpeed( (int)(snmpResult.getValueNumber() / 1000000.0));
							}
						}
					}
					// [6] ifPhysAddress
					else if ( i == 6 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setIfMacaddr( snmpResult.getValueHexString(':', 6).toUpperCase() );
					}
					// [7] ifAdminStatus
					else if ( i == 7 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setAdminStauts( snmpResult.getValueNumber().intValue() );
					}
					// [8] ifOperStatus
					else if ( i == 8 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setOperStauts( snmpResult.getValueNumber().intValue() );
					}
					// [10] ifInOctets
					else if ( i == 10 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setLastOctet( new BigDecimal(snmpResult.getValueNumber()) );
					}
				}
            }
			
			
			// 2. ifXEntry
			for (int i = 1; i <= 3; i++)
            {
				LinkedList<SnmpResult> listResult;
                if (i == 1)
                	listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_ifName, 1000, 3);
                else if (i == 2)
                	listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_ifHighSpeed, 1000, 3);
                else if (i == 3)	
                	listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_ifAlias, 1000, 3);
                else
                	continue;
				
				if (listResult == null)
					continue;
				
				for(SnmpResult snmpResult : listResult) {
					
					OID oid = snmpResult.getOid();
					int index = oid.get(oid.size()-1);
					
					// ifName
					if ( i == 1 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setIfName(snmpResult.getValueString("UTF-8"));
					}
					// ifHighSpeed
					else if ( i == 2 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setIfSpeed(snmpResult.getValueNumber().intValue());
					}
					// ifAlias
					else if ( i == 3 ) {
						InterfaceInfo inf = mapInf.get(index);
						if ( inf != null )
							inf.setIfAlias(snmpResult.getValueString("UTF-8"));
					}
				}
            }
			return mapInf;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			_logger.error(ex.getMessage(), ex);
		}
		
		mapInf.clear();
		return null;
	}
	//endregion

	//region [public] Collect Interface IP
	public List<InterfaceIp> collectInterfaceIP() {
		
		HashMap<String, InterfaceIp> mapIfIp = new HashMap<String, InterfaceIp>();
		
		try {
			
			SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);
			
			String sOID;
			for (int i = 1; i <= 2; i++)
            {
				LinkedList<SnmpResult> listResult;
			
				if (i == 1)
					listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_ipAdEntIfIndex, 1000, 3);
				else if (i == 2)
					listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_ipAdEntNetMask, 1000, 3);
				else
					continue;
				
				if (listResult == null)
					continue;
				
				for(SnmpResult snmpResult : listResult) {
					
					OID oid = snmpResult.getOid();
					String ifIpAddr = String.format("%d.%d.%d.%d"
							, oid.get(oid.size()-4) , oid.get(oid.size()-3)
							, oid.get(oid.size()-2), oid.get(oid.size()-1));
					
					if (i == 1) {
					
						// ipAdEntIfIndex
						InterfaceIp ifip = new InterfaceIp();
						ifip.setDeviceId(this._device_id);
						ifip.setIfNumber( snmpResult.getValueNumber().intValue() );
						ifip.setIfIpaddr( ifIpAddr );
						
						mapIfIp.put(ifip.getIfIpaddr(), ifip);
					}
					else if (i == 2) {

						// ipAdEntNetMask
						InterfaceIp ifip = mapIfIp.get(ifIpAddr);
						if (ifip != null) {
							ifip.setNetworkMask(snmpResult.getValueString());
							
							IPNetwork network = new IPNetwork(ifip.getIfIpaddr(), ifip.getNetworkMask()); 
							ifip.setNetworkIp( network.getNetworkIP() );
						}
					}
					
				}
				listResult.clear();
            }
			
			return new ArrayList<InterfaceIp> (mapIfIp.values());
		}
		catch(Exception ex) {
			ex.printStackTrace();
			_logger.error(ex.getMessage(), ex);
		}
		
		mapIfIp.clear();
		return null;
	}
	//endregion

	//region [public] Collect CAM
	public HashMap<String, InterfaceCam> collectCam() {
		
		HashMap<String, InterfaceCam> mapCam = new HashMap<String, InterfaceCam>();
		
		try {
			
			CamEntryData camEntry = new CamEntryData(this._device_id);
			
			// isCisco()
			if (isCisco()) {
				
				// Collect Vlan
				Set<Integer> setVlan = collectVlan();
				 
				for(Integer vlan : setVlan) {
					
					if (vlan == null)
						continue;
				
					// Community Indexing
					StringBuilder dotCommunity = new StringBuilder();
					dotCommunity.append(this._snmpCommunity).append("@").append(vlan);
					
					// dod1d SnmpUtil
					SnmpUtil snmpDot1dUtil = new SnmpUtil(this._host, dotCommunity.toString());
					
					collectCAMInfo_Dot1dIfIndex(snmpDot1dUtil, camEntry);
					collectCAMInfo_MacDot1d(snmpDot1dUtil, camEntry);
					CollectCAMInfo_StpRootPort(snmpDot1dUtil, camEntry);
					CollectCAMInfo_StpPortState(snmpDot1dUtil, camEntry);
				}
			}
			else {
				
				SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);
				
				collectCAMInfo_Dot1dIfIndex(snmpUtil, camEntry);
				collectCAMInfo_MacDot1d(snmpUtil, camEntry);
				CollectCAMInfo_StpRootPort(snmpUtil, camEntry);
				CollectCAMInfo_StpPortState(snmpUtil, camEntry); 
			}
			
			// Make CAM table
			return camEntry.makeCamTable();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			_logger.error(ex.getMessage(), ex);
		}
		
		mapCam.clear();
		return null;
	}
	//endregion
	
	//region isCisco
	public Boolean isCisco() {
		if (this._sysOid == null)
			checkSnmp();
		
		if ( this._sysOid == null )
			return null;
		
		if (this._sysOid.indexOf("1.3.6.1.4.1.9") == 0)
			return true;

		return false;
	}
	//endregion
	
	//region Collect Vlan Info
	private Set<Integer> collectVlan() {
		
		Set<Integer> setVlan = new HashSet<Integer>();

		SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);
		
		// vtpVlanIfIndex (.1.3.6.1.4.1.9.9.46.1.3.1.1.18)
        // .1.3.6.1.4.1.9.9.46.1.3.1.1.18.[?].[vlanNum] = INTEGER: [IfIndex]
		LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_vtpVlanIfIndex, 1000, 3);
		if (listResult != null) {
			
			for(SnmpResult snmpResult : listResult) {
				OID oid = snmpResult.getOid();
				
				int vlan = oid.get(oid.size()-1);
				
				if (setVlan.contains(vlan) == false)
					setVlan.add(vlan);
			}
			listResult.clear();
		}
		
		// ifDescr
		if (setVlan.size() == 0) {
			
			listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_ifDescr, 1000, 3);
			
			for(SnmpResult snmpResult : listResult) {
		
				// unrouted VLAN 117
                // VLAN 00005 (OFF14_2F_SW1)
                // VLAN 1004
                // Vlan145
                // VLAN-1002
				String ifName = snmpResult.getValueString();
				
				Pattern pattern = Pattern.compile("^(unrouted )?(VLAN|Vlan|Vl)[-| ]?([0-9]+)");
				
				Matcher matcher = pattern.matcher(ifName);
				if (matcher.find() && matcher.groupCount() > 0 ) {
					
					String vlanString =  matcher.group(3);
					
					int vlan = Integer.parseInt(vlanString);
					
					if (setVlan.contains(vlan) == false)
						setVlan.add(vlan);					
				}
			}
		}
		
		return setVlan;
	}
	//endregion
	
	//region Collect CAM - Dot1dIfIndex
	private boolean collectCAMInfo_Dot1dIfIndex(SnmpUtil snmpUtil, CamEntryData camEntry) {
		
		LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_dot1dBasePortIfIndex, 1000, 3);
		
		if (listResult == null)
			return false;
		
		for(SnmpResult snmpResult : listResult) {

			// dod1dBasePortIfIndex (1.3.6.1.2.1.17.1.4.1.2)
            // 1.3.6.1.2.1.17.1.4.1.2.[dod1d] = [IfIndex]
			
			OID oid = snmpResult.getOid();
			int dod1d = oid.get(oid.size()-1);
			int ifNum = snmpResult.getValueNumber().intValue();
			
			if ( camEntry.mapDot1dIfIndex.containsKey(dod1d) == false) {
				camEntry.mapDot1dIfIndex.put(dod1d, ifNum);
			}
		}
		
		listResult.clear();
		
		return true;
	}
	//endregion
	
	//region Collect CAM - MacDot1d
	private boolean collectCAMInfo_MacDot1d(SnmpUtil snmpUtil, CamEntryData camEntry) {
		
		LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_dot1dTpFdbPort, 1000, 3);
		
		if (listResult == null)
			return false;
		
		for(SnmpResult snmpResult : listResult) {
			
			// dot1dTpFdbPort (1.3.6.1.2.1.17.4.3.1.2)
            // 1.3.6.1.2.1.17.4.3.1.2.[MacAddr] = [dot1d]
			// 1.3.6.1.2.1.17.4.3.1.2.[0.1.89.128.36.142] : [1]
			
			OID oid = snmpResult.getOid();
			int dot1d = snmpResult.getValueNumber().intValue();
					
			String macAddr = String.format( "%02X:%02X:%02X:%02X:%02X:%02X"
					, oid.get(oid.size() - 6)
					, oid.get(oid.size() - 5)
					, oid.get(oid.size() - 4)
					, oid.get(oid.size() - 3)
					, oid.get(oid.size() - 2)
					, oid.get(oid.size() - 1)
			);
			
			if (camEntry.mapMacDot1d.containsKey(macAddr) == false) {
				camEntry.mapMacDot1d.put(macAddr, dot1d);
			}
		}
		
		listResult.clear();
		
		return true;
	}
	//endregion
	
	//region Collect CAM - StpRootPort
	private boolean CollectCAMInfo_StpRootPort(SnmpUtil snmpUtil, CamEntryData camEntry) {
		
		LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_dot1dStpRootPort, 1000, 3);

		if (listResult == null)
			return false;
		
		for(SnmpResult snmpResult : listResult) {
			
			// dot1dStpRootPort (1.3.6.1.2.1.17.2.7)
            // 1.3.6.1.2.1.17.2.7.0 = [dot1d]
			
			camEntry.setRootDot1d.add(snmpResult.getValueNumber().intValue());
		}
		
		listResult.clear();
		
		return true;
	}
	
	//region Collect CAM - StpPortState
	private boolean CollectCAMInfo_StpPortState(SnmpUtil snmpUtil, CamEntryData camEntry) {
		
		LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_dot1dStpPortState, 1000, 3);

		if (listResult == null)
			return false;
		
		for(SnmpResult snmpResult : listResult) {
			
			// dot1dStpPortState (1.3.6.1.2.1.17.2.15.1.3)
            // disabled(1), blocking(2), listening(3), learning(4), forwarding(5), broken(6)
			OID oid = snmpResult.getOid();
			int dot1d = oid.get(oid.size()-1);
			int state = snmpResult.getValueNumber().intValue();
			
			if (state == 1)
				camEntry.setDot1dPortState.put(dot1d, "disabled");
			else if (state == 2)
				camEntry.setDot1dPortState.put(dot1d, "blocking");
			else if (state == 3)
				camEntry.setDot1dPortState.put(dot1d, "listening");
			else if (state == 4)
				camEntry.setDot1dPortState.put(dot1d, "learning");
			else if (state == 5)
				camEntry.setDot1dPortState.put(dot1d, "forwarding");
			else if (state == 6)
				camEntry.setDot1dPortState.put(dot1d, "broken");
			else
				camEntry.setDot1dPortState.put(dot1d, "");
		}
		
		listResult.clear();
		
		return true;
	}
	//endregion

}
