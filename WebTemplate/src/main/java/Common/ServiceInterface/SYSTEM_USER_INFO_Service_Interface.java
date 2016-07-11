package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;

public interface SYSTEM_USER_INFO_Service_Interface {
	public List<Common.DTO.SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters);
	public int select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(HashMap<String, Object> parameters);
	public Common.DTO.SYSTEM_USER_INFO_DTO select_SYSTEM_USER_INFO_ONE_SEARCH(HashMap<String, Object> parameters);
	public int update_SYSTEM_USER_INFO_ONE_RECORD(HashMap<String, Object> parameters);
}
