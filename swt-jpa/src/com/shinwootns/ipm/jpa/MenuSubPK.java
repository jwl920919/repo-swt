package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the menu_sub database table.
 * 
 */
@Embeddable
public class MenuSubPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="master_cd")
	private String masterCd;

	@Column(name="sub_cd")
	private String subCd;

	public MenuSubPK() {
	}
	public String getMasterCd() {
		return this.masterCd;
	}
	public void setMasterCd(String masterCd) {
		this.masterCd = masterCd;
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
		if (!(other instanceof MenuSubPK)) {
			return false;
		}
		MenuSubPK castOther = (MenuSubPK)other;
		return 
			this.masterCd.equals(castOther.masterCd)
			&& this.subCd.equals(castOther.subCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.masterCd.hashCode();
		hash = hash * prime + this.subCd.hashCode();
		
		return hash;
	}
}