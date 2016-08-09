package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.ACCESS_POLICY_interface;

public class ACCESS_POLICY_Impl extends SqlSessionDaoSupport implements ACCESS_POLICY_interface {

	@Override
	public List<Map<String, Object>> select_POLICY_TABLE_SITE_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_TABLE_SITE_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_TABLE_SITE_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_TABLE_SITE_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_TABLE_SITE_SEARCHList;
	}

	@Override
	public List<Map<String, Object>> select_POLICY_OS_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_OS_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_OS_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_OS_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_OS_SEARCHList;
	}

	@Override
	public List<Map<String, Object>> select_POLICY_VENDOR_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_VENDOR_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_VENDOR_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_VENDOR_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_VENDOR_SEARCHList;
	}

	@Override
	public List<Map<String, Object>> select_POLICY_MODEL_SEARCH_REF_VENDOR(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_MODEL_SEARCH_REF_VENDORList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_MODEL_SEARCH_REF_VENDORList = getSqlSession().selectList("UI_Query.select_POLICY_MODEL_SEARCH_REF_VENDOR",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_MODEL_SEARCH_REF_VENDORList;
	}

	@Override
	public List<Map<String, Object>> select_POLICY_HOSTNAME_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_HOSTNAME_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_HOSTNAME_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_HOSTNAME_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_HOSTNAME_SEARCHList;
	}

	@Override
	public List<Map<String, Object>> select_POLICY_DEVICE_TYPE_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_DEVICE_TYPE_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_DEVICE_TYPE_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_DEVICE_TYPE_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_DEVICE_TYPE_SEARCHList;
	}

	@Override
	public int update_ACCESS_POLICY_INFORM(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().update("UI_Query.update_ACCESS_POLICY_INFORM", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

}
