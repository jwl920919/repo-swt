package com.shinwootns.common.network;

public class SyslogEntity {
	
	private String host;
	private int severity;
	private int facility;
	private long recvTime;
	private String data;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public int getFacility() {
		return facility;
	}

	public void setFacility(int facility) {
		this.facility = facility;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(long recvTime) {
		this.recvTime = recvTime;
	}
}
