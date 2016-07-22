package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import Common.DAOInterface.SITE_INFO_interface;
import Common.DTO.SITE_INFO_DTO;
import Common.ServiceInterface.SITE_INFO_Service_interface;

public class SITE_INFO_Service_Impl implements SITE_INFO_Service_interface {
	SITE_INFO_interface siteInfoDao;

	public void setSITE_INFO_dao(SITE_INFO_interface siteInfoDao) {
		this.siteInfoDao = siteInfoDao;
	}
	@Override
	public List<SITE_INFO_DTO> select_SITE_INFO() {
		return siteInfoDao.select_SITE_INFO();
	}
	
	@Override
	public List<SITE_INFO_DTO> select_SITE_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		return siteInfoDao.select_SITE_INFO_CONDITIONAL_SEARCH(parameters);
	}
	@Override
	public int update_SITE_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		return siteInfoDao.update_SITE_INFO_ONE_RECORD(parameters);
	}
	@Override
	public int insert_SITE_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		return siteInfoDao.insert_SITE_INFO_ONE_RECORD(parameters);
	}
	@Override
	public int delete_SITE_INFO_RECORDS(HashMap<String, Object> parameters) {
		return siteInfoDao.delete_SITE_INFO_RECORDS(parameters);
	}
	@Override
	public SITE_INFO_DTO select_SITE_INFO_ONE_SEARCH(HashMap<String, Object> parameters) {
		return siteInfoDao.select_SITE_INFO_ONE_SEARCH(parameters);
	}

}
