package Common.DTO;

import org.joda.time.DateTime;

public class UI_USER_INFO_DTO {
	private int nUserID;
	private String sUserID;
	private String sPassword;
	private String sUserName;
	private int nGroupID;
	private String sDeptName;
	private String sPosionName;
	private String sPhoneNum;
	private String sMobileNum;
	private String sEmail;
	private boolean bMySingleID;
	private String dInsertDate;
	private DateTime dModifyDate;

	protected String getsUserID() {
		return sUserID;
	}

	protected void setsUserID(String sUserID) {
		this.sUserID = sUserID;
	}

	protected String getsPassword() {
		return sPassword;
	}

	protected void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	protected String getsUserName() {
		return sUserName;
	}

	protected void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}

	protected int getnGroupID() {
		return nGroupID;
	}

	protected void setnGroupID(int nGroupID) {
		this.nGroupID = nGroupID;
	}

	protected String getsDeptName() {
		return sDeptName;
	}

	protected void setsDeptName(String sDeptName) {
		this.sDeptName = sDeptName;
	}

	protected String getsPosionName() {
		return sPosionName;
	}

	protected void setsPosionName(String sPosionName) {
		this.sPosionName = sPosionName;
	}

	protected String getsPhoneNum() {
		return sPhoneNum;
	}

	protected void setsPhoneNum(String sPhoneNum) {
		this.sPhoneNum = sPhoneNum;
	}

	protected String getsMobileNum() {
		return sMobileNum;
	}

	protected void setsMobileNum(String sMobileNum) {
		this.sMobileNum = sMobileNum;
	}

	protected String getsEmail() {
		return sEmail;
	}

	protected void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	protected boolean isbMySingleID() {
		return bMySingleID;
	}

	protected void setbMySingleID(boolean bMySingleID) {
		this.bMySingleID = bMySingleID;
	}

	protected String getdInsertDate() {
		return dInsertDate;
	}

	protected void setdInsertDate(String dInsertDate) {
		this.dInsertDate = dInsertDate;
	}

	protected DateTime getdModifyDate() {
		return dModifyDate;
	}

	protected void setdModifyDate(DateTime dModifyDate) {
		this.dModifyDate = dModifyDate;
	}

	protected int getnUserID() {
		return nUserID;
	}
}
