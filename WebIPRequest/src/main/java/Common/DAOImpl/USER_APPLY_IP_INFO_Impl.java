package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import Common.DAOInterface.USER_APPLY_IP_INFO_interface;

public class USER_APPLY_IP_INFO_Impl extends SqlSessionDaoSupport implements USER_APPLY_IP_INFO_interface {

	/**
	 * <p>
	 * IP 승인 요청 > IP 승인 요청 상태 데이터 조회.
	 * </p> 
	 **/
	@Override
	public List<Map<String, Object>> select_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_USER_APPLY_IP_INFO = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_USER_APPLY_IP_INFO : " + getSqlSession());
			select_USER_APPLY_IP_INFO = getSqlSession().selectList("UI_Query.select_USER_APPLY_IP_INFO", parameters);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return select_USER_APPLY_IP_INFO;
	}

	/**
	 * <p>
	 * IP 승인 요청 > IP 승인 요청 상태 데이터 추가.
	 * </p> 
	 **/
	@Override
	public int insert_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().insert("UI_Query.insert_USER_APPLY_IP_INFO", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	/**
	 * <p>
	 * IP 승인 요청 > IP 승인 요청 상태 데이터 삭제.
	 * </p> 
	 **/
	@Override
	public int delete_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().insert("UI_Query.delete_USER_APPLY_IP_INFO", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}
}
