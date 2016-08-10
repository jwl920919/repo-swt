package com.shinwootns.ipm.insight.service.snmp;

public class SystemEntry {
	
	private String sysDescr;
	private String sysObjectID;
	private Long sysUpTimeSec;
	private String sysContact;
	private String sysName;
	private String sysLocation;
	private Integer sysServices;
	
	public String getSysDescr() {
		return sysDescr;
	}
	public void setSysDescr(String sysDescr) {
		this.sysDescr = sysDescr;
	}
	public String getSysObjectID() {
		return sysObjectID;
	}
	public void setSysObjectID(String sysObjectID) {
		this.sysObjectID = sysObjectID;
	}
	public Long getSysUpTimeSec() {
		return sysUpTimeSec;
	}
	public void setSysUpTimeSec(Long sysUpTimeSec) {
		this.sysUpTimeSec = sysUpTimeSec;
	}
	public String getSysContact() {
		return sysContact;
	}
	public void setSysContact(String sysContact) {
		this.sysContact = sysContact;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getSysLocation() {
		return sysLocation;
	}
	public void setSysLocation(String sysLocation) {
		this.sysLocation = sysLocation;
	}
	public Integer getSysServices() {
		return sysServices;
	}
	public void setSysServices(Integer sysServices) {
		this.sysServices = sysServices;
	}
	
}
