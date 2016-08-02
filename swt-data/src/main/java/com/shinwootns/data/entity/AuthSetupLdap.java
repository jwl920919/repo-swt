package com.shinwootns.data.entity;

import java.io.Serializable;


public class AuthSetupLdap implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer setup_id;
	private String auth_type;
	private String base_domain;
	private String bind_domain;
	private String bind_passwd;
	private String group_class;
	private String group_member_attr;
	private String group_name_attr;
	private String host;
	private Boolean kerberos_enable;
	private String kerberos_host;
	private Integer kerberos_port;
	private String kerberos_realm;
	private Integer port;
	private Boolean ssl_enable;
	private String user_attr;
	private String user_class;
	private String user_memberof_attr;
	private String user_name_attr;

	public AuthSetupLdap() {
	}

	public Integer getSetupId() {
		return this.setup_id;
	}

	public void setSetupId(Integer setupId) {
		this.setup_id = setupId;
	}

	public String getAuthType() {
		return this.auth_type;
	}

	public void setAuthType(String authType) {
		this.auth_type = authType;
	}

	public String getBaseDomain() {
		return this.base_domain;
	}

	public void setBaseDomain(String baseDomain) {
		this.base_domain = baseDomain;
	}

	public String getBindDomain() {
		return this.bind_domain;
	}

	public void setBindDomain(String bindDomain) {
		this.bind_domain = bindDomain;
	}

	public String getBindPasswd() {
		return this.bind_passwd;
	}

	public void setBindPasswd(String bindPasswd) {
		this.bind_passwd = bindPasswd;
	}

	public String getGroupClass() {
		return this.group_class;
	}

	public void setGroupClass(String groupClass) {
		this.group_class = groupClass;
	}

	public String getGroupMemberAttr() {
		return this.group_member_attr;
	}

	public void setGroupMemberAttr(String groupMemberAttr) {
		this.group_member_attr = groupMemberAttr;
	}

	public String getGroupNameAttr() {
		return this.group_name_attr;
	}

	public void setGroupNameAttr(String groupNameAttr) {
		this.group_name_attr = groupNameAttr;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getKerberosEnable() {
		return this.kerberos_enable;
	}

	public void setKerberosEnable(Boolean kerberosEnable) {
		this.kerberos_enable = kerberosEnable;
	}

	public String getKerberosHost() {
		return this.kerberos_host;
	}

	public void setKerberosHost(String kerberosHost) {
		this.kerberos_host = kerberosHost;
	}

	public Integer getKerberosPort() {
		return this.kerberos_port;
	}

	public void setKerberosPort(Integer kerberosPort) {
		this.kerberos_port = kerberosPort;
	}

	public String getKerberosRealm() {
		return this.kerberos_realm;
	}

	public void setKerberosRealm(String kerberosRealm) {
		this.kerberos_realm = kerberosRealm;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getSslEnable() {
		return this.ssl_enable;
	}

	public void setSslEnable(Boolean sslEnable) {
		this.ssl_enable = sslEnable;
	}

	public String getUserAttr() {
		return this.user_attr;
	}

	public void setUserAttr(String userAttr) {
		this.user_attr = userAttr;
	}

	public String getUserClass() {
		return this.user_class;
	}

	public void setUserClass(String userClass) {
		this.user_class = userClass;
	}

	public String getUserMemberofAttr() {
		return this.user_memberof_attr;
	}

	public void setUserMemberofAttr(String userMemberofAttr) {
		this.user_memberof_attr = userMemberofAttr;
	}

	public String getUserNameAttr() {
		return this.user_name_attr;
	}

	public void setUserNameAttr(String userNameAttr) {
		this.user_name_attr = userNameAttr;
	}

}