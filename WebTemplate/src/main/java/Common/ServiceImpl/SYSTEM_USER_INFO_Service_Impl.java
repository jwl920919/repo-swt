package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import Common.DAOInterface.SYSTEM_USER_INFO_Interface;
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.ServiceInterface.SYSTEM_USER_INFO_Service_Interface;

public class SYSTEM_USER_INFO_Service_Impl implements SYSTEM_USER_INFO_Service_Interface {
	private SYSTEM_USER_INFO_Interface userInfoDao;
	
	public void setSYSTEM_USER_INFO_dao(SYSTEM_USER_INFO_Interface userDao){
		this.userInfoDao = userDao;
	}

	@Override
	public List<SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return userInfoDao.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH(parameters);
	}

	@Override
	public int select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(HashMap<String, Object> parameters) {
		return userInfoDao.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(parameters);
	}

	@Override
	public SYSTEM_USER_INFO_DTO select_SYSTEM_USER_INFO_ONE_SEARCH(HashMap<String, Object> parameters) {
		return userInfoDao.select_SYSTEM_USER_INFO_ONE_SEARCH(parameters);
	}

	@Override
	public int update_SYSTEM_USER_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		return userInfoDao.update_SYSTEM_USER_INFO_ONE_RECORD(parameters);
	}

	@Override
	public int insert_SYSTEM_USER_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		return userInfoDao.insert_SYSTEM_USER_INFO_ONE_RECORD(parameters);
	}

	@Override
	public int delete_SYSTEM_USER_INFO_RECORDS(HashMap<String, Object> parameters) {
		return userInfoDao.delete_SYSTEM_USER_INFO_RECORDS(parameters);
	}
}