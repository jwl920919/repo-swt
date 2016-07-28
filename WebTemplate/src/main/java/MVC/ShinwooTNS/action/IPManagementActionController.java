package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.ArrayList;
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
import Common.Helper.CommonHelper;
import Common.ServiceInterface.IP_MANAGEMENT_Service_Interface;

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

	// 고정 IP 현황 -> Segment 현황 조회
	@RequestMapping(value = "staticIPStatus_Segment_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void staticIPStatus_Segment_Select(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("staticIPStatus_Segment_Select");
		logger.info("staticIPStatus_Segment_Select : " + request.getLocalAddr());
		HttpSession session = request.getSession(true);
		List<Map<String, Object>> dataList = null;
		JsonArray jsonArray = null;
		int totalCount = 0;

		try {
			String[] columns = { "network", "start_ip", "end_ip", "comment" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					0);

			parameters.put("siteid", session.getAttribute("site_id").toString());
			dataList = ipManagementService.select_IP_MANAGEMENT_SEGMENT(parameters);

			if (dataList.size() > 0) {
				totalCount = Integer.parseInt(((Map<String, Object>) dataList.get(0)).get("allcount").toString());
			}

			jsonArray = gson.toJsonTree(dataList).getAsJsonArray();
			response.setContentType("Application/json;charset=utf-8");
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	// 고정 IP 현황 -> Segment별 상세 현황 조회
	@RequestMapping(value = "staticIPStatus_Segment_Detail_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void staticIPStatus_Segment_Detail_Select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("staticIPStatus_Segment_Detail_Select");
		logger.info("staticIPStatus_Segment_Detail_Select : " + request.getLocalAddr());
		HttpSession session = request.getSession(true);
		List<Map<String, Object>> dataList = null;
		JsonArray jsonArray = null;
		int totalCount = 0;

		try {
			String[] columns = { "ipaddr", "ip_type", "macaddr", "duid", "is_conflict", "conflict_types", "status",
					"lease_state", "obj_types", "discover_status", "usage", "host_name", "host_os", "fingerprint",
					// "is_never_ends", "is_never_start",
					"lease_start_time", "lease_end_time", "last_discovered", "user_description", "description" };
			String m_network = request.getParameter("network");
			String m_timezone = request.getParameter("timezone");
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					0);

			String siteID = session.getAttribute("site_id").toString();
			if (!siteID.equals("")) {
				parameters.put("siteid", Integer.parseInt(siteID));
				parameters.put("network", m_network);
				parameters.put("time_zone", m_timezone);

				dataList = ipManagementService.select_IP_MANAGEMENT_SEGMENT_DETAIL(parameters);

				if (dataList.size() > 0) {
					totalCount = Integer.parseInt(((Map<String, Object>) dataList.get(0)).get("allcount").toString());
				}

				jsonArray = gson.toJsonTree(dataList).getAsJsonArray();
				response.setContentType("Application/json;charset=utf-8");
			} else {
				result.result = false;
				result.errorMessage = "Site id is Null in Session!";
			}
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	// 고정 IP 현황 -> Segment별 상세 현황 IPMap 데이터 조회
	@RequestMapping(value = "staticIPStatus_Segment_MapData", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object staticIPStatus_Segment_MapData(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("staticIPStatus_Segment_MapData");
		logger.info("staticIPStatus_Segment_MapData : " + request.getLocalAddr());
		HttpSession session = request.getSession(true);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		result = new AjaxResult();

		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			String m_timezone = parameters.get("timezone").toString();

			String siteID = session.getAttribute("site_id").toString();
			if (!siteID.equals("")) {
				parameters.put("siteid", Integer.parseInt(siteID));

				// region 맵에서 사용할 데이터 쿼리
				String networkStartip = "";
				String networkEndip = "";
				StringBuilder m_DHCP_Range = new StringBuilder();

				// region DHCP Range 데이터 조회
				List<Map<String, Object>> dhcpRange = ipManagementService
						.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAP_DHCPRANGE(parameters);
				if (dhcpRange.size() > 0) {
					for (Map<String, Object> dhcpRangeMap : dhcpRange) {
						networkStartip = dhcpRangeMap.get("start_ip").toString();
						networkEndip = dhcpRangeMap.get("end_ip").toString();

						long startip = CommonHelper.IPv4ToLong(dhcpRangeMap.get("dhcp_start_ip").toString());
						long endip = CommonHelper.IPv4ToLong(dhcpRangeMap.get("dhcp_end_ip").toString());

						for (long i = startip; i <= endip; i++) {
							String rangeIP = CommonHelper.longToIPv4(i);
							m_DHCP_Range.append((m_DHCP_Range.toString().length() > 0) ? "," + rangeIP : rangeIP);
						}
					}
				}
				// endregion

				// region DHCP의 IP대역대 Stat, End영역과 Range 영역 설정을 위한 데이터를 담음
				HashMap<String, Object> ipMap = new HashMap<>();
				ipMap.put("KEY", "DHCP_RANGE");
				ipMap.put("Network_Start", networkStartip);
				ipMap.put("Network_End", networkEndip);
				ipMap.put("KEY", "DHCP_RANGE");
				ipMap.put("DHCP_Range", m_DHCP_Range);
				dataList.add(ipMap);
				// endregion

				System.out.println("time_zone : " + m_timezone);
				parameters.put("time_zone", m_timezone);
				List<Map<String, Object>> allDataList = ipManagementService
						.select_IP_MANAGEMENT_SEGMENT_DETAIL_MAPDATA(parameters);

				result.result = true;
				result.data = dataList;
				result.resultValue = allDataList;
			} else {
				result.result = false;
				result.errorMessage = "Site id is Null in Session!";
			}
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return gson.toJson(result);
	}

	// Lease IP 현황 -> 세그먼트 데이터 조회
	@RequestMapping(value = "dhcp_Network_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object dhcp_Network_Select(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("dhcp_Network_Select");
		logger.info("dhcp_Network_Select : " + request.getLocalAddr());
		HttpSession session = request.getSession(true);
		result = new AjaxResult();

		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			String siteID = session.getAttribute("site_id").toString();
			if (!siteID.equals("")) {
				parameters.put("siteid", Integer.parseInt(siteID));
				parameters.put("searchValue", "");
				parameters.put("orderColumn", "network");
				parameters.put("orderType", "ASC");
				parameters.put("startIndex", 0);
				parameters.put("length", Integer.MAX_VALUE);

				List<String> rangelist = new ArrayList<String>();
				// region DHCP Network 데이터 조회
				List<Map<String, Object>> dhcpRange = ipManagementService.select_IP_MANAGEMENT_SEGMENT(parameters);
				if (dhcpRange.size() > 0) {

					for (Map<String, Object> dhcpRangeMap : dhcpRange) {
						rangelist.add(dhcpRangeMap.get("network").toString());
					}
				}
				// endregion

				result.result = true;
				result.data = dhcpRange;
			} else {
				result.result = false;
				result.errorMessage = "Site id is Null in Session!";
			}
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return gson.toJson(result);
	}

	// Lease IP 현황 -> 데이터 조회
	@RequestMapping(value = "leaseIPStatus_Data_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void leaseIPStatus_Data_Select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("leaseIPStatus_Data_Select");
		logger.info("leaseIPStatus_Data_Select : " + request.getLocalAddr());
		HttpSession session = request.getSession(true);
		List<Map<String, Object>> dataList = null;
		JsonArray jsonArray = null;
		int totalCount = 0;

		try {
			String[] columns = { "ipaddr", "macaddr", "host_name", "host_os", "duid", "status", "lease_state", "obj_types",
			           "discover_status", "usage", "fingerprint", "is_never_ends", "is_never_start", "lease_start_time",
			           "lease_end_time", "last_discovered", "user_description" };
			String m_network = request.getParameter("network");
			String m_timezone = request.getParameter("timezone");
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					0);

			String siteID = session.getAttribute("site_id").toString();
			if (!siteID.equals("")) {
				parameters.put("siteid", Integer.parseInt(siteID));
				parameters.put("network", m_network);
				parameters.put("time_zone", m_timezone);

				dataList = ipManagementService.select_LEASEIP_STATUS_DATA(parameters);

				if (dataList.size() > 0) {
					totalCount = Integer.parseInt(((Map<String, Object>) dataList.get(0)).get("count").toString());
				}

				jsonArray = gson.toJsonTree(dataList).getAsJsonArray();
				response.setContentType("Application/json;charset=utf-8");
			} else {
				result.result = false;
				result.errorMessage = "Site id is Null in Session!";
			}
		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			response.getWriter().println(Common.Helper.DatatableHelper.makeCallback(request, jsonArray, totalCount));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	// 문자열 추가 Help 메서드
	private void StringCompare(String lowerCase, String key, String value, StringBuilder m_string) {
		if (lowerCase.equals(key)) {
			m_string.append((m_string.toString().length() > 0) ? "," + value : value);
		}
	}
}
