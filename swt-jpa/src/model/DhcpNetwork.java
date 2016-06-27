package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dhcp_network database table.
 * 
 */
@Entity
@Table(name="dhcp_network")
@NamedQuery(name="DhcpNetwork.findAll", query="SELECT d FROM DhcpNetwork d")
public class DhcpNetwork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dhcp_network_id")
	private Integer dhcpNetworkId;

	@Column(name="end_ip")
	private String endIp;

	@Column(name="filter_name")
	private String filterName;

	private String network;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="start_ip")
	private String startIp;

	public DhcpNetwork() {
	}

	public Integer getDhcpNetworkId() {
		return this.dhcpNetworkId;
	}

	public void setDhcpNetworkId(Integer dhcpNetworkId) {
		this.dhcpNetworkId = dhcpNetworkId;
	}

	public String getEndIp() {
		return this.endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public String getFilterName() {
		return this.filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
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

	public String getStartIp() {
		return this.startIp;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

}