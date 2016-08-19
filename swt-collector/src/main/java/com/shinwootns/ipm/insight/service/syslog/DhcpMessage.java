package com.shinwootns.ipm.insight.service.syslog;

public class DhcpMessage {
	
	private String dhcp_type;
	private String ip_type;		// IPV4, IPV6
	private String ip;			// IP Address (IPv4 or IPv6)
	private String mac;
	private String duid;
	private String hostname;
	private Boolean renew;
	private Integer duration;
	
	@Override
	public String toString() {
		return (new StringBuilder())
		.append("[").append(dhcp_type).append("]")
		.append(" ip_type=").append(ip_type)
		.append(", ip=").append(ip)
		.append(", mac=").append(mac)
		.append(", hostname=").append(hostname)
		.append(", duid=").append(duid)
		.append(", renew=").append(renew)
		.append(", duration=").append(duration)

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

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
