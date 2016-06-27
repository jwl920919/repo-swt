package com.shinwootns.swt.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tb_site_info")
@NamedQuery(name="tbSiteInfo.findAll", query="SELECT t FROM tb_site_info")
public class SiteInfoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="site_id")
	private int	site_id;

	@Column(name="site_name", nullable=false)
	private String	site_name;

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
}
