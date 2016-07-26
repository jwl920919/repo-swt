package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the interface_cam database table.
 * 
 */
@Embeddable
public class InterfaceCamPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="if_number")
	private Integer ifNumber;

	private String macaddr;

	public InterfaceCamPK() {
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
	public String getMacaddr() {
		return this.macaddr;
	}
	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InterfaceCamPK)) {
			return false;
		}
		InterfaceCamPK castOther = (InterfaceCamPK)other;
		return 
			this.deviceId.equals(castOther.deviceId)
			&& this.ifNumber.equals(castOther.ifNumber)
			&& this.macaddr.equals(castOther.macaddr);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deviceId.hashCode();
		hash = hash * prime + this.ifNumber.hashCode();
		hash = hash * prime + this.macaddr.hashCode();
		
		return hash;
	}
}