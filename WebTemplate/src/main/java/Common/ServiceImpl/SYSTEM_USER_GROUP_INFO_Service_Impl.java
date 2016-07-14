package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<Map<String,Object>> select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		return systemUserGroupInfoDao.select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH(parameters);
	}
	
	@Override
	public int update_SYSTEM_USER_GROUP_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		return systemUserGroupInfoDao.update_SYSTEM_USER_GROUP_INFO_ONE_RECORD(parameters);
	}

	@Override
	public int insert_SYSTEM_USER_GROUP_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		return systemUserGroupInfoDao.insert_SYSTEM_USER_GROUP_INFO_ONE_RECORD(parameters);
	}

	@Override
	public int delete_SYSTEM_USER_GROUP_INFO_RECORDS(HashMap<String, Object> parameters) {
		return systemUserGroupInfoDao.delete_SYSTEM_USER_GROUP_INFO_RECORDS(parameters);
	}

	

}
