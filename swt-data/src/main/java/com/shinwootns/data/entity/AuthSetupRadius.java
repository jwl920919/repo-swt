package com.shinwootns.data.entity;

import java.io.Serializable;


public class AuthSetupRadius implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer setup_id;
	private Integer acct_port;
	private Integer auth_port;
	private String auth_type;
	private String called_station_id;
	private String host;
	private String nas_identifier;
	private String nas_ip;
	private String password;
	private String service_type;
	private Integer timeout_sec;

	public AuthSetupRadius() {
	}

	public Integer getSetupId() {
		return this.setup_id;
	}

	public void setSetupId(Integer setupId) {
		this.setup_id = setupId;
	}

	public Integer getAcctPort() {
		return this.acct_port;
	}

	public void setAcctPort(Integer acctPort) {
		this.acct_port = acctPort;
	}

	public Integer getAuthPort() {
		return this.auth_port;
	}

	public void setAuthPort(Integer authPort) {
		this.auth_port = authPort;
	}

	public String getAuthType() {
		return this.auth_type;
	}

	public void setAuthType(String authType) {
		this.auth_type = authType;
	}

	public String getCalledStationId() {
		return this.called_station_id;
	}

	public void setCalledStationId(String calledStationId) {
		this.called_station_id = calledStationId;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getNasIdentifier() {
		return this.nas_identifier;
	}

	public void setNasIdentifier(String nasIdentifier) {
		this.nas_identifier = nasIdentifier;
	}

	public String getNasIp() {
		return this.nas_ip;
	}

	public void setNasIp(String nasIp) {
		this.nas_ip = nasIp;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServiceType() {
		return this.service_type;
	}

	public void setServiceType(String serviceType) {
		this.service_type = serviceType;
	}

	public Integer getTimeoutSec() {
		return this.timeout_sec;
	}

	public void setTimeoutSec(Integer timeoutSec) {
		this.timeout_sec = timeoutSec;
	}

}