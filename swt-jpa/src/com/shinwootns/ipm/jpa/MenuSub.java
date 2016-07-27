package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the menu_sub database table.
 * 
 */
@Entity
@Table(name="menu_sub")
@NamedQuery(name="MenuSub.findAll", query="SELECT m FROM MenuSub m")
public class MenuSub implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MenuSubPK id;

	@Column(name="controller_mapping")
	private String controllerMapping;

	@Column(name="display_yn")
	private Boolean displayYn;

	@Column(name="insert_date")
	private Timestamp insertDate;

	@Column(name="modify_date")
	private Timestamp modifyDate;

	@Column(name="sub_desc")
	private String subDesc;

	@Column(name="sub_icon")
	private String subIcon;

	@Column(name="sub_order")
	private Integer subOrder;

	@Column(name="\"sub_Url\"")
	private String sub_Url;

	@Column(name="subname_key")
	private String subnameKey;

	public MenuSub() {
	}

	public MenuSubPK getId() {
		return this.id;
	}

	public void setId(MenuSubPK id) {
		this.id = id;
	}

	public String getControllerMapping() {
		return this.controllerMapping;
	}

	public void setControllerMapping(String controllerMapping) {
		this.controllerMapping = controllerMapping;
	}

	public Boolean getDisplayYn() {
		return this.displayYn;
	}

	public void setDisplayYn(Boolean displayYn) {
		this.displayYn = displayYn;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSubDesc() {
		return this.subDesc;
	}

	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}

	public String getSubIcon() {
		return this.subIcon;
	}

	public void setSubIcon(String subIcon) {
		this.subIcon = subIcon;
	}

	public Integer getSubOrder() {
		return this.subOrder;
	}

	public void setSubOrder(Integer subOrder) {
		this.subOrder = subOrder;
	}

	public String getSub_Url() {
		return this.sub_Url;
	}

	public void setSub_Url(String sub_Url) {
		this.sub_Url = sub_Url;
	}

	public String getSubnameKey() {
		return this.subnameKey;
	}

	public void setSubnameKey(String subnameKey) {
		this.subnameKey = subnameKey;
	}

}