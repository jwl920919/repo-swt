package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.ACCESS_POLICY_interface;
import Common.DAOInterface.MANAGEMENT_interface;
import Common.DAOInterface.NETWORK_interface;

public class MANAGEMENT_Impl extends SqlSessionDaoSupport implements MANAGEMENT_interface {

	@Override
	public List<Map<String, Object>> select_CUSTOM_GROUP_INFO(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_CUSTOM_GROUP_INFOList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_CUSTOM_GROUP_INFOList = getSqlSession().selectList("UI_Query.select_CUSTOM_GROUP_INFO",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_CUSTOM_GROUP_INFOList;
	}

	@Override
	public int select_CUSTOM_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().selectOne("UI_Query.select_CUSTOM_GROUP_INFO_TOTAL_COUNT",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}
	

}
