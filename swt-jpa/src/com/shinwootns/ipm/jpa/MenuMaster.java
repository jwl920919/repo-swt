package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the menu_master database table.
 * 
 */
@Entity
@Table(name="menu_master")
@NamedQuery(name="MenuMaster.findAll", query="SELECT m FROM MenuMaster m")
public class MenuMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="master_cd")
	private String masterCd;

	@Column(name="insert_date")
	private Timestamp insertDate;

	@Column(name="master_desc")
	private String masterDesc;

	@Column(name="master_icon")
	private String masterIcon;

	@Column(name="master_namekey")
	private String masterNamekey;

	@Column(name="master_order")
	private Integer masterOrder;

	@Column(name="master_url")
	private String masterUrl;

	@Column(name="modify_date")
	private Timestamp modifyDate;

	public MenuMaster() {
	}

	public String getMasterCd() {
		return this.masterCd;
	}

	public void setMasterCd(String masterCd) {
		this.masterCd = masterCd;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public String getMasterDesc() {
		return this.masterDesc;
	}

	public void setMasterDesc(String masterDesc) {
		this.masterDesc = masterDesc;
	}

	public String getMasterIcon() {
		return this.masterIcon;
	}

	public void setMasterIcon(String masterIcon) {
		this.masterIcon = masterIcon;
	}

	public String getMasterNamekey() {
		return this.masterNamekey;
	}

	public void setMasterNamekey(String masterNamekey) {
		this.masterNamekey = masterNamekey;
	}

	public Integer getMasterOrder() {
		return this.masterOrder;
	}

	public void setMasterOrder(Integer masterOrder) {
		this.masterOrder = masterOrder;
	}

	public String getMasterUrl() {
		return this.masterUrl;
	}

	public void setMasterUrl(String masterUrl) {
		this.masterUrl = masterUrl;
	}

	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

}