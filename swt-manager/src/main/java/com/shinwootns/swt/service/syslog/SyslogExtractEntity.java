package com.shinwootns.swt.service.syslog;

public class SyslogExtractEntity {
	
	private boolean isFindFlag = false;
	private String value = "";
	private int lastIndex = -1;
	
	public boolean isFindFlag() {
		return isFindFlag;
	}
	public void setFindFlag(boolean isFindFlag) {
		this.isFindFlag = isFindFlag;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
}
