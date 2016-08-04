package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ACCESS_POLICY_interface {
	public List<Map<String, Object>> select_POLICY_TABLE_CONDITIONAL_SEARCH(HashMap<String, Object> parameters);
}
