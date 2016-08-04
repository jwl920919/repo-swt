package com.shinwootns.ipm.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the user_apply_ip_info database table.
 * 
 */
@Entity
@Table(name="user_apply_ip_info")
@NamedQuery(name="UserApplyIpInfo.findAll", query="SELECT u FROM UserApplyIpInfo u")
public class UserApplyIpInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer seq;

	@Column(name="apply_description")
	private String applyDescription;

	@Column(name="apply_end_time")
	private Timestamp applyEndTime;

	@Column(name="apply_site_id")
	private Integer applySiteId;

	@Column(name="apply_start_time")
	private Timestamp applyStartTime;

	@Column(name="apply_static_ip_num")
	private BigDecimal applyStaticIpNum;

	@Column(name="apply_static_ip_type")
	private String applyStaticIpType;

	@Column(name="apply_static_ipaddr")
	private String applyStaticIpaddr;

	@Column(name="apply_time")
	private Timestamp applyTime;

	@Column(name="insert_date")
	private Timestamp insertDate;

	@Column(name="issuance_end_time")
	private Timestamp issuanceEndTime;

	@Column(name="issuance_ip_num")
	private BigDecimal issuanceIpNum;

	@Column(name="issuance_ip_type")
	private String issuanceIpType;

	@Column(name="issuance_ipaddr")
	private String issuanceIpaddr;

	@Column(name="issuance_start_time")
	private Timestamp issuanceStartTime;

	@Column(name="modify_date")
	private Timestamp modifyDate;

	@Column(name="settlement_chief_id")
	private String settlementChiefId;

	@Column(name="settlement_chief_name")
	private String settlementChiefName;

	@Column(name="settlement_description")
	private String settlementDescription;

	@Column(name="settlement_status")
	private Integer settlementStatus;

	@Column(name="settlement_time")
	private Timestamp settlementTime;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_phone_num")
	private String userPhoneNum;

	@Column(name="user_site_id")
	private Integer userSiteId;

	public UserApplyIpInfo() {
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getApplyDescription() {
		return this.applyDescription;
	}

	public void setApplyDescription(String applyDescription) {
		this.applyDescription = applyDescription;
	}

	public Timestamp getApplyEndTime() {
		return this.applyEndTime;
	}

	public void setApplyEndTime(Timestamp applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Integer getApplySiteId() {
		return this.applySiteId;
	}

	public void setApplySiteId(Integer applySiteId) {
		this.applySiteId = applySiteId;
	}

	public Timestamp getApplyStartTime() {
		return this.applyStartTime;
	}

	public void setApplyStartTime(Timestamp applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public BigDecimal getApplyStaticIpNum() {
		return this.applyStaticIpNum;
	}

	public void setApplyStaticIpNum(BigDecimal applyStaticIpNum) {
		this.applyStaticIpNum = applyStaticIpNum;
	}

	public String getApplyStaticIpType() {
		return this.applyStaticIpType;
	}

	public void setApplyStaticIpType(String applyStaticIpType) {
		this.applyStaticIpType = applyStaticIpType;
	}

	public String getApplyStaticIpaddr() {
		return this.applyStaticIpaddr;
	}

	public void setApplyStaticIpaddr(String applyStaticIpaddr) {
		this.applyStaticIpaddr = applyStaticIpaddr;
	}

	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Timestamp getIssuanceEndTime() {
		return this.issuanceEndTime;
	}

	public void setIssuanceEndTime(Timestamp issuanceEndTime) {
		this.issuanceEndTime = issuanceEndTime;
	}

	public BigDecimal getIssuanceIpNum() {
		return this.issuanceIpNum;
	}

	public void setIssuanceIpNum(BigDecimal issuanceIpNum) {
		this.issuanceIpNum = issuanceIpNum;
	}

	public String getIssuanceIpType() {
		return this.issuanceIpType;
	}

	public void setIssuanceIpType(String issuanceIpType) {
		this.issuanceIpType = issuanceIpType;
	}

	public String getIssuanceIpaddr() {
		return this.issuanceIpaddr;
	}

	public void setIssuanceIpaddr(String issuanceIpaddr) {
		this.issuanceIpaddr = issuanceIpaddr;
	}

	public Timestamp getIssuanceStartTime() {
		return this.issuanceStartTime;
	}

	public void setIssuanceStartTime(Timestamp issuanceStartTime) {
		this.issuanceStartTime = issuanceStartTime;
	}

	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSettlementChiefId() {
		return this.settlementChiefId;
	}

	public void setSettlementChiefId(String settlementChiefId) {
		this.settlementChiefId = settlementChiefId;
	}

	public String getSettlementChiefName() {
		return this.settlementChiefName;
	}

	public void setSettlementChiefName(String settlementChiefName) {
		this.settlementChiefName = settlementChiefName;
	}

	public String getSettlementDescription() {
		return this.settlementDescription;
	}

	public void setSettlementDescription(String settlementDescription) {
		this.settlementDescription = settlementDescription;
	}

	public Integer getSettlementStatus() {
		return this.settlementStatus;
	}

	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public Timestamp getSettlementTime() {
		return this.settlementTime;
	}

	public void setSettlementTime(Timestamp settlementTime) {
		this.settlementTime = settlementTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoneNum() {
		return this.userPhoneNum;
	}

	public void setUserPhoneNum(String userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	public Integer getUserSiteId() {
		return this.userSiteId;
	}

	public void setUserSiteId(Integer userSiteId) {
		this.userSiteId = userSiteId;
	}

}