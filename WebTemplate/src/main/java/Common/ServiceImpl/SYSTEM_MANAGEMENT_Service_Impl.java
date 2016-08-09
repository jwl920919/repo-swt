package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.SYSTEM_MANAGEMENT_Interface;
import Common.ServiceInterface.SYSTEM_MANAGEMENT_Service_Interface;;

/**
 * <p>
 * 시스템관리 > 시스템관리 서비스 Implement.
 * </p> 
 **/
public class SYSTEM_MANAGEMENT_Service_Impl implements SYSTEM_MANAGEMENT_Service_Interface {
	private SYSTEM_MANAGEMENT_Interface systemManagementDao;
	
	public void setSYSTEM_MANAGEMENT_dao(SYSTEM_MANAGEMENT_Interface systemManagementDao){
		this.systemManagementDao = systemManagementDao;
	}

	/**
	 * <p>
	 * IP관리 > Black List 기능설정 데이터 조회.
	 * </p> 
	 **/
	public List<Map<String,Object>> select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return systemManagementDao.select_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(parameters);
	}


	/**
	 * <p>
	 * IP관리 > Black List 기능설정 데이터 추가, 수정, 삭제.
	 * </p> 
	 **/
	@Override
	public int insert_update_delete_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(HashMap<String, Object> parameters) {
		return systemManagementDao.insert_update_delete_SYSTEM_MANAGEMENT_BLACKLIST_SETTING_DATA(parameters);
	}
}