package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.ACCESS_POLICY_interface;

public class ACCESS_POLICY_Impl extends SqlSessionDaoSupport implements ACCESS_POLICY_interface {

	@Override
	public List<Map<String, Object>> select_POLICY_TABLE_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_POLICY_TABLE_CONDITIONAL_SEARCHList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_POLICY_TABLE_CONDITIONAL_SEARCHList = getSqlSession().selectList("UI_Query.select_POLICY_TABLE_CONDITIONAL_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_POLICY_TABLE_CONDITIONAL_SEARCHList;
	}

}
