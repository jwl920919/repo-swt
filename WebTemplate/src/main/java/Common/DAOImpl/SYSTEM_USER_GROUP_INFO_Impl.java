package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.SYSTEM_USER_GROUP_INFO_interface;
import Common.DTO.SYSTEM_USER_GROUP_DTO;

public class SYSTEM_USER_GROUP_INFO_Impl extends SqlSessionDaoSupport implements SYSTEM_USER_GROUP_INFO_interface {

	@Override
	public List<SYSTEM_USER_GROUP_DTO> select_SYSTEM_USER_GROUP_INFO() {
		List<Common.DTO.SYSTEM_USER_GROUP_DTO> select_SYSTEM_USER_GROUP_INFOList = null;
		try {
			System.out.println(getSqlSession());
			select_SYSTEM_USER_GROUP_INFOList = getSqlSession().selectList("UI_Query.select_SYSTEM_USER_GROUP_INFO");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SYSTEM_USER_GROUP_INFOList;
	}
	
	@Override
	public List<Map<String, Object>> select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> select_SYSTEM_USER_GROUP_INFOList = null;
		try {
			System.out.println(getSqlSession());
			select_SYSTEM_USER_GROUP_INFOList = getSqlSession().selectList("UI_Query.select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SYSTEM_USER_GROUP_INFOList;
	}
	

	@Override
	public int update_SYSTEM_USER_GROUP_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().update("UI_Query.update_SYSTEM_USER_GROUP_INFO_ONE_RECORD", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	@Override
	public int insert_SYSTEM_USER_GROUP_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().insert("UI_Query.insert_SYSTEM_USER_GROUP_INFO_ONE_RECORD", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	@Override
	public int delete_SYSTEM_USER_GROUP_INFO_RECORDS(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			// size zero로 for each를 이용한 sql 생성에서 발생하는 에러 방지
			if (((ArrayList<String>) parameters.get("list")).size() > 0)
				cnt = getSqlSession().delete("UI_Query.delete_SYSTEM_USER_GROUP_INFO_RECORDS", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	

}
