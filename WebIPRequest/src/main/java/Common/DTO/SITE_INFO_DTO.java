package Common.DTO;

public class SITE_INFO_DTO {
	private int site_id;
	private String site_name,site_code,description,site_master;
	
	public SITE_INFO_DTO() {
		
	}
	public SITE_INFO_DTO(int site_id, String site_name, String site_code, String description, String site_master) {
		super();
		this.site_id = site_id;
		this.site_name = site_name;
		this.site_code = site_code;
		this.description = description;
		this.site_master = site_master;
	}
	
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
	public String getSite_code() {
		return site_code;
	}
	public void setSite_code(String site_code) {
		this.site_code = site_code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSite_master(String site_master) {
		this.site_master = site_master;
	}
	@Override
	public String toString() {
		return "SITE_INFO_DTO [site_id=" + site_id + ", site_name=" + site_name + ", site_code=" + site_code
				+ ", description=" + description + ", site_master=" + site_master + "]";
	}
	
	
}
