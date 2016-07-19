package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_fixed_ip database table.
 * 
 */
@Entity
@Table(name="dhcp_fixed_ip")
@NamedQuery(name="DhcpFixedIp.findAll", query="SELECT d FROM DhcpFixedIp d")
public class DhcpFixedIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpFixedIpPK id;

	private String comment;

	private String macaddr;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DhcpFixedIp() {
	}

	public DhcpFixedIpPK getId() {
		return this.id;
	}

	public void setId(DhcpFixedIpPK id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}