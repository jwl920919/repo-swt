package Common.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import Common.DAOInterface.SITE_INFO_interface;
import Common.DTO.SITE_INFO_DTO;
import scala.annotation.meta.param;

public class SITE_INFO_Impl extends SqlSessionDaoSupport implements SITE_INFO_interface {

	@Override
	public List<SITE_INFO_DTO> select_SITE_INFO() {
		List<Common.DTO.SITE_INFO_DTO> select_SITE_INFOList = null;
		try {
			System.out.println(getSqlSession());
			select_SITE_INFOList = getSqlSession().selectList("UI_Query.select_SITE_INFO");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SITE_INFOList;
	}
	@Override
	public List<SITE_INFO_DTO> select_SITE_INFO_CONDITIONAL_SEARCH(HashMap<String, Object> parameters) {
		List<Common.DTO.SITE_INFO_DTO> select_SITE_INFOList = null;
		try {
			System.out.println(getSqlSession());
			select_SITE_INFOList = getSqlSession().selectList("UI_Query.select_SITE_INFO_CONDITIONAL_SEARCH",parameters);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return select_SITE_INFOList;
	}
	

	@Override
	public int update_SITE_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().update("UI_Query.update_SITE_INFO_ONE_RECORD", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	@Override
	public int insert_SITE_INFO_ONE_RECORD(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			cnt = getSqlSession().insert("UI_Query.insert_SITE_INFO_ONE_RECORD", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}

	@Override
	public int delete_SITE_INFO_RECORDS(HashMap<String, Object> parameters) {
		int cnt = -1;
		try {
			System.out.println(getSqlSession());
			// size zero로 for each를 이용한 sql 생성에서 발생하는 에러 방지
			if (((ArrayList<String>) parameters.get("list")).size() > 0)
				cnt = getSqlSession().delete("UI_Query.delete_SITE_INFO_RECORDS", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return cnt;
	}
	@Override
	public SITE_INFO_DTO select_SITE_INFO_ONE_SEARCH(int site_id) {
		SITE_INFO_DTO siteInfoDto = null;
		try {
			System.out.println(getSqlSession());
			siteInfoDto = getSqlSession().selectOne("UI_Query.select_SITE_INFO_ONE_SEARCH",
					site_id);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return siteInfoDto;
	}

}
