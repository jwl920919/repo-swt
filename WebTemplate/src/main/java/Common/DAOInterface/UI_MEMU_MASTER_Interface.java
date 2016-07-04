package Common.DAOInterface;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 데시보드 메뉴(UI_MEMU_MASTER) 인터페이스 .
 * </p> 
 **/
public interface UI_MEMU_MASTER_Interface {
	public List<HashMap<String, Object>> select_UI_MENU_MASTER();
}