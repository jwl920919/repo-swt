package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the system_user_info database table.
 * 
 */
@Entity
@Table(name="system_user_info")
@NamedQuery(name="SystemUserInfo.findAll", query="SELECT s FROM SystemUserInfo s")
public class SystemUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private String userId;

	@Column(name="dept_name")
	private String deptName;

	private String email;

	@Column(name="group_id")
	private Integer groupId;

	@Column(name="insert_date")
	private Timestamp insertDate;

	@Column(name="mobile_num")
	private String mobileNum;

	@Column(name="modify_date")
	private Timestamp modifyDate;

	@Column(name="mysingle_yn")
	private Boolean mysingleYn;

	@Column(name="phone_num")
	private String phoneNum;

	@Column(name="position_name")
	private String positionName;

	@Column(name="site_id")
	private Integer siteId;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_pw")
	private String userPw;

	@Column(name="user_seq")
	private Integer userSeq;

	public SystemUserInfo() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public String getMobileNum() {
		return this.mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Boolean getMysingleYn() {
		return this.mysingleYn;
	}

	public void setMysingleYn(Boolean mysingleYn) {
		this.mysingleYn = mysingleYn;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPw() {
		return this.userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public Integer getUserSeq() {
		return this.userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}

}