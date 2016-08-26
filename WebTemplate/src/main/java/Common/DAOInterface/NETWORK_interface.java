package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NETWORK_interface {
	public List<Map<String, Object>> select_SEARCHED_NETWORK_INFO(HashMap<String, Object> parameters);
	public Map<String, Object> select_VIEW_IP_STATUS(HashMap<String, Object> parameters);
	public int select_SEARCHED_NETWORK_INFO_TOTAL_COUNT(HashMap<String, Object> parameters);
}
