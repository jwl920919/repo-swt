package MVC.ShinwooTNS.action;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.ServiceInterface.IP_MANAGEMENT_Service_Interface;
import scala.Int;

@Controller
@RequestMapping(value = "/ipManagement/")
public class IPManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageActionController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = null;
		
	@Autowired
	private IP_MANAGEMENT_Service_Interface ipManagementService;
	
	@RequestMapping(value = "staticIPStatus_Segment_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void staticIPStatus_Segment_Select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("staticIPStatus_Segment_Select");
		logger.info("staticIPStatus_Segment_Select : " + request.getLocalAddr());
		List<Map<String, Object>> dataList = null;
		JsonArray jsonArray = null;
		int totalCount = 0;
				
		try {			
			String[] columns = { "network", "comment", "utilization", "site" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request,columns,0);
			
			dataList = ipManagementService.select_IP_MANAGEMENT_SEGMENT(parameters);

			if (dataList.size() > 0) {
				totalCount = Integer.parseInt(((Map<String, Object>)dataList.get(0)).get("allcount").toString());
			}
			
			jsonArray = gson.toJsonTree(dataList).getAsJsonArray();
			response.setContentType("Application/json;charset=utf-8");
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			logger.error(e.getMessage());
		}
		finally {
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();			
		}
	}

	@RequestMapping(value = "staticIPStatus_Segment_Detail_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void staticIPStatus_Segment_Detail_Select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("staticIPStatus_Segment_Detail_Select");
		logger.info("staticIPStatus_Segment_Detail_Select : " + request.getLocalAddr());
		List<Map<String, Object>> dataList = null;
		JsonArray jsonArray = null;
		int totalCount = 0;
				
		try {			
			String[] columns = { "ip", "name", "mac", "startus", "type", "client" };			
			int m_Segmentid = Integer.parseInt(request.getParameter("segmentid"));
			
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request,columns,0);
			parameters.put("segmentid", m_Segmentid);
			
			dataList = ipManagementService.select_IP_MANAGEMENT_SEGMENT_DETAIL(parameters);

			if (dataList.size() > 0) {
				totalCount = Integer.parseInt(((Map<String, Object>)dataList.get(0)).get("allcount").toString());
			}
			
			jsonArray = gson.toJsonTree(dataList).getAsJsonArray();
			response.setContentType("Application/json;charset=utf-8");
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			logger.error(e.getMessage());
		}
		finally {
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();			
		}
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "staticIPStatus_Segment_MapData", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object staticIPStatus_Segment_MapData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("staticIPStatus_Segment_MapData");
		logger.info("staticIPStatus_Segment_MapData : " + request.getLocalAddr());
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		try {
			String[] columns = { "ip", "name", "mac", "startus", "type", "client" };
//			System.out.println("segmentid : " + request.getParameter("segmentid"));

			HashMap<String, Object> param = gson.fromJson(request.getReader(),new TypeToken<HashMap<String, Object>>() {}.getType());
			String segmentid = param.get("segmentid").toString();
//			int m_Segmentid = Integer.parseInt(request.getParameter("segmentid"));
			
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request,columns,0);
			//parameters.put("segmentid", m_Segmentid);
			
			//region 맵에서 사용할 데이터 쿼리
			StringBuilder m_activeLease = new StringBuilder();
			StringBuilder m_conflict = new StringBuilder();
			StringBuilder m_exclusion = new StringBuilder();
			StringBuilder m_fixed = new StringBuilder();
			StringBuilder m_gostnotindns = new StringBuilder();
			StringBuilder m_object = new StringBuilder();
			StringBuilder m_pending = new StringBuilder();
			StringBuilder m_range = new StringBuilder();
			StringBuilder m_reservedrange = new StringBuilder();
			StringBuilder m_unmanaged = new StringBuilder();
			StringBuilder m_unused = new StringBuilder();
			StringBuilder m_used = new StringBuilder();
			
			parameters.put("length", Int.MaxValue());
			List<Map<String, Object>> allDataList = ipManagementService.select_IP_MANAGEMENT_SEGMENT_DETAIL(parameters);
			for (Map<String, Object> ipListMap : allDataList) {			
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "activeLease", ipListMap.get("ip").toString(), m_activeLease);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "conflict", ipListMap.get("ip").toString(), m_conflict);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "exclusion", ipListMap.get("ip").toString(), m_exclusion);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "fixed", ipListMap.get("ip").toString(), m_fixed);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "gostnotindns", ipListMap.get("ip").toString(), m_gostnotindns);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "object", ipListMap.get("ip").toString(), m_object);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "pending", ipListMap.get("ip").toString(), m_pending);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "range", ipListMap.get("ip").toString(), m_range);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "reservedrange", ipListMap.get("ip").toString(), m_reservedrange);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "unmanaged", ipListMap.get("ip").toString(), m_unmanaged);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "unused", ipListMap.get("ip").toString(), m_unused);
				StringCompare(ipListMap.get("status").toString().toLowerCase(), "used", ipListMap.get("ip").toString(), m_used);				
			}
			HashMap<String, Object> ipMap  = new HashMap<>();
			ipMap.put("activeLease", m_activeLease);
			ipMap.put("conflict", m_conflict);
			ipMap.put("exclusion", m_exclusion);
			ipMap.put("fixed", m_fixed);
			ipMap.put("gostnotindns", m_gostnotindns);
			ipMap.put("object", m_object);
			ipMap.put("pending", m_pending);
			ipMap.put("range", m_range);
			ipMap.put("reservedrange", m_reservedrange);
			ipMap.put("unmanaged", m_unmanaged);
			ipMap.put("unused", m_unused);
			ipMap.put("used", m_used);
			dataList.add(ipMap);
			//endregion			

			result.result = true;
			result.data = dataList;
		}  catch (Exception e) {
//			result.result = false;
//			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return gson.toJson(result);
	}

	private void StringCompare(String lowerCase, String key, String value, StringBuilder m_string) {
		if (lowerCase.equals(key)) {
			m_string.append((m_string.toString().length() > 0) ? "," + value : value);
		}
	}
}
