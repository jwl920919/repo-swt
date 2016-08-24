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

	/**
	 * <p>
	 * IP관리 > 고정IP현황 IP세그먼트별 ip map DHCP Range 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE(parameters);
	}

	/**
	 * <p>
	 * IP관리 > 고정IP현황 서비스  IP세그먼트별 ip map 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA(parameters);
	}

	/**
	 * <p>
	 * IP관리 > 리스 IP현황 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_LEASEIP_STATUS_DATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.select_LEASEIP_STATUS_DATA(parameters);
	}

	/**
	 * <p>
	 * IP관리 > IP 요청/승인 현황 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_IP_MANAGEMENT_CERTIFY_STATUS_DATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.select_IP_MANAGEMENT_CERTIFY_STATUS_DATA(parameters);
	}

	/**
	 * <p>
	 * IP관리 > IP 요청/승인 결제정보 수정.
	 * </p> 
	 **/
	public int update_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return ipManagementDao.update_USER_APPLY_IP_INFO(parameters);
	}
}