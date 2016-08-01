package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_setup_ldap database table.
 * 
 */
@Entity
@Table(name="auth_setup_ldap")
@NamedQuery(name="AuthSetupLdap.findAll", query="SELECT a FROM AuthSetupLdap a")
public class AuthSetupLdap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="setup_id")
	private Integer setupId;

	@Column(name="auth_type")
	private String authType;

	@Column(name="base_domain")
	private String baseDomain;

	@Column(name="bind_domain")
	private String bindDomain;

	@Column(name="bind_passwd")
	private String bindPasswd;

	@Column(name="group_class")
	private String groupClass;

	@Column(name="group_member_attr")
	private String groupMemberAttr;

	@Column(name="group_name_attr")
	private String groupNameAttr;

	private String host;

	@Column(name="kerberos_enable")
	private Boolean kerberosEnable;

	@Column(name="kerberos_host")
	private String kerberosHost;

	@Column(name="kerberos_port")
	private Integer kerberosPort;

	@Column(name="kerberos_realm")
	private String kerberosRealm;

	private Integer port;

	@Column(name="ssl_enable")
	private Boolean sslEnable;

	@Column(name="user_attr")
	private String userAttr;

	@Column(name="user_class")
	private String userClass;

	@Column(name="user_memberof_attr")
	private String userMemberofAttr;

	@Column(name="user_name_attr")
	private String userNameAttr;

	public AuthSetupLdap() {
	}

	public Integer getSetupId() {
		return this.setupId;
	}

	public void setSetupId(Integer setupId) {
		this.setupId = setupId;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getBaseDomain() {
		return this.baseDomain;
	}

	public void setBaseDomain(String baseDomain) {
		this.baseDomain = baseDomain;
	}

	public String getBindDomain() {
		return this.bindDomain;
	}

	public void setBindDomain(String bindDomain) {
		this.bindDomain = bindDomain;
	}

	public String getBindPasswd() {
		return this.bindPasswd;
	}

	public void setBindPasswd(String bindPasswd) {
		this.bindPasswd = bindPasswd;
	}

	public String getGroupClass() {
		return this.groupClass;
	}

	public void setGroupClass(String groupClass) {
		this.groupClass = groupClass;
	}

	public String getGroupMemberAttr() {
		return this.groupMemberAttr;
	}

	public void setGroupMemberAttr(String groupMemberAttr) {
		this.groupMemberAttr = groupMemberAttr;
	}

	public String getGroupNameAttr() {
		return this.groupNameAttr;
	}

	public void setGroupNameAttr(String groupNameAttr) {
		this.groupNameAttr = groupNameAttr;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getKerberosEnable() {
		return this.kerberosEnable;
	}

	public void setKerberosEnable(Boolean kerberosEnable) {
		this.kerberosEnable = kerberosEnable;
	}

	public String getKerberosHost() {
		return this.kerberosHost;
	}

	public void setKerberosHost(String kerberosHost) {
		this.kerberosHost = kerberosHost;
	}

	public Integer getKerberosPort() {
		return this.kerberosPort;
	}

	public void setKerberosPort(Integer kerberosPort) {
		this.kerberosPort = kerberosPort;
	}

	public String getKerberosRealm() {
		return this.kerberosRealm;
	}

	public void setKerberosRealm(String kerberosRealm) {
		this.kerberosRealm = kerberosRealm;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getSslEnable() {
		return this.sslEnable;
	}

	public void setSslEnable(Boolean sslEnable) {
		this.sslEnable = sslEnable;
	}

	public String getUserAttr() {
		return this.userAttr;
	}

	public void setUserAttr(String userAttr) {
		this.userAttr = userAttr;
	}

	public String getUserClass() {
		return this.userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public String getUserMemberofAttr() {
		return this.userMemberofAttr;
	}

	public void setUserMemberofAttr(String userMemberofAttr) {
		this.userMemberofAttr = userMemberofAttr;
	}

	public String getUserNameAttr() {
		return this.userNameAttr;
	}

	public void setUserNameAttr(String userNameAttr) {
		this.userNameAttr = userNameAttr;
	}

}