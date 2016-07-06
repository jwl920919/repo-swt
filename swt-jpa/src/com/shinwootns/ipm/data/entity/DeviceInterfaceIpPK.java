package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the device_interface_ip database table.
 * 
 */
@Embeddable
public class DeviceInterfaceIpPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="if_number")
	private Integer ifNumber;

	@Column(name="if_ipaddr")
	private String ifIpaddr;

	public DeviceInterfaceIpPK() {
	}
	public Integer getDeviceId() {
		return this.deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getIfNumber() {
		return this.ifNumber;
	}
	public void setIfNumber(Integer ifNumber) {
		this.ifNumber = ifNumber;
	}
	public String getIfIpaddr() {
		return this.ifIpaddr;
	}
	public void setIfIpaddr(String ifIpaddr) {
		this.ifIpaddr = ifIpaddr;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DeviceInterfaceIpPK)) {
			return false;
		}
		DeviceInterfaceIpPK castOther = (DeviceInterfaceIpPK)other;
		return 
			this.deviceId.equals(castOther.deviceId)
			&& this.ifNumber.equals(castOther.ifNumber)
			&& this.ifIpaddr.equals(castOther.ifIpaddr);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deviceId.hashCode();
		hash = hash * prime + this.ifNumber.hashCode();
		hash = hash * prime + this.ifIpaddr.hashCode();
		
		return hash;
	}
}