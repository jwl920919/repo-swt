package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.NETWORK_interface;
import Common.ServiceInterface.NETWORK_Service_interface;

public class NETWORK_Service_Impl implements NETWORK_Service_interface {
private NETWORK_interface networkMenuDao;
	
	public void setNETWORK_dao(NETWORK_interface networkMenuDao){
		this.networkMenuDao = networkMenuDao;
	}

	@Override
	public List<Map<String, Object>> select_SEARCHED_NETWORK_INFO(HashMap<String, Object> parameters) {
		return networkMenuDao.select_SEARCHED_NETWORK_INFO(parameters);
	}
}
