package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the client_info database table.
 * 
 */
@Entity
@Table(name="client_info")
@NamedQuery(name="ClientInfo.findAll", query="SELECT c FROM ClientInfo c")
public class ClientInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String macaddr;

	private String duid;

	private String hostname;

	@Column(name="ip_type")
	private String ipType;

	@Column(name="last_ip")
	private String lastIp;

	@Column(name="last_port")
	private String lastPort;

	@Column(name="last_switch")
	private String lastSwitch;

	private String model;

	private String os;

	@Column(name="position_update")
	private Timestamp positionUpdate;

	@Column(name="update_time")
	private Timestamp updateTime;

	@Column(name="user_comment")
	private String userComment;

	private String vendor;

	public ClientInfo() {
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getDuid() {
		return this.duid;
	}

	public void setDuid(String duid) {
		this.duid = duid;
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

	public String getLastIp() {
		return this.lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getLastPort() {
		return this.lastPort;
	}

	public void setLastPort(String lastPort) {
		this.lastPort = lastPort;
	}

	public String getLastSwitch() {
		return this.lastSwitch;
	}

	public void setLastSwitch(String lastSwitch) {
		this.lastSwitch = lastSwitch;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Timestamp getPositionUpdate() {
		return this.positionUpdate;
	}

	public void setPositionUpdate(Timestamp positionUpdate) {
		this.positionUpdate = positionUpdate;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserComment() {
		return this.userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}