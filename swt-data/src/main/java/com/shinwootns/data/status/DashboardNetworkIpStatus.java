package com.shinwootns.data.status;

import java.util.LinkedList;

public class DashboardNetworkIpStatus {

	public LinkedList<NetworkIpStatus> LEASEIPAVAILABLE = new LinkedList<NetworkIpStatus>();  
	
	public void addIPStatus(String segment, Double value) {
		NetworkIpStatus ipStatus = new NetworkIpStatus();
		ipStatus.segment = segment;
		ipStatus.value = value;
		
		LEASEIPAVAILABLE.add(ipStatus);
	}
	
	public class NetworkIpStatus {
		public String segment;
		public Double value;
	}
}
