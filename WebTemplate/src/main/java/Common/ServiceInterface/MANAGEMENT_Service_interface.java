package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MANAGEMENT_Service_interface {
	public List<Map<String, Object>> select_CUSTOM_GROUP_INFO(HashMap<String, Object> parameters);
}
