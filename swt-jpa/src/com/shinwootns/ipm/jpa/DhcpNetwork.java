package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_network database table.
 * 
 */
@Entity
@Table(name="dhcp_network")
@NamedQuery(name="DhcpNetwork.findAll", query="SELECT d FROM DhcpNetwork d")
public class DhcpNetwork implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpNetworkPK id;

	private String comment;

	@Column(name="end_ip")
	private String endIp;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="start_ip")
	private String startIp;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DhcpNetwork() {
	}

	public DhcpNetworkPK getId() {
		return this.id;
	}

	public void setId(DhcpNetworkPK id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEndIp() {
		return this.endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getStartIp() {
		return this.startIp;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}