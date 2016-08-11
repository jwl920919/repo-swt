package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.DAOInterface.ACCESS_POLICY_interface;
import Common.ServiceInterface.ACCESS_POLICY_Service_interface;

public class ACCESS_POLICY_Service_Impl implements ACCESS_POLICY_Service_interface {
private ACCESS_POLICY_interface accessPolicyDao;
	
	public void setACCESS_POLICY_dao(ACCESS_POLICY_interface accessPolicyDao){
		this.accessPolicyDao = accessPolicyDao;
	}

	@Override
	public List<Map<String, Object>> select_POLICY_TABLE_SITE_SEARCH(HashMap<String, Object> parameters) {
		return accessPolicyDao.select_POLICY_TABLE_SITE_SEARCH(parameters);
	}

	@Override
	public List<Map<String, Object>> select_POLICY_OS_SEARCH(HashMap<String, Object> parameters) {
		return accessPolicyDao.select_POLICY_OS_SEARCH(parameters);
	}

	@Override
	public List<Map<String, Object>> select_POLICY_VENDOR_SEARCH(HashMap<String, Object> parameters) {
		return accessPolicyDao.select_POLICY_VENDOR_SEARCH(parameters);
	}

	@Override
	public List<Map<String, Object>> select_POLICY_MODEL_SEARCH(HashMap<String, Object> parameters) {
		return accessPolicyDao.select_POLICY_MODEL_SEARCH(parameters);
	}

	@Override
	public List<Map<String, Object>> select_POLICY_HOSTNAME_SEARCH(HashMap<String, Object> parameters) {
		return accessPolicyDao.select_POLICY_HOSTNAME_SEARCH(parameters);
	}

	@Override
	public List<Map<String, Object>> select_POLICY_DEVICE_TYPE_SEARCH(HashMap<String, Object> parameters) {
		return accessPolicyDao.select_POLICY_DEVICE_TYPE_SEARCH(parameters);
	}

	@Override
	public int update_ACCESS_POLICY_INFORM(HashMap<String, Object> parameters) {
		return accessPolicyDao.update_ACCESS_POLICY_INFORM(parameters);
	}
	
	@Override
	public int insert_ACCESS_POLICY_INFORM_ONE_RECORD(HashMap<String, Object> parameters) {
		return accessPolicyDao.insert_ACCESS_POLICY_INFORM_ONE_RECORD(parameters);
	}
	
	@Override
	public int delete_ACCESS_POLICY_INFORM_RECORDS(HashMap<String, Object> parameters) {
		return accessPolicyDao.delete_ACCESS_POLICY_INFORM_RECORDS(parameters);
	}

}
