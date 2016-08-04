package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the client_device_type database table.
 * 
 */
@Embeddable
public class ClientDeviceTypePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String vendor;

	private String model;

	private String os;

	public ClientDeviceTypePK() {
	}
	public String getVendor() {
		return this.vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ClientDeviceTypePK)) {
			return false;
		}
		ClientDeviceTypePK castOther = (ClientDeviceTypePK)other;
		return 
			this.vendor.equals(castOther.vendor)
			&& this.model.equals(castOther.model)
			&& this.os.equals(castOther.os);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendor.hashCode();
		hash = hash * prime + this.model.hashCode();
		hash = hash * prime + this.os.hashCode();
		
		return hash;
	}
}