package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Common.DTO.AjaxResult;
import Common.DTO.SITE_INFO_DTO;
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.Helper.ErrorLoggingHelper;
import Common.ServiceInterface.ACCESS_POLICY_Service_interface;
import Common.ServiceInterface.SITE_INFO_Service_interface;

@Controller
@RequestMapping(value = "/policyManagement/")
public class PolicyManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = Logger.getLogger(PolicyManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	// redisTemplate를 쓰기위해 Autowired 해준다.
	@Autowired
	StringRedisTemplate redisTemplate;
	@Autowired
	ACCESS_POLICY_Service_interface accessPolicy;
	@Autowired
	SITE_INFO_Service_interface siteInfoService;

	// region getAccessPolicyTableDatas
	@RequestMapping(value = "getAccessPolicyTableDatas", method = RequestMethod.POST)
	public void getAccessPolicyTableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			init();
			String[] columns = { "priority", "site_name", "vendor", "model", "device_type", "os", "hostname", "desc",
					"access_policy_id", "site_id", "os_like", "device_type_like", "hostname_like", "model_like",
					"vendor_like", "is_permit" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			List<Map<String, Object>> accessPolicyDataList = accessPolicy.select_POLICY_TABLE_SITE_SEARCH(parameters);
			JSONArray jsonArray = new JSONArray();
			for (Map<String, Object> accessPolicyData : accessPolicyDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put("access_policy_id", accessPolicyData.get("access_policy_id"));
				jObj.put("priority", accessPolicyData.get("priority"));
				jObj.put("site_name", accessPolicyData.get("site_name"));
				jObj.put("vendor", accessPolicyData.get("vendor"));
				jObj.put("model", accessPolicyData.get("model"));
				jObj.put("os", accessPolicyData.get("os"));
				jObj.put("device_type", accessPolicyData.get("device_type"));
				jObj.put("hostname", accessPolicyData.get("hostname"));
				jObj.put("desc", accessPolicyData.get("desc"));
				jObj.put("is_permit", accessPolicyData.get("is_permit"));
				jObj.put("site_id", accessPolicyData.get("site_id"));
				jObj.put("os_like", accessPolicyData.get("os_like"));
				jObj.put("device_type_like", accessPolicyData.get("device_type_like"));
				jObj.put("hostname_like", accessPolicyData.get("hostname_like"));
				jObj.put("model_like", accessPolicyData.get("model_like"));
				jObj.put("vendor_like", accessPolicyData.get("vendor_like"));
				jObj.put("active", 1);
				jsonArray.add(jObj);
			}

			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(
					Common.Helper.DatatableHelper.makeCallback(request, jsonArray, accessPolicyDataList.size()));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			ErrorLoggingHelper.log(logger, "getAccessPolicyTableDatas", e);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getAccessPolicyTableDatas", e);
		}
	}
	// endregion

	// region getSiteNames
	@RequestMapping(value = "getSiteNames", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getSiteNames(HttpServletRequest request) {
		try {
			init();
			List<SITE_INFO_DTO> sitesInfoList = siteInfoService.select_SITE_INFO();
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			for (SITE_INFO_DTO sid : sitesInfoList) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("site_name", sid.getSite_name());
				map.put("site_id", sid.getSite_id());
				mapList.add(map);
			}
			result.data = mapList;
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getSiteNames", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region getVendor
	@RequestMapping(value = "getVendor", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getVendor(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_VENDOR_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getVendor", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getModel
	@RequestMapping(value = "getModel", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getModel(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_MODEL_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getModel", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getOs
	@RequestMapping(value = "getOs", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getOs(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_OS_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getOs", e);
			result.result = false;
			return gson.toJson(result);
		}

	}

	// endregion

	// region getHostname
	@RequestMapping(value = "getHostname", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getHostname(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_HOSTNAME_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getHostname", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getDeviceType
	@RequestMapping(value = "getDeviceType", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getDeviceType(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_DEVICE_TYPE_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getDeviceType", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region access_policy_modify
	@RequestMapping(value = "accessPolicyModify", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object accessPolicyModify(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("access_policy_id", Integer.parseInt(parameters.get("access_policy_id").toString()));
			parameters.put("priority", Integer.parseInt(parameters.get("priority").toString()));
			parameters.put("site_id", Integer.parseInt(parameters.get("site_id").toString()));
			int cnt = accessPolicy.update_ACCESS_POLICY_INFORM(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "accessPolicyModify", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region access_policy_insert
	@RequestMapping(value = "accessPolicyInsert", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object accessPolicyInsert(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("priority", Integer.parseInt(parameters.get("priority").toString()));
			parameters.put("site_id", Integer.parseInt(parameters.get("site_id").toString()));
			int cnt = accessPolicy.insert_ACCESS_POLICY_INFORM_ONE_RECORD(parameters);
			if (cnt > 0)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "accessPolicyInsert", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region accessPolicyDelete
	@RequestMapping(value = "accessPolicyDelete", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object accessPolicyDelete(HttpServletRequest request) {
		try {
			init();
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());

			ArrayList<Integer> accessPolicyIdList = new ArrayList<Integer>();
			for (HashMap<String, Object> map : jArray) {
				accessPolicyIdList.add(Integer.parseInt(map.get("access_policy_id").toString()));
			}
			HashMap<String, Object> parameter = new HashMap<>();
			parameter.put("list", accessPolicyIdList);
			int cnt = accessPolicy.delete_ACCESS_POLICY_INFORM_RECORDS(parameter);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "accessPolicyDelete", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	private void init() {
		result.data = null;
		result.resultValue = null;
	}
}
