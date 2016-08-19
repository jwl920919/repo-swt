package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;


public class DhcpLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private Integer device_id;
	private String dhcp_ip;
	private String dhcp_type;
	private Boolean is_renew;
	private Boolean is_guest_range;
	private Integer duration;
	private String client_ip_type;
	private String client_ip;
	private BigInteger client_ip_num;
	private String client_mac;
	private String client_duid;
	private String client_hostname;
	private Timestamp collect_time;

	public DhcpLog() {
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
				.append("[DhcpLog]")
				.append(" site_id=").append(site_id)
				.append(", device_id=").append(device_id)
				.append(", dhcp_type=").append(dhcp_type)
				.append(", is_renew=").append(is_renew)
				.append(", is_guest=").append(is_guest_range)
				.append(", duration=").append(duration)
				.append(", ip=").append(client_ip)
				.append(", mac=").append(client_mac)
				.append(", hostname=").append(client_hostname)
				.toString();
	}

	public String getClientDuid() {
		return this.client_duid;
	}

	public void setClientDuid(String clientDuid) {
		this.client_duid = clientDuid;
	}

	public String getClientIp() {
		return this.client_ip;
	}

	public void setClientIp(String clientIp) {
		this.client_ip = clientIp;
	}

	public String getClientMac() {
		return this.client_mac;
	}

	public void setClientMac(String clientMac) {
		this.client_mac = clientMac;
	}

	public Timestamp getCollectTime() {
		return this.collect_time;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collect_time = collectTime;
	}

	public Integer getDeviceId() {
		return this.device_id;
	}

	public void setDeviceId(Integer deviceId) {
		this.device_id = deviceId;
	}

	public String getDhcpIp() {
		return this.dhcp_ip;
	}

	public void setDhcpIp(String dhcpIp) {
		this.dhcp_ip = dhcpIp;
	}

	public String getDhcpType() {
		return this.dhcp_type;
	}

	public void setDhcpType(String dhcpType) {
		this.dhcp_type = dhcpType;
	}

	public String getClientIpType() {
		return this.client_ip_type;
	}

	public void setClientIpType(String ipType) {
		this.client_ip_type = ipType;
	}

	public Integer getSiteId() {
		return this.site_id;
	}

	public void setSiteId(Integer siteId) {
		this.site_id = siteId;
	}

	public Boolean getIsRenew() {
		return is_renew;
	}

	public void setIsRenew(Boolean is_renew) {
		this.is_renew = is_renew;
	}

	public BigInteger getClientIpNum() {
		return client_ip_num;
	}

	public void setClientIpNum(BigInteger client_ip_num) {
		this.client_ip_num = client_ip_num;
	}

	public Boolean getIsGuestRange() {
		return is_guest_range;
	}

	public void setIsGuestRange(Boolean is_guest_range) {
		this.is_guest_range = is_guest_range;
	}

	public String getClientHostname() {
		return client_hostname;
	}

	public void setClientHostname(String client_hostname) {
		this.client_hostname = client_hostname;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}