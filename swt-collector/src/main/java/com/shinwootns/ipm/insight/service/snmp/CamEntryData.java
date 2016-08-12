package com.shinwootns.ipm.insight.service.snmp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import com.shinwootns.data.entity.InterfaceCam;

public class CamEntryData {
	
	private int device_id;
	
	public HashSet<Integer> setCdpPort = new HashSet<Integer>();
	public HashMap<Integer, Integer> mapDot1dIfIndex = new HashMap<Integer, Integer>();
	public HashMap<String, Integer> mapMacDot1d = new HashMap<String, Integer>();
	public HashSet<Integer> setRootDot1d = new HashSet<Integer>();
	public HashMap<Integer, String> setDot1dPortState = new HashMap<Integer, String>();

	//region Make Cam Table
	
	public CamEntryData(int device_id) {
		this.device_id = device_id;
	}
	
	public HashMap<String, InterfaceCam> makeCamTable() {

		HashMap<String, InterfaceCam> mapCamTable = new HashMap<String, InterfaceCam>();
		
		for(Entry<String, Integer> entry : mapMacDot1d.entrySet()) {
			
			String macAddr = entry.getKey();
			String userIP = findUserIP(macAddr);
			int dod1d = entry.getValue();
			
			// Skip Root Port
			if (setRootDot1d.contains(dod1d))
				continue;
			
			// Find (dot1d -> IfNumber)
			Integer ifNum = this.mapDot1dIfIndex.get(dod1d);
			if (ifNum != null) {
				
				// Skip CDP Port
				if (setCdpPort.contains(ifNum))
					continue;
				
				StringBuilder sb = new StringBuilder();
				sb.append(ifNum).append("|").append(macAddr);
				
				if (mapCamTable.containsKey(sb.toString()) == false) {
				
					InterfaceCam cam = new InterfaceCam();
					cam.setDeviceId(this.device_id);
					cam.setIfNumber(ifNum);
					cam.setMacaddr(macAddr);
					cam.setIpaddr(userIP);
					
					mapCamTable.put(sb.toString(), cam);
				}
			}
			else {
				System.out.println( String.format("[CAM] Not Found Info - Dot1d(%d) -> IfNumber", dod1d));
			}
		}
		
		return mapCamTable;
	}
	
	/*public HashMap<Integer, HashMap<String, String>> makeCamTable() {

		HashMap<Integer, HashMap<String, String>> mapCamTable = new HashMap<Integer, HashMap<String, String>>();
		
		for(Entry<String, Integer> entry : mapMacDot1d.entrySet()) {
			
			String macAddr = entry.getKey();
			String userIP = findUserIP(macAddr);
			int dod1d = entry.getValue();
			
			// Skip Root Port
			if (setRootDot1d.contains(dod1d))
				continue;
			
			// Find (dot1d -> IfNumber)
			Integer ifNum = this.mapDot1dIfIndex.get(dod1d);
			if (ifNum != null) {
				
				// Skip CDP Port
				if (setCdpPort.contains(ifNum))
					continue;
				
				if (mapCamTable.containsKey(ifNum) == false) {
				
					HashMap<String, String> mapMacIp = new HashMap<String, String>();
					mapMacIp.put(macAddr, userIP);
					
					mapCamTable.put(ifNum, mapMacIp);
				}
				else {
					mapCamTable.get(ifNum).put(macAddr, userIP);
				}
			}
			else {
				System.out.println( String.format("[CAM] Not Found Info - Dot1d(%d) -> IfNumber", dod1d));
			}
		}
		
		return mapCamTable;
	}*/
	//endregion
	
	//region Find User IP
	private String findUserIP(String macAddr) {
		
		// ...
		
		return "";
	}
	//endregion
}
