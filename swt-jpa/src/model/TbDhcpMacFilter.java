package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Array;


/**
 * The persistent class for the tb_dhcp_mac_filter database table.
 * 
 */
@Entity
@Table(name="tb_dhcp_mac_filter")
@NamedQuery(name="TbDhcpMacFilter.findAll", query="SELECT t FROM TbDhcpMacFilter t")
public class TbDhcpMacFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="filter_id")
	private Integer filterId;

	@Column(name="filter_name")
	private Array filterName;

	public TbDhcpMacFilter() {
	}

	public Integer getFilterId() {
		return this.filterId;
	}

	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}

	public Array getFilterName() {
		return this.filterName;
	}

	public void setFilterName(Array filterName) {
		this.filterName = filterName;
	}

}