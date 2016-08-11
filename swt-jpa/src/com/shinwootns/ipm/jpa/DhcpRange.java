package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
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

	private Boolean disable;

	@Column(name="end_num")
	private BigDecimal endNum;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="ip_count")
	private BigDecimal ipCount;

	@Column(name="ip_type")
	private String ipType;

	@Column(name="start_num")
	private BigDecimal startNum;

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

	public Boolean getDisable() {
		return this.disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public BigDecimal getEndNum() {
		return this.endNum;
	}

	public void setEndNum(BigDecimal endNum) {
		this.endNum = endNum;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public BigDecimal getIpCount() {
		return this.ipCount;
	}

	public void setIpCount(BigDecimal ipCount) {
		this.ipCount = ipCount;
	}

	public String getIpType() {
		return this.ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public BigDecimal getStartNum() {
		return this.startNum;
	}

	public void setStartNum(BigDecimal startNum) {
		this.startNum = startNum;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}