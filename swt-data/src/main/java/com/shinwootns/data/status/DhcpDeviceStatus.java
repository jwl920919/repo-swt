package com.shinwootns.data.status;

import java.util.LinkedList;

public class DhcpDeviceStatus {
	
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
	public String power2_status;
	public String ntp_status;
	public String os;
	public LinkedList<Fan> fans = new LinkedList<Fan>();
	public LinkedList<License> licenses = new LinkedList<License>();
	public ServiceStatus service_status = new ServiceStatus();
	
	public Long collect_time;
	
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
		public Boolean enable_fingerprint;
		public Boolean enable_dhcpv6_service;
		public Boolean enable_dhcp_on_lan2;
		public Boolean enable_dhcp_on_ipv6_lan2;
	}
	
	public void addLicense(String type, Long expiry_date) {
		License license = new License();
		license.type = type;
		license.expiry_date = expiry_date;
		
		licenses.add(license);
	}
	
	public void addFan(int index, String status, int rpm) {
		Fan fan = new Fan();
		fan.index = index;
		fan.status = status;
		fan.rpm = rpm;
		
		fans.add(fan);
	}
}
