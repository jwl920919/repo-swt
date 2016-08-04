package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the client_connection_history database table.
 * 
 */
@Entity
@Table(name="client_connection_history")
@NamedQuery(name="ClientConnectionHistory.findAll", query="SELECT c FROM ClientConnectionHistory c")
public class ClientConnectionHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String macaddr;

	@Column(name="client_ip")
	private String clientIp;

	@Column(name="collect_time")
	private Timestamp collectTime;

	@Column(name="ip_type")
	private String ipType;

	private String port;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="switch")
	private String switch_;

	public ClientConnectionHistory() {
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Timestamp getCollectTime() {
		return this.collectTime;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}

	public String getIpType() {
		return this.ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSwitch_() {
		return this.switch_;
	}

	public void setSwitch_(String switch_) {
		this.switch_ = switch_;
	}

}