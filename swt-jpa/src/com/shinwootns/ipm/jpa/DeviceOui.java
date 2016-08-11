package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the device_oui database table.
 * 
 */
@Entity
@Table(name="device_oui")
@NamedQuery(name="DeviceOui.findAll", query="SELECT d FROM DeviceOui d")
public class DeviceOui implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String oui;

	private String vendor;

	public DeviceOui() {
	}

	public String getOui() {
		return this.oui;
	}

	public void setOui(String oui) {
		this.oui = oui;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}