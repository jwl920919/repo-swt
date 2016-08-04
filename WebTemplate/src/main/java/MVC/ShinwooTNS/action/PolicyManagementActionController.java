package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.HashMap;
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

import Common.DTO.AjaxResult;
import Common.ServiceInterface.ACCESS_POLICY_Service_interface;

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
	
	@RequestMapping(value = "getAccessPolicyTableDatas", method = RequestMethod.POST)
	public void getSystemUserManagementDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		logger.info("getAccessPolicyTableDatas : " + request.getLocalAddr());
		System.out.println("getAccessPolicyTableDatas Controller");
		try {
			init();
			String[] columns = { "access_policy_id","site_name", "vendor","model","os","device_type","hostname","desc","is_permit","site_id" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			List<Map<String,Object>> accessPolicyDataList = accessPolicy.select_POLICY_TABLE_CONDITIONAL_SEARCH(parameters);
			System.out.println(accessPolicyDataList);
			JSONArray jsonArray = new JSONArray();
			for (Map<String,Object> accessPolicyData : accessPolicyDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put("access_policy_id", accessPolicyData.get("access_policy_id"));
				jObj.put("site_name", accessPolicyData.get("site_name"));
				jObj.put("vendor", accessPolicyData.get("vendor"));
				jObj.put("model", accessPolicyData.get("model"));
				jObj.put("os", accessPolicyData.get("os"));
				jObj.put("device_type", accessPolicyData.get("device_type"));
				jObj.put("hostname", accessPolicyData.get("hostname"));
				jObj.put("desc", accessPolicyData.get("desc"));
				jObj.put("is_permit", accessPolicyData.get("is_permit"));
				jObj.put("site_id", accessPolicyData.get("site_id"));
				jObj.put("active", 1);
				jsonArray.add(jObj);
			}

			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, accessPolicyDataList.size()));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
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
