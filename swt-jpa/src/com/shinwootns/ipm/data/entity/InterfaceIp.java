package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the interface_ip database table.
 * 
 */
@Entity
@Table(name="interface_ip")
@NamedQuery(name="InterfaceIp.findAll", query="SELECT i FROM InterfaceIp i")
public class InterfaceIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InterfaceIpPK id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="network_ip")
	private String networkIp;

	@Column(name="network_mask")
	private String networkMask;

	@Column(name="update_time")
	private Timestamp updateTime;

	public InterfaceIp() {
	}

	public InterfaceIpPK getId() {
		return this.id;
	}

	public void setId(InterfaceIpPK id) {
		this.id = id;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getNetworkIp() {
		return this.networkIp;
	}

	public void setNetworkIp(String networkIp) {
		this.networkIp = networkIp;
	}

	public String getNetworkMask() {
		return this.networkMask;
	}

	public void setNetworkMask(String networkMask) {
		this.networkMask = networkMask;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}