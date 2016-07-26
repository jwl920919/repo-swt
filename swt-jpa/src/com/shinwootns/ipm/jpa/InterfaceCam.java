package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the interface_cam database table.
 * 
 */
@Entity
@Table(name="interface_cam")
@NamedQuery(name="InterfaceCam.findAll", query="SELECT i FROM InterfaceCam i")
public class InterfaceCam implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InterfaceCamPK id;

	private String ipaddr;

	@Column(name="update_time")
	private Timestamp updateTime;

	public InterfaceCam() {
	}

	public InterfaceCamPK getId() {
		return this.id;
	}

	public void setId(InterfaceCamPK id) {
		this.id = id;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}