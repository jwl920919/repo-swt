package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IP관리 > 고정IP현황 서비스 인터페이스 .
 * </p> 
 **/
public interface SYSTEM_MANAGEMENT_Service_Interface {
	public List<Map<String, Object>> select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(HashMap<String, Object> parameters);
}
