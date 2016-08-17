package com.shinwootns.data.key;

public class RedisKeys {

	// IPM Cluster
	public final static String KEY_IPM_CLUSTER_MASTER		= "cluster:ipm:master";
	public final static String KEY_IPM_CLUSTER_MEMBER		= "cluster:ipm:member";			// ~:{HostName}
	public final static String KEY_IPM_CLUSTER_JOB			= "cluster:ipm:job";			// ~:{HostName}
	
	// Insight Cluster
	public final static String KEY_INSIGHT_CLUSTER_MASTER	= "cluster:insight:master";		// ~:{SiteID}
	public final static String KEY_INSIGHT_CLUSTER_MEMBER	= "cluster:insight:member";		// ~:{SiteID}:{HostName}
	public final static String KEY_INSIGHT_CLUSTER_JOB		= "cluster:insight:job";		// ~:{SiteID}:{HostName}
	
	// Status
	public final static String KEY_STATUS_DEVICE			= "status:dhcp:device_status";	// ~:{SiteID}
	public final static String KEY_STATUS_VRRP				= "status:dhcp:vrrp_status";	// ~:{SiteID}
	public final static String KEY_STATUS_DHCP_COUNTER		= "status:dhcp:dhcp_counter";	// ~:{SiteID}
	public final static String KEY_STATUS_DNS_COUNTER		= "status:dhcp:dns_counter";	// ~:{SiteID}
	
	// Dashboard
	public final static String KEY_DASHBOARD_NETWORK_IP		= "status:dashboard:network_ip_status";	// ~:{SiteID}
	public final static String KEY_DASHBOARD_LEASE_IPV4		= "status:dashboard:lease_ipv4_status";	// ~:{SiteID}
	public final static String KEY_DASHBOARD_LEASE_IPV6		= "status:dashboard:lease_ipv6_status";	// ~:{SiteID}
	public final static String KEY_DASHBOARD_GUEST_IP		= "status:dashboard:guest_ip_status";	// ~:{SiteID}
	
	// Dhcp Counter
	public final static String KEY_COUNTER_DHCP				= "counter:dhcp_type";			// ~:{siteID}:{Sec%10}:{DHCP_TYPE}
	public final static String KEY_COUNTER_SYSLOG			= "counter:syslog";				// ~:{siteID}
	public final static String KEY_COUNTER_PROCESS			= "counter:process";			// ~:{siteID}
	
	// Data
	public final static String KEY_DATA_SYSLOG				= "data:syslog";				// ~:{SiteID}
}
