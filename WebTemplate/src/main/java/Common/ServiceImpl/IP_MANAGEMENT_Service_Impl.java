package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.IP_MANAGEMENT_Interface;
import Common.ServiceInterface.IP_MANAGEMENT_Service_Interface;

/**
 * <p>
 * IP관리 > 고정IP현황 서비스 Implement.
 * </p> 
 **/
public class IP_MANAGEMENT_Service_Impl implements IP_MANAGEMENT_Service_Interface {
	private IP_MANAGEMENT_Interface ipManagementDao;
	
	public void setIP_MANAGEMENT_dao(IP_MANAGEMENT_Interface ipManagementDao){
		this.ipManagementDao = ipManagementDao;
	}

	/**
	 * <p>
	 * IP관리 > 고정IP현황 서비스  IP세그먼트 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_IP_MANAGEMENT_SEGMENT(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.select_IP_MANAGEMENT_SEGMENT(parameters);
	}

	/**
	 * <p>
	 * IP관리 > 고정IP현황 서비스  IP세그먼트별 상세 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.select_IP_MANAGEMENT_SEGMENT_DETAIL(parameters);
	}
}