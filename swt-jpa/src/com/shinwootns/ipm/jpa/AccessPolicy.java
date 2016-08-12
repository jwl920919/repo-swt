package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the access_policy database table.
 * 
 */
@Entity
@Table(name="access_policy")
@NamedQuery(name="AccessPolicy.findAll", query="SELECT a FROM AccessPolicy a")
public class AccessPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="access_policy_id")
	private Integer accessPolicyId;

	private String desc;

	@Column(name="device_type")
	private String deviceType;

	@Column(name="device_type_like")
	private Boolean deviceTypeLike;

	private String hostname;

	@Column(name="hostname_like")
	private Boolean hostnameLike;

	@Column(name="is_permit")
	private Boolean isPermit;

	private String model;

	@Column(name="model_like")
	private Boolean modelLike;

	private String os;

	@Column(name="os_like")
	private Boolean osLike;

	private Integer priority;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="update_time")
	private Timestamp updateTime;

	private String vendor;

	@Column(name="vendor_like")
	private Boolean vendorLike;

	public AccessPolicy() {
	}

	public Integer getAccessPolicyId() {
		return this.accessPolicyId;
	}

	public void setAccessPolicyId(Integer accessPolicyId) {
		this.accessPolicyId = accessPolicyId;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Boolean getDeviceTypeLike() {
		return this.deviceTypeLike;
	}

	public void setDeviceTypeLike(Boolean deviceTypeLike) {
		this.deviceTypeLike = deviceTypeLike;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Boolean getHostnameLike() {
		return this.hostnameLike;
	}

	public void setHostnameLike(Boolean hostnameLike) {
		this.hostnameLike = hostnameLike;
	}

	public Boolean getIsPermit() {
		return this.isPermit;
	}

	public void setIsPermit(Boolean isPermit) {
		this.isPermit = isPermit;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Boolean getModelLike() {
		return this.modelLike;
	}

	public void setModelLike(Boolean modelLike) {
		this.modelLike = modelLike;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Boolean getOsLike() {
		return this.osLike;
	}

	public void setOsLike(Boolean osLike) {
		this.osLike = osLike;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Boolean getVendorLike() {
		return this.vendorLike;
	}

	public void setVendorLike(Boolean vendorLike) {
		this.vendorLike = vendorLike;
	}

}