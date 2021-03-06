package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.MANAGEMENT_interface;
import Common.DAOInterface.NETWORK_interface;
import Common.ServiceInterface.MANAGEMENT_Service_interface;
import Common.ServiceInterface.NETWORK_Service_interface;

public class MANAGEMENT_Service_Impl implements MANAGEMENT_Service_interface {
private MANAGEMENT_interface mgmtMenuDao;
	
	public void setMANAGEMENT_dao(MANAGEMENT_interface mgmtMenuDao){
		this.mgmtMenuDao = mgmtMenuDao;
	}

	@Override
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_CUSTOM_IP_GROUP_INFO(parameters);
	}

	@Override
	public int select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(parameters);
	}
	@Override
	public int select_EXIST_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_EXIST_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(parameters);
	}

	@Override
	public int update_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.update_CUSTOM_IP_GROUP_INFO(parameters);
	}

	@Override
	public int insert_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.insert_CUSTOM_IP_GROUP_INFO(parameters);
	}
	@Override
	public int delete_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.delete_CUSTOM_IP_GROUP_INFO(parameters);
	}
	@Override
	public int delete_ALL_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.delete_ALL_CUSTOM_IP_GROUP_INFO(parameters);
	}

	@Override
	public List<Map<String, Object>> select_EXIST_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_EXIST_CUSTOM_IP_GROUP_INFO(parameters);
	}

	@Override
	public int insert_PLURAL_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameter) {
		return mgmtMenuDao.insert_PLURAL_CUSTOM_IP_GROUP_INFO(parameter);
	}

	@Override
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO_FOR_BACKUP(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_CUSTOM_IP_GROUP_INFO_FOR_BACKUP(parameters);
	}

	@Override
	public List<Map<String, Object>> select_CLIENT_DEVICE_INFO() {
		return mgmtMenuDao.select_CLIENT_DEVICE_INFO();
	}

	@Override
	public List<Map<String, Object>> select_VIEW_CLIENT_INFO(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_VIEW_CLIENT_INFO(parameters);
	}

	@Override
	public int select_VIEW_CLIENT_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		return mgmtMenuDao.select_VIEW_CLIENT_INFO_TOTAL_COUNT(parameters);
	}
}
