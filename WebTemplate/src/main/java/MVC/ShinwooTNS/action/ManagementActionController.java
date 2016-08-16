package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import Common.Helper.ErrorLoggingHelper;
import Common.ServiceInterface.ACCESS_POLICY_Service_interface;
import Common.ServiceInterface.MANAGEMENT_Service_interface;
import Common.ServiceInterface.NETWORK_Service_interface;

@Controller
@RequestMapping(value = "/management/")
public class ManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = Logger.getLogger(ManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();
	
	@Autowired
	StringRedisTemplate redisTemplate;
	@Autowired
	MANAGEMENT_Service_interface managementService;
	
	@RequestMapping(value = "getIpTableDatas", method = RequestMethod.POST)
	public void getAccessPolicyTableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			String[] columns = { "network", "group_name" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
				0);
			
			parameters.put("ip_type", request.getParameter("ip_type"));
//			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			List<Map<String, Object>> ipDataList = managementService.select_CUSTOM_GROUP_INFO(parameters);
			JSONArray jsonArray = new JSONArray();
			for (Map<String, Object> ipData : ipDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put("site_id", ipData.get("site_id"));
				jObj.put("network", ipData.get("key"));
//				jObj.put("ip_type", ipData.get("ip_type"));
				jObj.put("group_name", ipData.get("group_name"));
				jObj.put("group_id", ipData.get("group_id"));
				jsonArray.add(jObj);
			}
			String callback = Common.Helper.DatatableHelper.makeCallback(request, jsonArray, managementService.select_CUSTOM_GROUP_INFO_TOTAL_COUNT(parameters));
			response.setContentType("Application/json;charset=utf-8");
			response.getWriter().println(callback);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			ErrorLoggingHelper.log(logger, "getIpTableDatas", e);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getIpTableDatas", e);
		}
	}
	
	private void init() {
		result.data = null;
		result.resultValue = null;
	}
}
