package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dhcp_range database table.
 * 
 */
@Entity
@Table(name="dhcp_range")
@NamedQuery(name="DhcpRange.findAll", query="SELECT d FROM DhcpRange d")
public class DhcpRange implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DhcpRangePK id;

	private String comment;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="update_time")
	private Timestamp updateTime;

	public DhcpRange() {
	}

	public DhcpRangePK getId() {
		return this.id;
	}

	public void setId(DhcpRangePK id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}