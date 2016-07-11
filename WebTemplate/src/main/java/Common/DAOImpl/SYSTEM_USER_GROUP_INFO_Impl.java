package Common.DAOImpl;

import java.util.List;

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

}
