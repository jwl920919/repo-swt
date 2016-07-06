package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_interface_ip database table.
 * 
 */
@Entity
@Table(name="device_interface_ip")
@NamedQuery(name="DeviceInterfaceIp.findAll", query="SELECT d FROM DeviceInterfaceIp d")
public class DeviceInterfaceIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DeviceInterfaceIpPK id;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="network_ip")
	private String networkIp;

	@Column(name="network_mask")
	private String networkMask;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DeviceInterfaceIp() {
	}

	public DeviceInterfaceIpPK getId() {
		return this.id;
	}

	public void setId(DeviceInterfaceIpPK id) {
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