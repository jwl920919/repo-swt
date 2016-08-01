package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_ip_status database table.
 * 
 */
@Entity
@Table(name="dhcp_ip_status")
@NamedQuery(name="DhcpIpStatus.findAll", query="SELECT d FROM DhcpIpStatus d")
public class DhcpIpStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpIpStatusPK id;

	@Column(name="conflict_types")
	private String conflictTypes;

	@Column(name="discover_status")
	private String discoverStatus;

	private String duid;

	private String fingerprint;

	@Column(name="host_name")
	private String hostName;

	@Column(name="host_os")
	private String hostOs;

	@Column(name="ip_num")
	private BigDecimal ipNum;

	@Column(name="ip_type")
	private String ipType;

	@Column(name="is_conflict")
	private Boolean isConflict;

	@Column(name="is_never_ends")
	private Boolean isNeverEnds;

	@Column(name="is_never_start")
	private Boolean isNeverStart;

	@Column(name="last_discovered")
	private Timestamp lastDiscovered;

	@Column(name="lease_end_time")
	private Timestamp leaseEndTime;

	@Column(name="lease_start_time")
	private Timestamp leaseStartTime;

	@Column(name="lease_state")
	private String leaseState;

	private String macaddr;

	private String network;

	@Column(name="obj_types")
	private String objTypes;

	private String status;

	@Column(name="update_time")
	private Timestamp updateTime;

	private String usage;

	@Column(name="user_description")
	private String userDescription;

	public DhcpIpStatus() {
	}

	public DhcpIpStatusPK getId() {
		return this.id;
	}

	public void setId(DhcpIpStatusPK id) {
		this.id = id;
	}

	public String getConflictTypes() {
		return this.conflictTypes;
	}

	public void setConflictTypes(String conflictTypes) {
		this.conflictTypes = conflictTypes;
	}

	public String getDiscoverStatus() {
		return this.discoverStatus;
	}

	public void setDiscoverStatus(String discoverStatus) {
		this.discoverStatus = discoverStatus;
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

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostOs() {
		return this.hostOs;
	}

	public void setHostOs(String hostOs) {
		this.hostOs = hostOs;
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

	public Boolean getIsConflict() {
		return this.isConflict;
	}

	public void setIsConflict(Boolean isConflict) {
		this.isConflict = isConflict;
	}

	public Boolean getIsNeverEnds() {
		return this.isNeverEnds;
	}

	public void setIsNeverEnds(Boolean isNeverEnds) {
		this.isNeverEnds = isNeverEnds;
	}

	public Boolean getIsNeverStart() {
		return this.isNeverStart;
	}

	public void setIsNeverStart(Boolean isNeverStart) {
		this.isNeverStart = isNeverStart;
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

	public String getLeaseState() {
		return this.leaseState;
	}

	public void setLeaseState(String leaseState) {
		this.leaseState = leaseState;
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
		return this.objTypes;
	}

	public void setObjTypes(String objTypes) {
		this.objTypes = objTypes;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getUserDescription() {
		return this.userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

}