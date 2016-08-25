package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import Common.DTO.AjaxResult;
import Common.DTO.SITE_INFO_DTO;
import Common.ServiceInterface.SITE_INFO_Service_interface;
import Common.ServiceInterface.USER_APPLY_IP_INFO_Service_interface;

@Controller
public class PageActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageActionController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();

	@Autowired
	private SITE_INFO_Service_interface siteInfoService;
	@Autowired
	private USER_APPLY_IP_INFO_Service_interface userApplyIPInfoService;

	//region site info 데이터 조회  -> 조회
	@RequestMapping(value = "/select_site_info", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object select_site_info(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("select_site_info");
		logger.info("select_site_info : " + request.getLocalAddr());
		result = new AjaxResult();
		
		try {									
			List<SITE_INFO_DTO> siteInfoList = siteInfoService.select_SITE_INFO();
			
			result.result = true;
			result.resultValue = siteInfoList;
		}  catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return gson.toJson(result);
	}
	//endregion

	//retion IP 신청 데이터 -> 조회
	@RequestMapping(value = "/select_USER_APPLY_IP_INFO", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void select_USER_APPLY_IP_INFO(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("select_USER_APPLY_IP_INFO");
		HttpSession session = request.getSession(true);
		List<Map<String, Object>> dataList = null;
		JsonArray jsonArray = null;
		int totalCount = 0;

		try {
			String[] columns = { "user_id", "user_name", "user_phone_num", "apply_site_id", "apply_static_ip_type", "apply_static_ipaddr",
			"apply_start_time", "apply_end_time", "apply_description", "apply_time", "settlement_status", "settlement_chief_id", "settlement_chief_name",
			"settlement_description", "issuance_ip_type", "issuance_ipaddr", "issuance_start_time", "issuance_end_time"};
			
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns, 0);

			parameters.put("time_zone", request.getParameter("timezone"));
			parameters.put("user_id", session.getAttribute("user_id").toString());
			parameters.put("user_site_id", session.getAttribute("site_id").toString());			
			dataList = userApplyIPInfoService.select_USER_APPLY_IP_INFO(parameters);

			if (dataList.size() > 0) {
				totalCount = Integer.parseInt(((Map<String, Object>) dataList.get(0)).get("allcount").toString());
			}

			jsonArray = gson.toJsonTree(dataList).getAsJsonArray();
			response.setContentType("Application/json;charset=utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	//endregion
	
	//region IP 신청  -> 추가
	@RequestMapping(value = "/insert_USER_APPLY_IP_INFO", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object insert_USER_APPLY_IP_INFO(HttpServletRequest request) {
		System.out.println("insert_USER_APPLY_IP_INFO");
		HttpSession session = request.getSession(true);
		result = new AjaxResult();

		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {}.getType());

			String m_timezone = parameters.get("time_zone").toString();
			String m_siteid = session.getAttribute("site_id").toString();
			String m_userid = session.getAttribute("user_id").toString();
			String m_username = session.getAttribute("user_name").toString();
			String m_userphonenum = session.getAttribute("user_phone_num").toString();			
			String m_applysiteid = parameters.get("apply_site_id").toString();
			String m_applystaticiptype = parameters.get("apply_static_ip_type").toString();
			String m_applystaticipaddr = parameters.get("apply_static_ipaddr").toString();
			String m_applystaticip_num = parameters.get("apply_static_ip_num").toString();
			String m_applystarttime = parameters.get("apply_start_time").toString();
			String m_applyendtime = parameters.get("apply_end_time").toString();
			String m_applydescription = parameters.get("apply_description").toString();

			System.out.println(m_applystaticip_num);
			System.out.println(Integer.parseInt(m_applystaticip_num));
			parameters.put("time_zone", m_timezone);
			parameters.put("site_id", Integer.parseInt(m_siteid));
			parameters.put("user_id", m_userid);
			parameters.put("user_name", m_username);
			parameters.put("user_phone_num", m_userphonenum);
			parameters.put("apply_site_id", Integer.parseInt(m_applysiteid));
			parameters.put("apply_static_ip_type", m_applystaticiptype);
			parameters.put("apply_static_ipaddr", m_applystaticipaddr);
			parameters.put("apply_static_ip_num", Integer.parseInt(m_applystaticip_num));
			parameters.put("apply_start_time", m_applystarttime);
			parameters.put("apply_end_time", m_applyendtime);
			parameters.put("apply_description", m_applydescription);	
			
			if (userApplyIPInfoService.insert_USER_APPLY_IP_INFO(parameters) > -1)
				result.result = true;
			else
				result.result = false;
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	//endregion
	

	//region IP 신청  -> 삭제
	@RequestMapping(value = "/delete_USER_APPLY_IP_INFO", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object delete_USER_APPLY_IP_INFO(HttpServletRequest request) {
		System.out.println("delete_USER_APPLY_IP_INFO");
		result = new AjaxResult();

		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {}.getType());

			parameters.put("seq", parameters.get("seq").toString());
			
			if (userApplyIPInfoService.delete_USER_APPLY_IP_INFO(parameters) > -1)
				result.result = true;
			else
				result.result = false;
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
	//endregion
}
