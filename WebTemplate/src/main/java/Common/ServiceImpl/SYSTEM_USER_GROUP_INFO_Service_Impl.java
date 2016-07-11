package Common.ServiceImpl;

import java.util.List;

import Common.DAOInterface.SYSTEM_USER_GROUP_INFO_interface;
import Common.DTO.SYSTEM_USER_GROUP_DTO;
import Common.ServiceInterface.SYSTEM_USER_GROUP_INFO_Service_interface;

public class SYSTEM_USER_GROUP_INFO_Service_Impl implements SYSTEM_USER_GROUP_INFO_Service_interface {
	SYSTEM_USER_GROUP_INFO_interface systemUserGroupInfoDao;

	public void setSYSTEM_USER_GROUP_INFO_dao(SYSTEM_USER_GROUP_INFO_interface systemUserGroupInfoDao) {
		this.systemUserGroupInfoDao = systemUserGroupInfoDao;
	}

	@Override
	public List<SYSTEM_USER_GROUP_DTO> select_SYSTEM_USER_GROUP_INFO() {
		return systemUserGroupInfoDao.select_SYSTEM_USER_GROUP_INFO();
	}

}
