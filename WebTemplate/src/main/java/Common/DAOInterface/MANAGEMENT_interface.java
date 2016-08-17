package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MANAGEMENT_interface {
	public List<Map<String, Object>> select_EXIST_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters);
	public int select_EXIST_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters);
	public int update_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int insert_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
}
