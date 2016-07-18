package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_lease_ip database table.
 * 
 */
@Entity
@Table(name="dhcp_lease_ip")
@NamedQuery(name="DhcpLeaseIp.findAll", query="SELECT d FROM DhcpLeaseIp d")
public class DhcpLeaseIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpLeaseIpPK id;

	private String description;

	private String duid;

	private String fingerprint;

	private String hostname;

	@Column(name="ip_type")
	private String ipType;

	@Column(name="last_discovered")
	private Timestamp lastDiscovered;

	@Column(name="lease_end_time")
	private Timestamp leaseEndTime;

	@Column(name="lease_start_time")
	private Timestamp leaseStartTime;

	private String macaddr;

	private String os;

	@Column(name="segment_id")
	private Integer segmentId;

	private String state;

	private String username;

	public DhcpLeaseIp() {
	}

	public DhcpLeaseIpPK getId() {
		return this.id;
	}

	public void setId(DhcpLeaseIpPK id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuid() {
		return this.duid;
	}

	public void setDuid(String duid) {
		this.duid = duid;
	}

	public String getFingerprint() {
		return this.fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIpType() {
		return this.ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public Timestamp getLastDiscovered() {
		return this.lastDiscovered;
	}

	public void setLastDiscovered(Timestamp lastDiscovered) {
		this.lastDiscovered = lastDiscovered;
	}

	public Timestamp getLeaseEndTime() {
		return this.leaseEndTime;
	}

	public void setLeaseEndTime(Timestamp leaseEndTime) {
		this.leaseEndTime = leaseEndTime;
	}

	public Timestamp getLeaseStartTime() {
		return this.leaseStartTime;
	}

	public void setLeaseStartTime(Timestamp leaseStartTime) {
		this.leaseStartTime = leaseStartTime;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Integer getSegmentId() {
		return this.segmentId;
	}

	public void setSegmentId(Integer segmentId) {
		this.segmentId = segmentId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}