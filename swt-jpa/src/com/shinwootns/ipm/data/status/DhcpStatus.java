package com.shinwootns.ipm.data.status;

public class DhcpStatus {
	public String host = "";
	public String host_name;
	public Long sys_uptime;	//seconds
	
	public String hwid;
	public Integer cpu_usage;
	public Integer memory_usage;
	public Integer swap_usage;
	public Integer db_usage;
	public Integer disk_usage;
	public Integer capacity_usage;
	public Integer cpu_temp;
	public Integer sys_temp;
	public String power1_status;
	public String ps2Status;
	public String power2_status;
	public String ntp_status;
	public String os;
	
	public Fan[] fans = {};
	public License[] licenses = {};
	public ServiceStatus service_status = new ServiceStatus();
	
	// Classes
	public class Fan {
		public int index;
		public String status;
		public int rpm;
	}
	
	public class License {
		public String type;
		public Long expiry_date;
	}
	
	public class ServiceStatus { 
		public Boolean enable_dhcp;
		public Boolean enable_dns;
	}
}
