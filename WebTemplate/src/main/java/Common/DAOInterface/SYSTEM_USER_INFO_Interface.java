package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;


public interface SYSTEM_USER_INFO_Interface {
	public List<Common.DTO.SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters);
	public int select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(HashMap<String, Object> parameters);
}
