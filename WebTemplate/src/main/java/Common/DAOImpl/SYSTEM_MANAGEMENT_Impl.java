package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.SYSTEM_MANAGEMENT_Interface;

/**
 * <p>
 * IP관리.
 * </p> 
 **/
public class SYSTEM_MANAGEMENT_Impl extends SqlSessionDaoSupport implements SYSTEM_MANAGEMENT_Interface{
	/**
	 * <p>
	 * 시스템 관리 > Black List 기능설정 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String, Object>> select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA : " + getSqlSession());
			select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA = getSqlSession().selectList("UI_Query.select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA;
	}

}
