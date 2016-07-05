package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.SYSTEM_USER_INFO_Interface;


public class SYSTEM_USER_INFO_Impl extends SqlSessionDaoSupport implements SYSTEM_USER_INFO_Interface {

	public List<Common.DTO.SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFO() {
		// TODO Auto-generated method stub
		List<Common.DTO.SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFOList = new ArrayList<>();
		try {
			System.out.println(getSqlSession());
			select_SYSTEM_USER_INFOList = getSqlSession().selectList("UI_Query.select_SYSTEM_USER_INFO");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SYSTEM_USER_INFOList;
	}

}
