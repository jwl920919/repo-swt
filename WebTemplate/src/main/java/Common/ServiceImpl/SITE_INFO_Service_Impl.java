package Common.ServiceImpl;

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

}
