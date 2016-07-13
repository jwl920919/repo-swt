package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;

public interface SITE_INFO_interface {
	public List<Common.DTO.SITE_INFO_DTO> select_SITE_INFO();
	public int update_SITE_INFO_ONE_RECORD(HashMap<String, Object> parameters);
	public int insert_SITE_INFO_ONE_RECORD(HashMap<String, Object> parameters);
	public int delete_SITE_INFO_RECORDS(HashMap<String, Object> parameters);
}
