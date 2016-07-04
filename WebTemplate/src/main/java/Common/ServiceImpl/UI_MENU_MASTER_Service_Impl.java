package Common.ServiceImpl;

import java.util.HashMap;
import java.util.List;

import Common.DAOInterface.UI_MEMU_MASTER_Interface;
import Common.ServiceInterface.UI_MENU_MASTER_Service_Interface;

public class UI_MENU_MASTER_Service_Impl implements UI_MENU_MASTER_Service_Interface {
	private UI_MEMU_MASTER_Interface memuMasterDao;
	
	public void setUI_MEMU_MASTER_dao(UI_MEMU_MASTER_Interface masterDao){
		this.memuMasterDao = masterDao;
	}

	public List<HashMap<String,Object>> select_UI_MENU_MASTER() {
		// TODO Auto-generated method stub
		return memuMasterDao.select_UI_MENU_MASTER();
	}
}