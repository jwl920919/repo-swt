package com.shinwootns.ipm.insight.service.snmp;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;

import com.google.gson.JsonObject;
import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;
import com.shinwootns.data.entity.DeviceSnmp;
import com.shinwootns.data.key.SnmpOIDs;

public class SnmpHandler {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private String _host;
	private String _snmpCommunity;
	
	public SnmpHandler(String host, String snmpCommunity) {
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
	public JsonObject collectInterface() {
		JsonObject result = new JsonObject(); 
		
		try {
			SnmpUtil snmpUtil = new SnmpUtil(this._host, this._snmpCommunity);

			LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(1, SnmpOIDs.OW_system, 1000, 3);
			
			if (listResult != null) {
				
				for(SnmpResult snmpResult : listResult) {
					
					OID oid = snmpResult.getOid();
					int index = oid.get(oid.size()-2);
					
					if ( index == 1 )
						result.addProperty("sysDescr", snmpResult.getValueString());
					else if ( index == 2 )
						result.addProperty("sysObjectID", snmpResult.getValueString());
					else if ( index == 3 )
						result.addProperty("sysUpTime", snmpResult.getValueNumber().longValue());
					else if ( index == 4 )
						result.addProperty("sysContact", snmpResult.getValueString());
					else if ( index == 5 )
						result.addProperty("sysName", snmpResult.getValueString());
					else if ( index == 6 )
						result.addProperty("sysLocation", snmpResult.getValueString());
					else if ( index == 7 )
						result.addProperty("sysServices", snmpResult.getValueNumber().intValue());
					else
						break;
				}
			}
			
		}
		catch(Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}
		
		return result;
	}
	//endregion
}
