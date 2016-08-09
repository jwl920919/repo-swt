package com.shinwootns.data.status;

public class GuestIpStatus {

	public IpStatus GUEST_IP = new IpStatus();
	
	public class IpStatus {
		public Integer used = 0;
		public Integer totoal = 0;
		public String unit = "count";
	}
}
