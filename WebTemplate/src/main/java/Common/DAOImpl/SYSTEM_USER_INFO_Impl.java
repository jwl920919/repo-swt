package Common.DAOImpl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.SYSTEM_USER_INFO_Interface;
import Common.DTO.SYSTEM_USER_INFO_DTO;


public class SYSTEM_USER_INFO_Impl extends SqlSessionDaoSupport implements SYSTEM_USER_INFO_Interface {
	
	
	@Override
	public List<SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		List<Common.DTO.SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFOList = null;
		try {
			System.out.println(getSqlSession());
			select_SYSTEM_USER_INFOList = getSqlSession().selectList("UI_Query.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SYSTEM_USER_INFOList;
	}

	@Override
	public int select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(
			HashMap<String, Object> parameters) {
		int total =0;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().selectOne("UI_Query.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}

	@Override
	public Common.DTO.SYSTEM_USER_INFO_DTO select_SYSTEM_USER_INFO_ONE_SEARCH(HashMap<String, Object> parameters) {
		Common.DTO.SYSTEM_USER_INFO_DTO systemUserInfo = null;
		try {
			System.out.println(getSqlSession());
			systemUserInfo = getSqlSession().selectOne("UI_Query.select_SYSTEM_USER_INFO_ONE_SEARCH",parameters);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return systemUserInfo;
	}

	@Override
	public int update_SYSTEM_USER_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().update("UI_Query.update_SYSTEM_USER_INFO_ONE_RECORD",parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}
	
	@Override
	public int insert_SYSTEM_USER_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().insert("UI_Query.insert_SYSTEM_USER_INFO_ONE_RECORD",parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	@Override
	public int delete_SYSTEM_USER_INFO_RECORDS(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().delete("UI_Query.delete_SYSTEM_USER_INFO_RECORDS",parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}
	
	

}
