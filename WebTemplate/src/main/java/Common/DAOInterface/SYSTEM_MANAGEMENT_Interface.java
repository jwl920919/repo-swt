package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 시스템관리 > 시스템관리 인터페이스 .
 * </p> 
 **/
public interface SYSTEM_MANAGEMENT_Interface {
	public List<Map<String, Object>> select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(HashMap<String, Object> parameters);
}