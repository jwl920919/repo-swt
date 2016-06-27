package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the system_user_group database table.
 * 
 */
@Entity
@Table(name="system_user_group")
@NamedQuery(name="SystemUserGroup.findAll", query="SELECT s FROM SystemUserGroup s")
public class SystemUserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="group_id")
	private Integer groupId;

	@Column(name="group_desc")
	private String groupDesc;

	@Column(name="group_name")
	private String groupName;

	@Column(name="site_id")
	private Integer siteId;

	public SystemUserGroup() {
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}