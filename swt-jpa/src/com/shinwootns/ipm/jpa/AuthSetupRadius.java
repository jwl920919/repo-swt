package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_setup_radius database table.
 * 
 */
@Entity
@Table(name="auth_setup_radius")
@NamedQuery(name="AuthSetupRadius.findAll", query="SELECT a FROM AuthSetupRadius a")
public class AuthSetupRadius implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="setup_id")
	private Integer setupId;

	@Column(name="acct_port")
	private Integer acctPort;

	@Column(name="auth_port")
	private Integer authPort;

	@Column(name="called_station_id")
	private String calledStationId;

	private String host;

	@Column(name="nas_identifier")
	private String nasIdentifier;

	@Column(name="nas_ip")
	private String nasIp;

	private String password;

	@Column(name="service_type")
	private String serviceType;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="timeout_sec")
	private Integer timeoutSec;

	public AuthSetupRadius() {
	}

	public Integer getSetupId() {
		return this.setupId;
	}

	public void setSetupId(Integer setupId) {
		this.setupId = setupId;
	}

	public Integer getAcctPort() {
		return this.acctPort;
	}

	public void setAcctPort(Integer acctPort) {
		this.acctPort = acctPort;
	}

	public Integer getAuthPort() {
		return this.authPort;
	}

	public void setAuthPort(Integer authPort) {
		this.authPort = authPort;
	}

	public String getCalledStationId() {
		return this.calledStationId;
	}

	public void setCalledStationId(String calledStationId) {
		this.calledStationId = calledStationId;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getNasIdentifier() {
		return this.nasIdentifier;
	}

	public void setNasIdentifier(String nasIdentifier) {
		this.nasIdentifier = nasIdentifier;
	}

	public String getNasIp() {
		return this.nasIp;
	}

	public void setNasIp(String nasIp) {
		this.nasIp = nasIp;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getTimeoutSec() {
		return this.timeoutSec;
	}

	public void setTimeoutSec(Integer timeoutSec) {
		this.timeoutSec = timeoutSec;
	}

}