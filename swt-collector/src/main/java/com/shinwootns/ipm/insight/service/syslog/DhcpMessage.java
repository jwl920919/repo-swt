package com.shinwootns.ipm.insight.service.syslog;

public class DhcpMessage {
	
	private String dhcp_type;
	private String ip_type;		// IPV4, IPV6
	private String ip;			// IP Address (IPv4 or IPv6)
	private String mac;
	private String duid;
	private Boolean renew;
	
	@Override
	public String toString() {
		return (new StringBuilder())
		.append("[").append(dhcp_type).append("]")
		.append(" ip_type=").append(ip_type)
		.append(", ip=").append(ip)
		.append(", mac=").append(mac)
		.append(", renew=").append(renew)

		.toString();
	}
	
	public String getDhcpType() {
		return dhcp_type;
	}
	public void setDhcpType(String dhcp_type) {
		this.dhcp_type = dhcp_type;
	}
	public String getIpType() {
		return ip_type;
	}
	public void setIpType(String ip_type) {
		this.ip_type = ip_type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getDuid() {
		return duid;
	}
	public void setDuid(String duid) {
		this.duid = duid;
	}
	public Boolean getRenew() {
		return renew;
	}
	public void setRenew(Boolean renew) {
		this.renew = renew;
	}
}
