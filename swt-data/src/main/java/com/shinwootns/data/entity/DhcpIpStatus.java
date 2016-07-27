package com.shinwootns.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class DhcpIpStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String ipaddr;
	private String discover_status;
	private String duid;
	private String fingerprint;
	private String host_os;
	private String host_name;
	private String ip_type;
	private Boolean is_conflict;
	private Boolean is_never_ends;
	private Boolean is_never_start;
	private String conflict_types;
	private Timestamp last_discovered;
	private Timestamp lease_end_time;
	private Timestamp lease_start_time;
	private String lease_state;
	private String macaddr;
	private String network;
	private String obj_types;
	private String status;
	private Timestamp update_time;
	private String usage;
	private String user_description;

	public DhcpIpStatus() {
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

	public String getHostOs() {
		return this.host_os;
	}

	public void setHostOs(String hostOs) {
		this.host_os = hostOs;
	}

	public String getHostname() {
		return this.host_name;
	}

	public void setHostname(String hostname) {
		this.host_name = hostname;
	}

	public String getIpType() {
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public Boolean getIsConflict() {
		return this.is_conflict;
	}

	public void setIsConflict(Boolean isConflict) {
		this.is_conflict = isConflict;
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

	public String getLeaseState() {
		return this.lease_state;
	}

	public void setLeaseState(String leaseState) {
		this.lease_state = leaseState;
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

	public String getObjTypes() {
		return this.obj_types;
	}

	public void setObjTypes(String objTypes) {
		this.obj_types = objTypes;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getUserDescription() {
		return this.user_description;
	}

	public void setUserDescription(String userDescription) {
		this.user_description = userDescription;
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

	public String getDiscoverStatus() {
		return discover_status;
	}

	public void setDiscoverStatus(String discover_status) {
		this.discover_status = discover_status;
	}

	public String getConflictTypes() {
		return conflict_types;
	}

	public void setConflictTypes(String conflict_types) {
		this.conflict_types = conflict_types;
	}

	public Boolean getIsNeverEnds() {
		return is_never_ends;
	}

	public void setIsNeverEnds(Boolean is_never_ends) {
		this.is_never_ends = is_never_ends;
	}

	public Boolean getIsNeverStart() {
		return is_never_start;
	}

	public void setIsNeverStart(Boolean is_never_start) {
		this.is_never_start = is_never_start;
	}

}