package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import Common.DAOInterface.SYSTEM_USER_INFO_Interface;
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
}