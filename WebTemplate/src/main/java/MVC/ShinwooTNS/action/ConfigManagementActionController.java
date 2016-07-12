package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import Common.DTO.AjaxResult;
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.ServiceInterface.SYSTEM_USER_INFO_Service_Interface;
import io.undertow.attribute.RequestMethodAttribute;

@Controller
@RequestMapping(value = "/configManagement/")
public class ConfigManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(ConfigManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();
	@Autowired
	private SYSTEM_USER_INFO_Service_Interface userInfoService;
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
			HashMap<String, Object> map = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {
			}.getType());
			int group_id = Integer.parseInt(map.get("group_id").toString()),
					site_id = Integer.parseInt(map.get("site_id").toString());
			String time_zone = map.get("time_zone").toString();
			map.remove("group_id");
			map.remove("site_id");
			map.put("group_id", group_id);
			map.put("site_id", site_id);

			int cnt = userInfoService.update_SYSTEM_USER_INFO_ONE_RECORD(map);
			if (cnt > 0)
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
			HashMap<String, Object> map = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {
			}.getType());
			SYSTEM_USER_INFO_DTO systemUserInfo = userInfoService.select_SYSTEM_USER_INFO_ONE_SEARCH(map);
			if (systemUserInfo == null) {
				if (map.get("user_id").toString().equals(""))
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
			HashMap<String, Object> map = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {
			}.getType());
			int group_id = Integer.parseInt(map.get("group_id").toString()),
					site_id = Integer.parseInt(map.get("site_id").toString());
			map.remove("group_id");
			map.remove("site_id");
			map.put("group_id", group_id);
			map.put("site_id", site_id);
			int cnt = userInfoService.insert_SYSTEM_USER_INFO_ONE_RECORD(map);
			if (cnt > 0)
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
			HashMap<String, Object> map = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {
			}.getType());
			
			for(Iterator iter = map.keySet().iterator();iter.hasNext();) {
				String key = iter.next().toString();
				System.out.println(key + " :: " + map.get(key));
			}
			// HashMap<String, Object> typeCastMap = new HashMap<>();
			// typeCastMap.put("user_pw", map.get("user_pw"));
			// typeCastMap.put("mobile_num", map.get("mobile_num"));
			// typeCastMap.put("user_id", map.get("user_id"));
			// typeCastMap.put("group_id",
			// Integer.parseInt(map.get("group_id").toString()));
			// typeCastMap.put("user_name", map.get("user_name"));
			// typeCastMap.put("site_id",
			// Integer.parseInt(map.get("site_id").toString()));
			// typeCastMap.put("position_name", map.get("position_name"));
			// typeCastMap.put("dept_name", map.get("dept_name"));
			// typeCastMap.put("phone_num", map.get("phone_num"));
			// typeCastMap.put("email", map.get("email"));
			// typeCastMap.put("time_zone", map.get("time_zone"));
			//
			// int cnt =
			// userInfoService.insert_SYSTEM_USER_INFO_ONE_RECORD(typeCastMap);
			// if (cnt > 0)
			// result.result = true;
			// else
			// result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	// endregion

}
