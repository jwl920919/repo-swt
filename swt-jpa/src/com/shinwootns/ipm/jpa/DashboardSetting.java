package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dashboard_setting database table.
 * 
 */
@Entity
@Table(name="dashboard_setting")
@NamedQuery(name="DashboardSetting.findAll", query="SELECT d FROM DashboardSetting d")
public class DashboardSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DashboardSettingPK id;

	@Column(name="display_yn")
	private Boolean displayYn;

	@Column(name="master_cd")
	private String masterCd;

	@Column(name="setting_order")
	private Integer settingOrder;

	public DashboardSetting() {
	}

	public DashboardSettingPK getId() {
		return this.id;
	}

	public void setId(DashboardSettingPK id) {
		this.id = id;
	}

	public Boolean getDisplayYn() {
		return this.displayYn;
	}

	public void setDisplayYn(Boolean displayYn) {
		this.displayYn = displayYn;
	}

	public String getMasterCd() {
		return this.masterCd;
	}

	public void setMasterCd(String masterCd) {
		this.masterCd = masterCd;
	}

	public Integer getSettingOrder() {
		return this.settingOrder;
	}

	public void setSettingOrder(Integer settingOrder) {
		this.settingOrder = settingOrder;
	}

}