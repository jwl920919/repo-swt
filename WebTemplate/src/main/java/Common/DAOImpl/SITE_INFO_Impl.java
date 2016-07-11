package Common.DAOImpl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.SITE_INFO_interface;
import Common.DTO.SITE_INFO_DTO;

public class SITE_INFO_Impl extends SqlSessionDaoSupport implements SITE_INFO_interface {

	@Override
	public List<SITE_INFO_DTO> select_SITE_INFO() {
		List<Common.DTO.SITE_INFO_DTO> select_SYSTEM_USER_INFOList = null;
		try {
			System.out.println(getSqlSession());
			select_SYSTEM_USER_INFOList = getSqlSession().selectList("UI_Query.select_SITE_INFO");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SYSTEM_USER_INFOList;
	}

}
