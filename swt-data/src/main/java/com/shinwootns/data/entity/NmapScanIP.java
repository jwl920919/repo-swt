package com.shinwootns.data.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class NmapScanIP implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer site_id;
	private String network;
	private String ipaddr;
	private BigInteger ip_num;
	private String ip_type;
	
	private String nmap_macaddr;
	private String nmap_vendor;
	private String nmap_os;
	
	public Integer getSiteId() {
		return site_id;
	}
	public void setSiteId(Integer site_id) {
		this.site_id = site_id;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public BigInteger getIpNum() {
		return ip_num;
	}
	public void setIpNum(BigInteger ip_num) {
		this.ip_num = ip_num;
	}
	public String getIpType() {
		return ip_type;
	}
	public void setIpType(String ip_type) {
		this.ip_type = ip_type;
	}
	public String getNmapMacaddr() {
		return nmap_macaddr;
	}
	public void setNmapMacaddr(String nmap_macaddr) {
		this.nmap_macaddr = nmap_macaddr;
	}
	public String getNmapVendor() {
		return nmap_vendor;
	}
	public void setNmapVendor(String nmap_vendor) {
		this.nmap_vendor = nmap_vendor;
	}
	public String getNmapOs() {
		return nmap_os;
	}
	public void setNmapOs(String nmap_os) {
		this.nmap_os = nmap_os;
	}
}
