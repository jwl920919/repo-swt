package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import Common.DTO.SITE_INFO_DTO;
import Common.DTO.node.IpNetworkTree;
import Common.DTO.node.tree.NetworkData;
import Common.DTO.node.tree.NetworkTree;
import Common.Helper.ErrorLoggingHelper;
import Common.ServiceInterface.MANAGEMENT_Service_interface;
import Common.ServiceInterface.NETWORK_Service_interface;
import Common.ServiceInterface.SITE_INFO_Service_interface;
import Common.ip.IPAddr;
import Common.ip.IPNetwork;
import Common.ip.ipv6.IPv6Address;

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
	@Autowired
	NETWORK_Service_interface networkService;
	@Autowired
	private SITE_INFO_Service_interface siteInfoService;

	// region getIpTableDatas
	@RequestMapping(value = "getIpTableDatas", method = RequestMethod.POST)
	public void getIpTableDatas(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String[] columns = { "network", "group_name", "ip_type", "group_id" };

			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					0);

			int site_id = Integer.parseInt(session.getAttribute("site_id").toString());
			String site_master = session.getAttribute("site_master").toString();
			parameters.put("site_id", site_id);
			parameters.put("site_master", site_master);

			parameters.put("ip_type", request.getParameter("ip_type"));
			JSONArray jsonArray = new JSONArray();
			List<Map<String, Object>> ipDataList = managementService.select_CUSTOM_IP_GROUP_INFO(parameters);
			for (Map<String, Object> ipData : ipDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put("site_id", ipData.get("site_id"));
				jObj.put("network", ipData.get("key"));
				jObj.put("ip_type", ipData.get("ip_type"));
				jObj.put("group_name", ipData.get("group_name"));
				jObj.put("group_id", ipData.get("group_id"));
				jsonArray.add(jObj);
			}
			String callback = Common.Helper.DatatableHelper.makeCallback(request, jsonArray,
					managementService.select_CUSTOM_IP_GROUP_INFO_TOTAL_COUNT(parameters));
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
	// endregion

	// region modifyIpCustomGroup
	@RequestMapping(value = "modifyIpCustomGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object modifyIpCustomGroup(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("group_id", Integer.parseInt(parameters.get("group_id").toString()));
			int cnt = managementService.update_CUSTOM_IP_GROUP_INFO(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "modifyIpCustomGroup", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region addIpCustomGroup
	@RequestMapping(value = "addIpCustomGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addIpCustomGroup(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("site_id", Integer.parseInt(parameters.get("site_id").toString()));
			IPNetwork ipNetwork = new IPNetwork(parameters.get("network").toString());
			if (ipNetwork.getStartIP().isIPv4()) {
				parameters.put("network",
						String.format("%s/%s", ipNetwork.getStartIP().toString(), ipNetwork.getPrefixLength()));
			} else {
				BigInteger nwip = ipNetwork.getStartIP().getNumberToBigInteger().add( new BigInteger("0") );
				IPv6Address nwIp = IPv6Address.fromBigInteger(nwip);
				parameters.put("network",String.format("%s/%s", nwIp.toString(), ipNetwork.getPrefixLength()));
			}
			int cnt = managementService.insert_CUSTOM_IP_GROUP_INFO(parameters);
			if (cnt > 0)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "addIpCustomGroup", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region addIpCustomGroup
	@RequestMapping(value = "deleteIpCustomGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteIpCustomGroup(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("group_id", Integer.parseInt(parameters.get("group_id").toString()));
			int cnt = managementService.delete_CUSTOM_IP_GROUP_INFO(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "deleteIpCustomGroup", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getIpTreeNode
	@RequestMapping(value = "getIpTreeNode", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getIpTreeNode(HttpServletRequest request) {
		try {
			init();
			HttpSession session = request.getSession(true);
			int site_id = Integer.parseInt(session.getAttribute("site_id").toString());
			String site_master = session.getAttribute("site_master").toString();
			HashMap<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("site_id", site_id);
			parameter.put("site_master", site_master);
			IpNetworkTree ipNetworkTree = new IpNetworkTree(managementService, networkService, parameter);
			result.resultValue = ipNetworkTree.getIPNodeJsonStr4jstree();
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getIpTreeNode", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region getSiteNames
	@RequestMapping(value = "getSiteNames", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getSiteNames(HttpServletRequest request) {
		try {
			init();
			HttpSession session = request.getSession(true);
			if (session.getAttribute("site_master").toString().equals("t")) {
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
			} else {
				result.result = false;
			}
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getSiteNames", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region getSiteName
	@RequestMapping(value = "getSiteName", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getSiteName(HttpServletRequest request) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			SITE_INFO_DTO sid = siteInfoService
					.select_SITE_INFO_ONE_SEARCH(Integer.parseInt(parameters.get("site_id").toString()));
			HashMap<String, Object> map = new HashMap<>();
			map.put("site_name", sid.getSite_name());
			result.resultValue = map;
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getSiteName", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region migrationAction
	@RequestMapping(value = "migrationAction", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object migrationAction(HttpServletRequest request) {
		try {
			init();
			HttpSession session = request.getSession(true);
			String site_master = session.getAttribute("site_master").toString();
			int site_id = Integer.parseInt(session.getAttribute("site_id").toString());
			List<Map<String, Object>> parameters = gson.fromJson(request.getReader(),
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
			for (Map<String, Object> params : parameters) {
				if (!params.get("site_id").equals("")) {
					if (site_master.equals("t"))
						params.put("site_id", Integer.parseInt(params.get("site_id").toString()));
					else
						params.put("site_id", -1);
				} else {
					params.put("site_id", -1);
				}
				params.put("ip_type", params.get("ip_type").toString().toUpperCase());
			}

			HashMap<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("site_id", site_id);
			parameter.put("list", parameters);
			if (managementService.insert_PLURAL_CUSTOM_IP_GROUP_INFO(parameter) > 0)
				result.result = true;
			else
				result.result = false;

			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "migrationAction", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region backupData
	@RequestMapping(value = "backupData", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public @ResponseBody Object getBackupData(HttpServletRequest request) {
		try {
			init();
			HttpSession session = request.getSession(true);
			int site_id = Integer.parseInt(session.getAttribute("site_id").toString());
			HashMap<String, Object> parameter = new HashMap<String, Object>();
			String site_master = session.getAttribute("site_master").toString();
			parameter.put("site_id", site_id);
			parameter.put("site_master", site_master);
			List<Map<String, Object>> backupList = managementService.select_CUSTOM_IP_GROUP_INFO_FOR_BACKUP(parameter);
			StringBuffer sb = new StringBuffer();
			sb.append("network,group_name,ip_type,site_id\n");
			for (Map<String, Object> bData : backupList) {
				sb.append(bData.get("key"));
				sb.append(',');
				sb.append(bData.get("group_name"));
				sb.append(',');
				sb.append(bData.get("ip_type"));
				sb.append(',');
				sb.append(bData.get("site_id"));
				sb.append('\n');
			}
			return sb.toString();
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "backupData", e);
			return "File Creation Error";
		}
	}
	// endregion
	
	// region deleteAllCustomIpGroupInfo
	@RequestMapping(value = "deleteAllCustomIpGroupInfo", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteAllCustomIpGroupInfo(HttpServletRequest request) {
		try {
			init();
			HttpSession session = request.getSession(true);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			int site_id = Integer.parseInt(session.getAttribute("site_id").toString());
			String site_master = session.getAttribute("site_master").toString();
			parameters.put("site_id", site_id);
			parameters.put("site_master", site_master);
			if(managementService.delete_ALL_CUSTOM_IP_GROUP_INFO(parameters)>-1)
				result.result = true;
			else result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "deleteAllCustomIpGroupInfo", e);
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
