package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the device_interface database table.
 * 
 */
@Entity
@Table(name="device_interface")
@NamedQuery(name="DeviceInterface.findAll", query="SELECT d FROM DeviceInterface d")
public class DeviceInterface implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DeviceInterfacePK id;

	@Column(name="admin_stauts")
	private Integer adminStauts;

	@Column(name="if_alias")
	private String ifAlias;

	@Column(name="if_desc")
	private String ifDesc;

	@Column(name="if_macaddr")
	private String ifMacaddr;

	@Column(name="if_name")
	private String ifName;

	@Column(name="if_speed")
	private Integer ifSpeed;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="last_used_octect")
	private double lastUsedOctect;

	@Column(name="last_used_time")
	private Timestamp lastUsedTime;

	@Column(name="oper_stauts")
	private Integer operStauts;

	@Column(name="update_time")
	private Timestamp updateTime;

	@Column(name="user_desc")
	private String userDesc;

	public DeviceInterface() {
	}

	public DeviceInterfacePK getId() {
		return this.id;
	}

	public void setId(DeviceInterfacePK id) {
		this.id = id;
	}

	public Integer getAdminStauts() {
		return this.adminStauts;
	}

	public void setAdminStauts(Integer adminStauts) {
		this.adminStauts = adminStauts;
	}

	public String getIfAlias() {
		return this.ifAlias;
	}

	public void setIfAlias(String ifAlias) {
		this.ifAlias = ifAlias;
	}

	public String getIfDesc() {
		return this.ifDesc;
	}

	public void setIfDesc(String ifDesc) {
		this.ifDesc = ifDesc;
	}

	public String getIfMacaddr() {
		return this.ifMacaddr;
	}

	public void setIfMacaddr(String ifMacaddr) {
		this.ifMacaddr = ifMacaddr;
	}

	public String getIfName() {
		return this.ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public Integer getIfSpeed() {
		return this.ifSpeed;
	}

	public void setIfSpeed(Integer ifSpeed) {
		this.ifSpeed = ifSpeed;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public double getLastUsedOctect() {
		return this.lastUsedOctect;
	}

	public void setLastUsedOctect(double lastUsedOctect) {
		this.lastUsedOctect = lastUsedOctect;
	}

	public Timestamp getLastUsedTime() {
		return this.lastUsedTime;
	}

	public void setLastUsedTime(Timestamp lastUsedTime) {
		this.lastUsedTime = lastUsedTime;
	}

	public Integer getOperStauts() {
		return this.operStauts;
	}

	public void setOperStauts(Integer operStauts) {
		this.operStauts = operStauts;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

}