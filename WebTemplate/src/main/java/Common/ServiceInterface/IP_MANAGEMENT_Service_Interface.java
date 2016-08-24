package Common.ServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IP관리 > 고정IP현황 서비스 인터페이스 .
 * </p> 
 **/
public interface IP_MANAGEMENT_Service_Interface {
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT(HashMap<String, Object> parameters);
	
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL(HashMap<String, Object> parameters);

	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE(HashMap<String, Object> parameters);
	
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA(HashMap<String, Object> parameters);
	
	public List<Map<String, Object>> select_LEASEIP_STATUS_DATA(HashMap<String, Object> parameters);

	public List<Map<String, Object>> select_IP_MANAGEMENT_CERTIFY_STATUS_DATA(HashMap<String, Object> parameters);
	
	public int update_USER_APPLY_IP_INFO(HashMap<String, Object> parameters);
}
