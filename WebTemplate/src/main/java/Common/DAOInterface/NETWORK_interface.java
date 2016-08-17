package Common.DAOInterface;

import java.util.List;
import java.util.Map;

public interface NETWORK_interface {
	public List<Map<String, Object>> select_SEARCHED_NETWORK_INFO();
	public int select_SEARCHED_NETWORK_INFO_TOTAL_COUNT();
}
