package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_fixed_ip database table.
 * 
 */
@Entity
@Table(name="dhcp_fixed_ip")
@NamedQuery(name="DhcpFixedIp.findAll", query="SELECT d FROM DhcpFixedIp d")
public class DhcpFixedIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpFixedIpPK id;

	private String comment;

	private Boolean disable;

	@Column(name="ip_num")
	private BigDecimal ipNum;

	@Column(name="ip_type")
	private String ipType;

	@Column(name="ipv4_match_client")
	private String ipv4MatchClient;

	@Column(name="ipv6_address_type")
	private String ipv6AddressType;

	@Column(name="ipv6_duid")
	private String ipv6Duid;

	@Column(name="ipv6_prefix")
	private String ipv6Prefix;

	@Column(name="ipv6_prefix_bits")
	private Integer ipv6PrefixBits;

	private String macaddr;

	private String network;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DhcpFixedIp() {
	}

	public DhcpFixedIpPK getId() {
		return this.id;
	}

	public void setId(DhcpFixedIpPK id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getDisable() {
		return this.disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public BigDecimal getIpNum() {
		return this.ipNum;
	}

	public void setIpNum(BigDecimal ipNum) {
		this.ipNum = ipNum;
	}

	public String getIpType() {
		return this.ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public String getIpv4MatchClient() {
		return this.ipv4MatchClient;
	}

	public void setIpv4MatchClient(String ipv4MatchClient) {
		this.ipv4MatchClient = ipv4MatchClient;
	}

	public String getIpv6AddressType() {
		return this.ipv6AddressType;
	}

	public void setIpv6AddressType(String ipv6AddressType) {
		this.ipv6AddressType = ipv6AddressType;
	}

	public String getIpv6Duid() {
		return this.ipv6Duid;
	}

	public void setIpv6Duid(String ipv6Duid) {
		this.ipv6Duid = ipv6Duid;
	}

	public String getIpv6Prefix() {
		return this.ipv6Prefix;
	}

	public void setIpv6Prefix(String ipv6Prefix) {
		this.ipv6Prefix = ipv6Prefix;
	}

	public Integer getIpv6PrefixBits() {
		return this.ipv6PrefixBits;
	}

	public void setIpv6PrefixBits(Integer ipv6PrefixBits) {
		this.ipv6PrefixBits = ipv6PrefixBits;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}