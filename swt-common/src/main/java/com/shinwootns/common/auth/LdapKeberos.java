package com.shinwootns.common.auth;

public class LdapKeberos {

	private boolean isKerberos = false;
	private String kerberosHost = "";
	private Integer kerberosPort = 88;		// Default: 88
	private String kerberosRealm = "";
	
	public boolean isKerberos() {
		return isKerberos;
	}
	public void setKerberos(boolean isKerberos) {
		this.isKerberos = isKerberos;
	}
	public String getKerberosHost() {
		return kerberosHost;
	}
	public void setKerberosHost(String kerberosHost) {
		this.kerberosHost = kerberosHost;
	}
	public Integer getKerberosPort() {
		return kerberosPort;
	}
	public void setKerberosPort(Integer kerberosPort) {
		this.kerberosPort = kerberosPort;
	}
	public String getKerberosRealm() {
		return kerberosRealm;
	}
	public void setKerberosRealm(String kerberosRealm) {
		this.kerberosRealm = kerberosRealm;
	}
}
