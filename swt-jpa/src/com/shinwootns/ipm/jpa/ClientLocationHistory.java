package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the client_location_history database table.
 * 
 */
@Entity
@Table(name="client_location_history")
@NamedQuery(name="ClientLocationHistory.findAll", query="SELECT c FROM ClientLocationHistory c")
public class ClientLocationHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String macaddr;

	@Column(name="client_ip")
	private String clientIp;

	@Column(name="collect_time")
	private Timestamp collectTime;

	@Column(name="ip_type")
	private String ipType;

	@Column(name="port_name")
	private String portName;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="switch_name")
	private String switchName;

	public ClientLocationHistory() {
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

	public String getPortName() {
		return this.portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSwitchName() {
		return this.switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

}