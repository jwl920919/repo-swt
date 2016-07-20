package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.AUTH_MENU_interface;
import Common.ServiceInterface.AUTH_MENU_Service_interface;

public class AUTH_MENU_Service_Impl implements AUTH_MENU_Service_interface {
private AUTH_MENU_interface authMenuDao;
	
	public void setAUTH_MENU_dao(AUTH_MENU_interface authMenuDao){
		this.authMenuDao = authMenuDao;
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_MAKE_SEARCH_FOR_SITE(HashMap<String, Object> parameters) {
		return authMenuDao.select_AUTH_MENU_MAKE_SEARCH_FOR_SITE(parameters);
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_GROUP_NAMES(HashMap<String, Object> parameters) {
		return authMenuDao.select_AUTH_MENU_GROUP_NAMES(parameters);
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_M_MASTER() {
		return authMenuDao.select_AUTH_MENU_M_MASTER();
	}

	@Override
	public List<Map<String, Object>> select_AUTH_MENU_M_SUB() {
		return authMenuDao.select_AUTH_MENU_M_SUB();
	}

}
