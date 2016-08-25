package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface USER_APPLY_IP_INFO_interface {

	public List<Map<String, Object>> select_USER_APPLY_IP_INFO(HashMap<String, Object> parameters);
	public int insert_USER_APPLY_IP_INFO(HashMap<String, Object> parameters);
	public int delete_USER_APPLY_IP_INFO(HashMap<String, Object> parameters);
}
