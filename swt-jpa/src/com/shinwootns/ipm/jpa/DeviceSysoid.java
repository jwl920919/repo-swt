package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the device_sysoid database table.
 * 
 */
@Entity
@Table(name="device_sysoid")
@NamedQuery(name="DeviceSysoid.findAll", query="SELECT d FROM DeviceSysoid d")
public class DeviceSysoid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String sysoid;

	private String model;

	private String vendor;

	public DeviceSysoid() {
	}

	public String getSysoid() {
		return this.sysoid;
	}

	public void setSysoid(String sysoid) {
		this.sysoid = sysoid;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}