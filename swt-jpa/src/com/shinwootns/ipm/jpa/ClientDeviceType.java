package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the client_device_type database table.
 * 
 */
@Entity
@Table(name="client_device_type")
@NamedQuery(name="ClientDeviceType.findAll", query="SELECT c FROM ClientDeviceType c")
public class ClientDeviceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ClientDeviceTypePK id;

	@Column(name="compare_type_hostname")
	private String compareTypeHostname;

	@Column(name="compare_type_model")
	private String compareTypeModel;

	@Column(name="compare_type_os")
	private String compareTypeOs;

	@Column(name="compare_type_vendor")
	private String compareTypeVendor;

	@Column(name="device_type")
	private String deviceType;

	public ClientDeviceType() {
	}

	public ClientDeviceTypePK getId() {
		return this.id;
	}

	public void setId(ClientDeviceTypePK id) {
		this.id = id;
	}

	public String getCompareTypeHostname() {
		return this.compareTypeHostname;
	}

	public void setCompareTypeHostname(String compareTypeHostname) {
		this.compareTypeHostname = compareTypeHostname;
	}

	public String getCompareTypeModel() {
		return this.compareTypeModel;
	}

	public void setCompareTypeModel(String compareTypeModel) {
		this.compareTypeModel = compareTypeModel;
	}

	public String getCompareTypeOs() {
		return this.compareTypeOs;
	}

	public void setCompareTypeOs(String compareTypeOs) {
		this.compareTypeOs = compareTypeOs;
	}

	public String getCompareTypeVendor() {
		return this.compareTypeVendor;
	}

	public void setCompareTypeVendor(String compareTypeVendor) {
		this.compareTypeVendor = compareTypeVendor;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}