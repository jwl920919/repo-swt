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
	public List<Map<String, Object>> select_POLICY_MODEL_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_MODEL_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_MODEL_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_MODEL_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_MODEL_SEARCHList;
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
	
	@Override
	public int insert_ACCESS_POLICY_INFORM_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			System.out.println("insert parameters :: "+parameters);
			cnt = getSqlSession().insert("UI_Query.insert_ACCESS_POLICY_INFORM_ONE_RECORD", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	@Override
	public int delete_ACCESS_POLICY_INFORM_RECORDS(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			// size zero로 for each를 이용한 sql 생성에서 발생하는 에러 방지
			if (((ArrayList<String>) parameters.get("list")).size() > 0)
				cnt = getSqlSession().delete("UI_Query.delete_ACCESS_POLICY_INFORM_RECORDS", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

}
