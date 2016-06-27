package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_setup_esb database table.
 * 
 */
@Entity
@Table(name="auth_setup_esb")
@NamedQuery(name="AuthSetupEsb.findAll", query="SELECT a FROM AuthSetupEsb a")
public class AuthSetupEsb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="setup_id")
	private Integer setupId;

	@Column(name="site_id")
	private Integer siteId;

	private String url;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_passwd")
	private String userPasswd;

	public AuthSetupEsb() {
	}

	public Integer getSetupId() {
		return this.setupId;
	}

	public void setSetupId(Integer setupId) {
		this.setupId = setupId;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPasswd() {
		return this.userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

}