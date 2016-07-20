package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.AUTH_MENU_interface;

public class AUTH_MENU_Impl extends SqlSessionDaoSupport implements AUTH_MENU_interface {

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_MAKE_SEARCH_FOR_SITE(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_AUTH_MENU_MAKE_SEARCH_FOR_SITEList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_AUTH_MENU_MAKE_SEARCH_FOR_SITEList = getSqlSession().selectList("UI_Query.select_AUTH_MENU_MAKE_SEARCH_FOR_SITE",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_AUTH_MENU_MAKE_SEARCH_FOR_SITEList;
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_GROUP_NAMES(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_AUTH_MENU_GROUP_NAMESList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_AUTH_MENU_GROUP_NAMESList = getSqlSession().selectList("UI_Query.select_AUTH_MENU_GROUP_NAMES",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_AUTH_MENU_GROUP_NAMESList;
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_M_MASTER() {
		List<Map<String, Object>> select_AUTH_MENU_M_MASTERList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_AUTH_MENU_M_MASTERList = getSqlSession().selectList("UI_Query.select_AUTH_MENU_M_MASTER");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_AUTH_MENU_M_MASTERList;
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_M_SUB() {
		List<Map<String, Object>> select_AUTH_MENU_M_SUBList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_AUTH_MENU_M_SUBList = getSqlSession().selectList("UI_Query.select_AUTH_MENU_M_SUB");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_AUTH_MENU_M_SUBList;
	}

}
