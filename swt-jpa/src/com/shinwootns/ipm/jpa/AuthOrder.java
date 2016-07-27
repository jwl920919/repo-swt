package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_order database table.
 * 
 */
@Entity
@Table(name="auth_order")
@NamedQuery(name="AuthOrder.findAll", query="SELECT a FROM AuthOrder a")
public class AuthOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuthOrderPK id;

	@Column(name="sequence_type")
	private String sequenceType;

	public AuthOrder() {
	}

	public AuthOrderPK getId() {
		return this.id;
	}

	public void setId(AuthOrderPK id) {
		this.id = id;
	}

	public String getSequenceType() {
		return this.sequenceType;
	}

	public void setSequenceType(String sequenceType) {
		this.sequenceType = sequenceType;
	}

}