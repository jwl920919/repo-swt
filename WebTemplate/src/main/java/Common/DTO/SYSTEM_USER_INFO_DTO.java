package Common.DTO;

import java.sql.Timestamp;
import java.util.Date;

public class SYSTEM_USER_INFO_DTO {
	private int site_id,user_seq,group_id;
	private String user_id,user_pw,user_name,dept_name,posion_name,phone_num,mobile_num,email;
	private boolean mysingle_yn;
	private Timestamp insert_date,modify_date;
	
	public SYSTEM_USER_INFO_DTO() {
	}
	
	
	public SYSTEM_USER_INFO_DTO(int site_id, int user_seq, int group_id, String user_id, String user_pw, String user_name,
			String dept_name, String posion_name, String phone_num, String mobile_num, String email,
			boolean mysingle_yn, Timestamp insert_date, Timestamp modify_date) {
		super();
		this.site_id = site_id;
		this.user_seq = user_seq;
		this.group_id = group_id;
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.dept_name = dept_name;
		this.posion_name = posion_name;
		this.phone_num = phone_num;
		this.mobile_num = mobile_num;
		this.email = email;
		this.mysingle_yn = mysingle_yn;
		this.insert_date = insert_date;
		this.modify_date = modify_date;
	}
	

	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public int getUser_seq() {
		return user_seq;
	}
	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getPosion_name() {
		return posion_name;
	}
	public void setPosion_name(String posion_name) {
		this.posion_name = posion_name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getMobile_num() {
		return mobile_num;
	}
	public void setMobile_num(String mobile_num) {
		this.mobile_num = mobile_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isMysingle_yn() {
		return mysingle_yn;
	}
	public void setMysingle_yn(boolean mysingle_yn) {
		this.mysingle_yn = mysingle_yn;
	}

	public Timestamp getInsert_date() {
		return insert_date;
	}


	public void setInsert_date(Timestamp insert_date) {
		this.insert_date = insert_date;
	}


	public Timestamp getModify_date() {
		return modify_date;
	}


	public void setModify_date(Timestamp modify_date) {
		this.modify_date = modify_date;
	}


	@Override
	public String toString() {
		return "SYSTEM_USER_INFO_DTO [site_id=" + site_id + ", user_seq=" + user_seq + ", group_id=" + group_id
				+ ", user_id=" + user_id + ", user_pw=" + user_pw + ", user_name=" + user_name + ", dept_name="
				+ dept_name + ", posion_name=" + posion_name + ", phone_num=" + phone_num + ", mobile_num=" + mobile_num
				+ ", email=" + email + ", mysingle_yn=" + mysingle_yn + ", insert_date=" + Common.Helper.CommonHelper.convertTimestampToString(insert_date)
				+ ", modify_date=" + Common.Helper.CommonHelper.convertTimestampToString(modify_date) + "]";
	}
	
	
	
}
