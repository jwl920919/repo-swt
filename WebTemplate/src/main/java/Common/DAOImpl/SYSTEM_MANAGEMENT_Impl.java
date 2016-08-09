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

	/**
	 * <p>
	 * 시스템 관리 > Black List 기능설정 추가, 수정, 삭제.
	 * </p> 
	 **/
	@Override
	public int insert_update_delete_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println("insert_update_delete_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA : " + getSqlSession());
			if (parameters.get("functionclass").equals("add")) {
				System.out.println("insert_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA functionclass : " + parameters.get("functionclass"));
				
				cnt = getSqlSession().insert("UI_Query.insert_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA", parameters);				
			}
			else if (parameters.get("functionclass").equals("modify")) {
				System.out.println("update_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA functionclass : " + parameters.get("functionclass"));
				
				cnt = getSqlSession().update("UI_Query.update_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA", parameters);	
			}
			else if (parameters.get("functionclass").equals("delete")) {
				System.out.println("delete_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA functionclass : " + parameters.get("functionclass"));
				
				cnt = getSqlSession().delete("UI_Query.delete_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA", parameters);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}
}
