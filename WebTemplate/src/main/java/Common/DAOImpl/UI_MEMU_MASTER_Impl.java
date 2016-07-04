package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.UI_MEMU_MASTER_Interface;


public class UI_MEMU_MASTER_Impl extends SqlSessionDaoSupport implements UI_MEMU_MASTER_Interface{

	public List<HashMap<String, Object>> select_UI_MENU_MASTER() {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> select_UI_MENU_MASTERList = new ArrayList<HashMap<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_UI_MENU_MASTERList = getSqlSession().selectList("UI_Query.select_UI_MENU_MASTER");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_UI_MENU_MASTERList;
	}
}
