package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_menu database table.
 * 
 */
@Entity
@Table(name="auth_menu")
@NamedQuery(name="AuthMenu.findAll", query="SELECT a FROM AuthMenu a")
public class AuthMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="auth_menu_id")
	private Integer authMenuId;

	@Column(name="auth_state")
	private String authState;

	@Column(name="group_id")
	private Integer groupId;

	@Column(name="master_cd")
	private String masterCd;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="sub_cd")
	private String subCd;

	public AuthMenu() {
	}

	public Integer getAuthMenuId() {
		return this.authMenuId;
	}

	public void setAuthMenuId(Integer authMenuId) {
		this.authMenuId = authMenuId;
	}

	public String getAuthState() {
		return this.authState;
	}

	public void setAuthState(String authState) {
		this.authState = authState;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getMasterCd() {
		return this.masterCd;
	}

	public void setMasterCd(String masterCd) {
		this.masterCd = masterCd;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSubCd() {
		return this.subCd;
	}

	public void setSubCd(String subCd) {
		this.subCd = subCd;
	}

}