package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.IP_MANAGEMENT_Interface;

/**
 * <p>
 * IP관리.
 * </p> 
 **/
public class IP_MANAGEMENT_Impl extends SqlSessionDaoSupport implements IP_MANAGEMENT_Interface{
	
	/**
	 * <p>
	 * IP관리 > 고정IP현황 IP세그먼트 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENTList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_IP_MANAGEMENT_SEGMENT : " + getSqlSession());
			select_IP_MANAGEMENT_SEGMENTList = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_SEGMENT", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_SEGMENTList;
	}
	
	/**
	 * <p>
	 * IP관리 > 고정IP현황 IP세그먼트별 상세 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAILList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_IP_MANAGEMENT_SEGMENT_DETAIL : " + getSqlSession());
			select_IP_MANAGEMENT_SEGMENT_DETAILList = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_SEGMENT_DETAIL", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_SEGMENT_DETAILList;
	}
	
	/**
	 * <p>
	 * IP관리 > 고정IP현황 IP세그먼트별 ip map DHCP Range 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGEList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE : " + getSqlSession());
			select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGEList = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGEList;
	}
	
	/**
	 * <p>
	 * IP관리 > 고정IP현황 IP세그먼트별 ip map 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATAList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA : " + getSqlSession());
			select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATAList = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATAList;
	}
	
	/**
	 * <p>
	 * IP관리 > 리스 IP현황 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_LEASEIP_STATUS_DATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_LEASEIP_STATUS_DATAList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_LEASEIP_STATUS_DATA : " + getSqlSession());
			select_LEASEIP_STATUS_DATAList = getSqlSession().selectList("UI_Query.select_LEASEIP_STATUS_DATA", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_LEASEIP_STATUS_DATAList;
	}

	/**
	 * <p>
	 * IP관리 > IP 요청/승인 현황 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_IP_MANAGEMENT_CERTIFY_STATUS_DATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_IP_MANAGEMENT_CERTIFY_STATUS_DATA = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_IP_MANAGEMENT_CERTIFY_STATUS_DATA : " + getSqlSession());
			select_IP_MANAGEMENT_CERTIFY_STATUS_DATA = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_CERTIFY_STATUS_DATA", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_CERTIFY_STATUS_DATA;
	}

}
