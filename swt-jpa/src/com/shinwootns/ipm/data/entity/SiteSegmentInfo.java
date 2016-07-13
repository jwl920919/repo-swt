package com.shinwootns.ipm.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the site_segment_info database table.
 * 
 */
@Entity
@Table(name="site_segment_info")
@NamedQuery(name="SiteSegmentInfo.findAll", query="SELECT s FROM SiteSegmentInfo s")
public class SiteSegmentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="segment_id")
	private Integer segmentId;

	@Column(name="end_ip")
	private Long endIp;

	@Column(name="ip_count")
	private Integer ipCount;

	private String network;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="start_ip")
	private Long startIp;

	public SiteSegmentInfo() {
	}

	public Integer getSegmentId() {
		return this.segmentId;
	}

	public void setSegmentId(Integer segmentId) {
		this.segmentId = segmentId;
	}

	public Long getEndIp() {
		return this.endIp;
	}

	public void setEndIp(Long endIp) {
		this.endIp = endIp;
	}

	public Integer getIpCount() {
		return this.ipCount;
	}

	public void setIpCount(Integer ipCount) {
		this.ipCount = ipCount;
	}

	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Long getStartIp() {
		return this.startIp;
	}

	public void setStartIp(Long startIp) {
		this.startIp = startIp;
	}

}