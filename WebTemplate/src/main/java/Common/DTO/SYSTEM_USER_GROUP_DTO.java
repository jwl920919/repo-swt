package Common.DTO;

public class SYSTEM_USER_GROUP_DTO {
	private int site_id, group_id;
	private String group_name,group_desc;
	
	public SYSTEM_USER_GROUP_DTO() {
	}
	public SYSTEM_USER_GROUP_DTO(int site_id, int group_id, String group_name, String group_desc) {
		super();
		this.site_id = site_id;
		this.group_id = group_id;
		this.group_name = group_name;
		this.group_desc = group_desc;
	}
	
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getGroup_desc() {
		return group_desc;
	}
	public void setGroup_desc(String group_desc) {
		this.group_desc = group_desc;
	}
	
	
}
