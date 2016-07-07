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

	public List<Common.DTO.SYSTEM_USER_INFO_DTO> select_SYSTEM_USER_INFO() {
		// TODO Auto-generated method stub
		return userInfoDao.select_SYSTEM_USER_INFO();
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
}