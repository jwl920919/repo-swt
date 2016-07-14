package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SYSTEM_USER_GROUP_INFO_Service_interface {
	public List<Common.DTO.SYSTEM_USER_GROUP_DTO> select_SYSTEM_USER_GROUP_INFO();
	public List<Map<String, Object>> select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters);
	public int update_SYSTEM_USER_GROUP_INFO_ONE_RECORD(HashMap<String, Object> parameters);
	public int insert_SYSTEM_USER_GROUP_INFO_ONE_RECORD(HashMap<String, Object> parameters);
	public int delete_SYSTEM_USER_GROUP_INFO_RECORDS(HashMap<String, Object> parameters);
}
