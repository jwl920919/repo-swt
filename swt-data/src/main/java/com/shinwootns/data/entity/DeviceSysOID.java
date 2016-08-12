package com.shinwootns.data.entity;

import java.io.Serializable;

public class DeviceSysOID implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String sysoid;
	private String vendor;
	private String model;

	public DeviceSysOID() {
	}

	public String getSysoid() {
		return sysoid;
	}

	public void setSysoid(String sysoid) {
		this.sysoid = sysoid;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
