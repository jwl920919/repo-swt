package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the interface_cam_history database table.
 * 
 */
@Embeddable
public class InterfaceCamHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="device_id")
	private Integer deviceId;

	@Column(name="if_number")
	private Integer ifNumber;

	private String macaddr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_time")
	private java.util.Date insertTime;

	public InterfaceCamHistoryPK() {
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
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InterfaceCamHistoryPK)) {
			return false;
		}
		InterfaceCamHistoryPK castOther = (InterfaceCamHistoryPK)other;
		return 
			this.deviceId.equals(castOther.deviceId)
			&& this.ifNumber.equals(castOther.ifNumber)
			&& this.macaddr.equals(castOther.macaddr)
			&& this.insertTime.equals(castOther.insertTime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.deviceId.hashCode();
		hash = hash * prime + this.ifNumber.hashCode();
		hash = hash * prime + this.macaddr.hashCode();
		hash = hash * prime + this.insertTime.hashCode();
		
		return hash;
	}
}