package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_type database table.
 * 
 */
@Entity
@Table(name="auth_type")
@NamedQuery(name="AuthType.findAll", query="SELECT a FROM AuthType a")
public class AuthType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="auth_type")
	private String authType;

	@Column(name="auth_name")
	private String authName;

	public AuthType() {
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthName() {
		return this.authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

}