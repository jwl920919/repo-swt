package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.ACCESS_POLICY_interface;
import Common.DAOInterface.NETWORK_interface;

public class NETWORK_Impl extends SqlSessionDaoSupport implements NETWORK_interface {

	public List<Map<String, Object>> select_SEARCHED_NETWORK_INFO() {
		List<Map<String, Object>> select_SEARCHED_NETWORK_INFOList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_SEARCHED_NETWORK_INFOList = getSqlSession().selectList("UI_Query.select_SEARCHED_NETWORK_INFO");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SEARCHED_NETWORK_INFOList;
	}

	@Override
	public int select_SEARCHED_NETWORK_INFO_TOTAL_COUNT() {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().selectOne("UI_Query.select_SEARCHED_NETWORK_INFO_TOTAL_COUNT");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}

}
