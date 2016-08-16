package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class InterfaceInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer device_id;
	private Integer if_number;
	private String if_desc;
	private String if_name;
	private String if_macaddr;
	private Integer if_speed;
	private String if_alias;
	private Integer admin_stauts;
	private Integer oper_stauts;
	private BigDecimal last_octet;
	private Timestamp last_octet_update;

	public InterfaceInfo() {
	}
	
	@Override
	public String toString() {
		return (new StringBuilder())
		.append("[").append(if_number).append("]")
		.append(" name=").append(if_name)
		.append(", desc=").append(if_desc)
		.append(", macaddr=").append(if_macaddr)
		.append(", speed=").append(if_speed)
		.append(", alias=").append(if_alias)
		.append(", adminStatus=").append(admin_stauts)
		.append(", operStauts=").append(oper_stauts)
		.append(", inOctet=").append(last_octet)
		.toString();
	}

	public Integer getAdminStauts() {
		return this.admin_stauts;
	}

	public void setAdminStauts(Integer adminStauts) {
		this.admin_stauts = adminStauts;
	}

	public String getIfAlias() {
		return this.if_alias;
	}

	public void setIfAlias(String ifAlias) {
		this.if_alias = ifAlias;
	}

	public String getIfDesc() {
		return this.if_desc;
	}

	public void setIfDesc(String ifDesc) {
		this.if_desc = ifDesc;
	}

	public String getIfMacaddr() {
		return this.if_macaddr;
	}

	public void setIfMacaddr(String ifMacaddr) {
		this.if_macaddr = ifMacaddr;
	}

	public String getIfName() {
		return this.if_name;
	}

	public void setIfName(String ifName) {
		this.if_name = ifName;
	}

	public Integer getIfSpeed() {
		return this.if_speed;
	}

	public void setIfSpeed(Integer ifSpeed) {
		this.if_speed = ifSpeed;
	}

	public BigDecimal getLastOctet() {
		return this.last_octet;
	}

	public void setLastOctet(BigDecimal lastOctet) {
		this.last_octet = lastOctet;
	}

	public Integer getOperStauts() {
		return this.oper_stauts;
	}

	public void setOperStauts(Integer operStauts) {
		this.oper_stauts = operStauts;
	}

	public Integer getDeviceId() {
		return device_id;
	}

	public void setDeviceId(Integer device_id) {
		this.device_id = device_id;
	}

	public Integer getIfNumber() {
		return if_number;
	}

	public void setIfNumber(Integer if_number) {
		this.if_number = if_number;
	}

	public Timestamp getLastOctetUpdate() {
		return last_octet_update;
	}

	public void setLastOctetUpdate(Timestamp last_octet_update) {
		this.last_octet_update = last_octet_update;
	}

}