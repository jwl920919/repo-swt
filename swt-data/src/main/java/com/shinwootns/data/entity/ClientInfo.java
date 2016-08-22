package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;


public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String macaddr;
	private String duid;
	private String hostname;
	private String ip_type;
	private String last_ip;
	private BigInteger last_ip_num;
	private String last_switch;
	private String last_port;
	private Timestamp position_update;
	private String model;
	private String os;
	private Timestamp update_time;
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
		return this.ip_type;
	}

	public void setIpType(String ipType) {
		this.ip_type = ipType;
	}

	public String getLastIp() {
		return this.last_ip;
	}

	public void setLastIp(String lastIp) {
		this.last_ip = lastIp;
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

	public Timestamp getUpdateTime() {
		return this.update_time;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.update_time = updateTime;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getLastSwitch() {
		return last_switch;
	}

	public void setLastSwitch(String last_switch) {
		this.last_switch = last_switch;
	}

	public String getLastPort() {
		return last_port;
	}

	public void setLastPort(String last_port) {
		this.last_port = last_port;
	}

	public Timestamp getPositionUpdate() {
		return position_update;
	}

	public void setPositionUpdate(Timestamp position_update) {
		this.position_update = position_update;
	}

	public BigInteger getLastIpNum() {
		return last_ip_num;
	}

	public void setLastIpNum(BigInteger last_ip_num) {
		this.last_ip_num = last_ip_num;
	}

}