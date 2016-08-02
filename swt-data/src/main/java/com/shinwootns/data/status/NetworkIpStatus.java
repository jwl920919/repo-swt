package com.shinwootns.data.status;

import java.util.LinkedList;

public class NetworkIpStatus {

	public LinkedList<IpStatus> LEASEIPAVAILABLE = new LinkedList<IpStatus>();  
	
	public void addIPStatus(String segment, Double value) {
		
		IpStatus ipStatus = new IpStatus();
		ipStatus.segment = segment;
		ipStatus.value = value;
		
		LEASEIPAVAILABLE.add(ipStatus);
	}
	
	public class IpStatus {
		public String segment;
		public Double value;
	}
}
