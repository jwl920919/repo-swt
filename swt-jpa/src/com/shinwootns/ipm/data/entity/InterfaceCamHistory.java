package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the interface_cam_history database table.
 * 
 */
@Entity
@Table(name="interface_cam_history")
@NamedQuery(name="InterfaceCamHistory.findAll", query="SELECT i FROM InterfaceCamHistory i")
public class InterfaceCamHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InterfaceCamHistoryPK id;

	private String ipaddr;

	public InterfaceCamHistory() {
	}

	public InterfaceCamHistoryPK getId() {
		return this.id;
	}

	public void setId(InterfaceCamHistoryPK id) {
		this.id = id;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

}