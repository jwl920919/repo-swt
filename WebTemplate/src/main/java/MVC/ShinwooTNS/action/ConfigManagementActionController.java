package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import Common.DTO.AjaxResult;
import Common.DTO.SITE_INFO_DTO;
import Common.DTO.SYSTEM_USER_GROUP_DTO;
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.ServiceInterface.SYSTEM_USER_INFO_Service_Interface;
import Common.ServiceInterface.SITE_INFO_Service_interface;
import Common.ServiceInterface.SYSTEM_USER_GROUP_INFO_Service_interface;

@Controller
@RequestMapping(value = "/configManagement/")
public class ConfigManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(ConfigManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	// region systemUserManagement

	// region Autowired
	@Autowired
	private SYSTEM_USER_INFO_Service_Interface userInfoService;
	// endregion

	// region getSystemUserManagementDatatableDatas

	@RequestMapping(value = "getSystemUserManagementDatatableDatas", method = RequestMethod.POST)
	public void getSystemUserManagementDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getSystemUserManagementDatatableDatas : " + request.getLocalAddr());
		System.out.println("getSystemUserManagementDatatableDatas Controller");
		try {
			String[] columns = { "user_id", "user_name" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);

			List<SYSTEM_USER_INFO_DTO> userDataList = userInfoService
					.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH(parameters);

			JSONArray jsonArray = new JSONArray();
			for (SYSTEM_USER_INFO_DTO suid : userDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put("user_id", suid.getUser_id());
				jObj.put("user_name", suid.getUser_name());
				jObj.put("active", 1);
				jsonArray.add(jObj);
			}
			int totalCount = userInfoService.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(parameters);

			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	// endregion

	// region getUserInfo
	@RequestMapping(value = "getUserInfo", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getUserInfo(HttpServletRequest request) {
		logger.info("getUserInfo : " + request.getLocalAddr());
		System.out.println("getUserInfo Controller");
		try {
			SYSTEM_USER_INFO_DTO systemUserInfo = userInfoService.select_SYSTEM_USER_INFO_ONE_SEARCH(
					gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {
					}.getType()));
			Map<String, Object> mData = new HashMap<String, Object>();
			mData.put("user_name", systemUserInfo.getUser_name());
			mData.put("group_id", systemUserInfo.getGroup_id());
			mData.put("site_id", systemUserInfo.getSite_id());
			mData.put("dept_name", systemUserInfo.getDept_name());
			mData.put("position_name", systemUserInfo.getPosition_name());
			mData.put("email", systemUserInfo.getEmail());
			mData.put("phone_num", systemUserInfo.getPhone_num());
			mData.put("mobile_num", systemUserInfo.getMobile_num());

			result.result = true;
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(mData);
			result.data = list;
			return gson.toJson(result);
		} catch (IOException e1) {
			e1.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region updateUserInfo
	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object updateUserInfo(HttpServletRequest request) {
		logger.info("updateUserInfo : " + request.getLocalAddr());
		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			int group_id = Integer.parseInt(parameters.get("group_id").toString()),
					site_id = Integer.parseInt(parameters.get("site_id").toString());
			String time_zone = parameters.get("time_zone").toString();
			parameters.remove("group_id");
			parameters.remove("site_id");
			parameters.put("group_id", group_id);
			parameters.put("site_id", site_id);

			int cnt = userInfoService.update_SYSTEM_USER_INFO_ONE_RECORD(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	// endregion

	// region checkId
	@RequestMapping(value = "checkId", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object checkId(HttpServletRequest request) {
		logger.info("checkId : " + request.getLocalAddr());
		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			SYSTEM_USER_INFO_DTO systemUserInfo = userInfoService.select_SYSTEM_USER_INFO_ONE_SEARCH(parameters);
			if (systemUserInfo == null) {
				if (parameters.get("user_id").toString().equals(""))
					result.result = false;
				else
					result.result = true;
			} else {
				result.result = false;
			}
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	// endregion

	// region addUser
	@RequestMapping(value = "addUser", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addUser(HttpServletRequest request) {
		logger.info("addUser : " + request.getLocalAddr());
		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			int group_id = Integer.parseInt(parameters.get("group_id").toString()),
					site_id = Integer.parseInt(parameters.get("site_id").toString());
			parameters.remove("group_id");
			parameters.remove("site_id");
			parameters.put("group_id", group_id);
			parameters.put("site_id", site_id);
			int cnt = userInfoService.insert_SYSTEM_USER_INFO_ONE_RECORD(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	// endregion

	// region deleteUser
	@RequestMapping(value = "deleteUsers", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteUsers(HttpServletRequest request) {
		logger.info("deleteUsers : " + request.getLocalAddr());
		try {
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());

			ArrayList<String> userIdList = new ArrayList<String>();
			for (HashMap<String, Object> map : jArray) {
				userIdList.add(map.get("user_id").toString());
			}
			HashMap<String, Object> parameter = new HashMap<>();
			parameter.put("list", userIdList);
			int cnt = userInfoService.delete_SYSTEM_USER_INFO_RECORDS(parameter);
			System.out.println(cnt);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	// endregion

	// endregion

	// region systemGroupManagement

	// region Autowired
	@Autowired
	private SITE_INFO_Service_interface siteInfoService;
	@Autowired
	private SYSTEM_USER_GROUP_INFO_Service_interface groupInfoService;
	// endregion

	// region getPlaceOfBusinessDatatableDatas
	@RequestMapping(value = "getPlaceOfBusinessDatatableDatas", method = RequestMethod.POST)
	public void getPlaceOfBusinessDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getPlaceOfBusinessDatatableDatas : " + request.getLocalAddr());
		System.out.println("getPlaceOfBusinessDatatableDatas Controller");
		try {
			String[] columns = { "site_name", "site_code", "description" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);

			List<SITE_INFO_DTO> siteDataList = siteInfoService.select_SITE_INFO_CONDITIONAL_SEARCH(parameters);

			JSONArray jsonArray = new JSONArray();
			for (SITE_INFO_DTO sid : siteDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put(columns[0], sid.getSite_name());
				jObj.put(columns[1], sid.getSite_code());
				jObj.put(columns[2], sid.getDescription());
				jObj.put("active", 1);
				jsonArray.add(jObj);
			}
			int totalCount = siteDataList.size();

			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	// endregion

	// region getUserGroupDatatableDatas
	@RequestMapping(value = "getUserGroupDatatableDatas", method = RequestMethod.POST)
	public void getUserGroupDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getUserGroupDatatableDatas : " + request.getLocalAddr());
		System.out.println("getUserGroupDatatableDatas Controller");
		try {
			String[] columns = { "site_name", "group_name", "group_desc" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			List<Map<String, Object>> siteDataList = groupInfoService
					.select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH(parameters);
			for(Map<String,Object> siteData : siteDataList) {
				siteData.put("active", 1);
			}
			JsonArray jsonArray = gson.toJsonTree(siteDataList).getAsJsonArray();

			int totalCount = siteDataList.size();

			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	// endregion

	// endregion
}
