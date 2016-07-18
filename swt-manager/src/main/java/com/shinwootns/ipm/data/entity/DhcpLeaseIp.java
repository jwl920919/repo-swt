package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DhcpLeaseIp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String ipaddr;
	private String description;
	private String duid;
	private String fingerprint;
	private String hostname;
	private String ip_type;
	private Timestamp last_discovered;
	private Timestamp lease_end_time;
	private Timestamp lease_start_time;
	private String macaddr;
	private String os;
	private Integer segment_id;
	private String state;
	private String username;

	public DhcpLeaseIp() {
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
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public Timestamp getLastDiscovered() {
		return this.last_discovered;
	}

	public void setLastDiscovered(Timestamp lastDiscovered) {
		this.last_discovered = lastDiscovered;
	}

	public Timestamp getLeaseEndTime() {
		return this.lease_end_time;
	}

	public void setLeaseEndTime(Timestamp leaseEndTime) {
		this.lease_end_time = leaseEndTime;
	}

	public Timestamp getLeaseStartTime() {
		return this.lease_start_time;
	}

	public void setLeaseStartTime(Timestamp leaseStartTime) {
		this.lease_start_time = leaseStartTime;
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

	public Integer getSiteId() {
		return site_id;
	}

	public void setSiteId(Integer site_id) {
		this.site_id = site_id;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Integer getSegmentId() {
		return this.segment_id;
	}

	public void setSegmentId(Integer segmentId) {
		this.segment_id = segmentId;
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