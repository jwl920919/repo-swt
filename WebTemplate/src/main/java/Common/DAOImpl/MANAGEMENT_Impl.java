package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.ACCESS_POLICY_interface;
import Common.DAOInterface.MANAGEMENT_interface;
import Common.DAOInterface.NETWORK_interface;

public class MANAGEMENT_Impl extends SqlSessionDaoSupport implements MANAGEMENT_interface {


	@Override
	public List<Map<String, Object>> select_EXIST_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_EXIST_CUSTOM_IP_GROUP_INFOList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_EXIST_CUSTOM_IP_GROUP_INFOList = getSqlSession().selectList("UI_Query.select_EXIST_CUSTOM_IP_GROUP_INFO", parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_EXIST_CUSTOM_IP_GROUP_INFOList;
	}
	
	@Override
	public List<Map<String, Object>> select_CLIENT_DEVICE_INFO() {
		List<Map<String, Object>> select_CLIENT_DEVICE_INFOList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_CLIENT_DEVICE_INFOList = getSqlSession().selectList("UI_Query.select_CLIENT_DEVICE_INFO");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_CLIENT_DEVICE_INFOList;
	}
	@Override
	public List<Map<String, Object>> select_VIEW_CLIENT_INFO(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_VIEW_CLIENT_INFOList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_VIEW_CLIENT_INFOList = getSqlSession().selectList("UI_Query.select_VIEW_CLIENT_INFO",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_VIEW_CLIENT_INFOList;
	}
	@Override
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFOList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_CUSTOM_IP_GROUP_INFOList = getSqlSession().selectList("UI_Query.select_CUSTOM_IP_GROUP_INFO",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_CUSTOM_IP_GROUP_INFOList;
	}
	
	@Override
	public List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO_FOR_BACKUP(HashMap<String, Object> parameters) {
		List<Map<String, Object>> select_CUSTOM_IP_GROUP_INFO_FOR_BACKUPList = new ArrayList<Map<String, Object>>();
		try {
			System.out.println(getSqlSession());
			select_CUSTOM_IP_GROUP_INFO_FOR_BACKUPList = getSqlSession().selectList("UI_Query.select_CUSTOM_IP_GROUP_INFO_FOR_BACKUP",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_CUSTOM_IP_GROUP_INFO_FOR_BACKUPList;
	}

	@Override
	public int select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().selectOne("UI_Query.select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}
	@Override
	public int select_EXIST_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().selectOne("UI_Query.select_EXIST_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}

	@Override
	public int update_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().update("UI_Query.update_CUSTOM_IP_GROUP_INFO",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}
	
	@Override
	public int delete_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().delete("UI_Query.delete_CUSTOM_IP_GROUP_INFO",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}
	
	@Override
	public int delete_ALL_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().delete("UI_Query.delete_ALL_CUSTOM_IP_GROUP_INFO",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}

	@Override
	public int insert_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().insert("UI_Query.insert_CUSTOM_IP_GROUP_INFO",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}
	@Override
	public int insert_PLURAL_CUSTOM_IP_GROUP_INFO(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().insert("UI_Query.insert_PLURAL_CUSTOM_IP_GROUP_INFO",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}

	@Override
	public int select_VIEW_CLIENT_INFO_TOTAL_COUNT(HashMap<String, Object> parameters) {
		int total = -1;
		try {
			System.out.println(getSqlSession());
			total = getSqlSession().selectOne("UI_Query.select_VIEW_CLIENT_INFO_TOTAL_COUNT",
					parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return total;
	}


	

}
