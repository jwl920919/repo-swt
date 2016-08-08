package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ACCESS_POLICY_interface {
	public List<Map<String, Object>> select_POLICY_TABLE_SITE_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_OS_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_VENDOR_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_MODEL_SEARCH_REF_VENDOR(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_HOSTNAME_SEARCH(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_POLICY_DEVICE_TYPE_SEARCH(HashMap<String, Object> parameters);
}
