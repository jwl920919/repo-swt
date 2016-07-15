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
			System.out.println(getSqlSession());
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
			System.out.println(getSqlSession());
			select_IP_MANAGEMENT_SEGMENT_DETAILList = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_SEGMENT_DETAIL", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_SEGMENT_DETAILList;
	}
	
	/**
	 * <p>
	 * IP관리 > 고정IP현황 IP세그먼트별 ip map 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA(int segmentid) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATAList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATAList = getSqlSession().selectList("UI_Query.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA", segmentid);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATAList;
	}
}
