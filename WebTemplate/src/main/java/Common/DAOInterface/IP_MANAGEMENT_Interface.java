package Common.DAOInterface;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IP관리 > 고정IP현황 인터페이스 .
 * </p> 
 **/
public interface IP_MANAGEMENT_Interface {
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT();
}