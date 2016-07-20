package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AUTH_MENU_Service_interface {
	public List<Map<String, Object>> select_AUTH_MENU_MAKE_SEARCH_FOR_SITE(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_AUTH_MENU_GROUP_NAMES(HashMap<String, Object> parameters);
	public List<Map<String, Object>> select_AUTH_MENU_M_MASTER();
	public List<Map<String, Object>> select_AUTH_MENU_M_SUB();
}
