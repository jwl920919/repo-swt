package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dashboard_setting database table.
 * 
 */
@Embeddable
public class DashboardSettingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="sub_cd")
	private String subCd;

	public DashboardSettingPK() {
	}
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getSubCd() {
		return this.subCd;
	}
	public void setSubCd(String subCd) {
		this.subCd = subCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DashboardSettingPK)) {
			return false;
		}
		DashboardSettingPK castOther = (DashboardSettingPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.subCd.equals(castOther.subCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.subCd.hashCode();
		
		return hash;
	}
}