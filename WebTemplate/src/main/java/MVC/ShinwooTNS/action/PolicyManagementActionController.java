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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import Common.ServiceInterface.ACCESS_POLICY_Service_interface;
import Common.ServiceInterface.SITE_INFO_Service_interface;

@Controller
@RequestMapping(value = "/policyManagement/")
public class PolicyManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PolicyManagementActionController.class);
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
	public void getSystemUserManagementDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		logger.info("getAccessPolicyTableDatas : " + request.getLocalAddr());
		System.out.println("getAccessPolicyTableDatas Controller");
		try {
			init();
			String[] columns = { "access_policy_id", "site_name", "vendor", "model", "os", "device_type", "hostname",
					"desc", "is_permit", "site_id" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			List<Map<String, Object>> accessPolicyDataList = accessPolicy.select_POLICY_TABLE_SITE_SEARCH(parameters);
			System.out.println(accessPolicyDataList);
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
				jObj.put("active", 1);
				jsonArray.add(jObj);
			}

			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(
					Common.Helper.DatatableHelper.makeCallback(request, jsonArray, accessPolicyDataList.size()));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	// endregion

	// region getSiteNames
	@RequestMapping(value = "getSiteNames", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getSiteNames(HttpServletRequest request) {
		logger.info("getSiteNames : " + request.getLocalAddr());
		try {
			init();
			List<SITE_INFO_DTO> sitesInfoList = siteInfoService.select_SITE_INFO();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (SITE_INFO_DTO sid : sitesInfoList) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("site_name", sid.getSite_name());
				map.put("site_id", sid.getSite_id());
				mapList.add(map);
			}
			result.data = mapList;
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region getVendor
	@RequestMapping(value = "getVendor", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getVendor(HttpServletRequest request) {
		logger.info("getVendor : " + request.getLocalAddr());
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_VENDOR_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getModel
	@RequestMapping(value = "getModel", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getModel(HttpServletRequest request) {
		logger.info("getModel : " + request.getLocalAddr());
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_MODEL_SEARCH_REF_VENDOR(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getOs
	@RequestMapping(value = "getOs", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getOs(HttpServletRequest request) {
		logger.info("getOs : " + request.getLocalAddr());
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_OS_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}

	// endregion

	// region getHostname
	@RequestMapping(value = "getHostname", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getHostname(HttpServletRequest request) {
		logger.info("getHostname : " + request.getLocalAddr());
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_HOSTNAME_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getDeviceType
	@RequestMapping(value = "getDeviceType", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getDeviceType(HttpServletRequest request) {
		logger.info("getDeviceType : " + request.getLocalAddr());
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			result.data = accessPolicy.select_POLICY_DEVICE_TYPE_SEARCH(parameters);
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion
	
	// region access_policy_modify
	@RequestMapping(value = "access_policy_modify", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object access_policy_modify(HttpServletRequest request) {
		logger.info("access_policy_modify : " + request.getLocalAddr());
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("access_policy_id", Integer.parseInt(parameters.get("access_policy_id").toString()));
			parameters.put("priority", Integer.parseInt(parameters.get("priority").toString()));
			parameters.put("site_id", Integer.parseInt(parameters.get("site_id").toString()));
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion
	
	@RequestMapping(value = "sample", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public @ResponseBody Object getInfobloxdatas(HttpServletRequest request, HttpSession session) {
		logger.info("sample : " + request.getLocalAddr());
		init();
		try {
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}

	private void init() {
		result.data = null;
		result.resultValue = null;
	}
}
