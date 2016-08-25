package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.USER_APPLY_IP_INFO_interface;
import Common.ServiceInterface.USER_APPLY_IP_INFO_Service_interface;

public class USER_APPLY_IP_INFO_Service_Impl implements USER_APPLY_IP_INFO_Service_interface {
	USER_APPLY_IP_INFO_interface userApplyIPInfoDao;

	public void setUSER_APPLY_IP_INFO_dao(USER_APPLY_IP_INFO_interface userApplyIPInfoDao) {
		this.userApplyIPInfoDao = userApplyIPInfoDao;
	}
	
	@Override
	public List<Map<String,Object>> select_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		return userApplyIPInfoDao.select_USER_APPLY_IP_INFO(parameters);
	}	
	
	@Override
	public int insert_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		return userApplyIPInfoDao.insert_USER_APPLY_IP_INFO(parameters);
	}	
	
	@Override
	public int delete_USER_APPLY_IP_INFO(HashMap<String, Object> parameters) {
		return userApplyIPInfoDao.delete_USER_APPLY_IP_INFO(parameters);
	}	
}
