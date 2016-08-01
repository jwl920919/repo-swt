package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;


public class DhcpFixedIp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String network;
	private String ipaddr;
	private String ip_type;
	private BigInteger ip_num;
	private String comment;
	private String macaddr;
	private Boolean disable;
	private String ipv4_match_client;
	private String ipv6_address_type;	// ADDRESS, BOTH, PREFIX
	private String ipv6_duid;
	private String ipv6_prefix;
	private Integer ipv6_prefix_bits;
	private Timestamp update_time;

	public DhcpFixedIp() {
	}


	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}


	public Integer getSiteId() {
		return site_id;
	}


	public void setSiteId(Integer site_id) {
		this.site_id = site_id;
	}


	public String getNetwork() {
		return network;
	}


	public void setNetwork(String network) {
		this.network = network;
	}


	public String getIpaddr() {
		return ipaddr;
	}


	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}


	public Boolean getDisable() {
		return disable;
	}


	public void setDisable(Boolean disable) {
		this.disable = disable;
	}


	public BigInteger getIpNum() {
		return ip_num;
	}


	public void setIpNum(BigInteger ip_num) {
		this.ip_num = ip_num;
	}


	public String getMatchClient() {
		return ipv4_match_client;
	}


	public void setMatchClient(String match_client) {
		this.ipv4_match_client = match_client;
	}


	public String getIpType() {
		return ip_type;
	}


	public void setIpType(String ip_type) {
		this.ip_type = ip_type;
	}


	public String getIpv6AddressType() {
		return ipv6_address_type;
	}


	public void setIpv6AddressType(String ipv6_address_type) {
		this.ipv6_address_type = ipv6_address_type;
	}


	public String getIpv6Duid() {
		return ipv6_duid;
	}


	public void setIpv6Duid(String ipv6_duid) {
		this.ipv6_duid = ipv6_duid;
	}


	public String getIpv6Prefix() {
		return ipv6_prefix;
	}


	public void setIpv6Prefix(String ipv6_prefix) {
		this.ipv6_prefix = ipv6_prefix;
	}


	public Integer getIpv6PrefixBits() {
		return ipv6_prefix_bits;
	}


	public void setIpv6PrefixBits(Integer ipv6_prefix_bits) {
		this.ipv6_prefix_bits = ipv6_prefix_bits;
	}

}