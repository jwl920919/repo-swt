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
	
	

}
