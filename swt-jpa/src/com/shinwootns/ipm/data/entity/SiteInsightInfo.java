package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the site_insight_info database table.
 * 
 */
@Entity
@Table(name="site_insight_info")
@NamedQuery(name="SiteInsightInfo.findAll", query="SELECT s FROM SiteInsightInfo s")
public class SiteInsightInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="insight_id")
	private Integer insightId;

	@Column(name="device_name")
	private String deviceName;

	@Column(name="device_type")
	private String deviceType;

	@Column(name="enable_collect_cam")
	private Boolean enableCollectCam;

	@Column(name="enable_recv_syslog")
	private Boolean enableRecvSyslog;

	private String ipv4;

	private String ipv6;

	@Column(name="limit_count")
	private Integer limitCount;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="used_count")
	private Integer usedCount;

	public SiteInsightInfo() {
	}

	public Integer getInsightId() {
		return this.insightId;
	}

	public void setInsightId(Integer insightId) {
		this.insightId = insightId;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Boolean getEnableCollectCam() {
		return this.enableCollectCam;
	}

	public void setEnableCollectCam(Boolean enableCollectCam) {
		this.enableCollectCam = enableCollectCam;
	}

	public Boolean getEnableRecvSyslog() {
		return this.enableRecvSyslog;
	}

	public void setEnableRecvSyslog(Boolean enableRecvSyslog) {
		this.enableRecvSyslog = enableRecvSyslog;
	}

	public String getIpv4() {
		return this.ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getIpv6() {
		return this.ipv6;
	}

	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}

	public Integer getLimitCount() {
		return this.limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getUsedCount() {
		return this.usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

}