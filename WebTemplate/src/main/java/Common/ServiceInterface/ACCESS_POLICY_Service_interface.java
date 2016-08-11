package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ACCESS_POLICY_Service_interface {
	public List<Map<String, Object>> select_POLICY_TABLE_SITE_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_OS_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_VENDOR_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_MODEL_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_HOSTNAME_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_DEVICE_TYPE_SEARCH(HashMap<String, Object> parameters);
	public int update_ACCESS_POLICY_INFORM(HashMap<String, Object> parameters);
	public int insert_ACCESS_POLICY_INFORM_ONE_RECORD(HashMap<String, Object> parameters);
	public int delete_ACCESS_POLICY_INFORM_RECORDS(HashMap<String, Object> parameters);
}
