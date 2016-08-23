package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MANAGEMENT_Service_interface {
	public List<Map<String, Object>> select_EXIST_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_CLIENT_DEVICE_INFO();
	public List<Map<String, Object>> select_VIEW_CLIENT_INFO(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO_FOR_BACKUP(HashMap<String, Object> parameters);
	public int select_EXIST_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters);
	public int select_VIEW_CLIENT_INFO_TOTAL_COUNT(HashMap<String, Object> parameters);
	public int update_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int insert_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int insert_PLURAL_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int delete_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
	public int delete_ALL_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters);
}
