package MVC.ShinwooTNS.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
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
import com.shinwootns.common.utils.ip.IPNetwork;

import Common.DTO.AjaxResult;
import Common.DTO.node.tree.NetworkData;
import Common.DTO.node.tree.NetworkTree;
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
	@Autowired
	NETWORK_Service_interface networkService;

	// region getIpTableDatas
	@RequestMapping(value = "getIpTableDatas", method = RequestMethod.POST)
	public void getIpTableDatas(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			String[] columns = { "network", "group_name", "ip_type", "group_id" };

			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					0);

			parameters.put("ip_type", request.getParameter("ip_type"));
			// parameters.put("site_id",
			// Integer.parseInt(session.getAttribute("site_id").toString()));
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

	// region insertIpCustomGroup
	@RequestMapping(value = "addIpCustomGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addIpCustomGroup(HttpServletRequest request) {
		try {
			init();
			HttpSession session = request.getSession(true);
			session.setAttribute("site_id", "1");
			session.setAttribute("site_master", "t");
			int site_id = Integer.parseInt(session.getAttribute("site_id").toString());
			boolean site_master = session.getAttribute("site_master").equals("t");
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			parameters.put("site_id", site_id);

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

	// region getIpTreeNode
	@RequestMapping(value = "getIpTreeNode", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getIpTreeNode(HttpServletRequest request) {
		try {
			init();
			List<Map<String, Object>> list = networkService.select_SEARCHED_NETWORK_INFO();
			List<Map<String, Object>> existList = managementService.select_EXIST_CUSTOM_IP_GROUP_INFO(null);
			List<NetworkData> ipv4List = new ArrayList<>();
			List<NetworkData> ipv6List = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				NetworkData nd = new NetworkData(new IPNetwork(list.get(i).get("network").toString()));
				if (list.get(i).get("ip_type").equals("IPV4")) {
					ipv4List.add(nd);
				} else
					ipv6List.add(nd);
			}
			for (int i = 0; i < existList.size(); i++) {
				NetworkData nd = new NetworkData(new IPNetwork(existList.get(i).get("key").toString()));
				boolean chk = true;
				if (existList.get(i).get("ip_type").equals("IPV4")) {
					for (NetworkData n : ipv4List) {
						if (n.toString().trim().toUpperCase().equals(nd.toString().trim().toUpperCase())) {
							chk = false;
							break;
						}
					}
					if (chk)
						ipv4List.add(nd);
				} else {
					for (NetworkData n : ipv6List) {
						if (n.toString().trim().toUpperCase().equals(nd.toString().trim().toUpperCase())) {
							chk = false;
							break;
						}
					}
					if (chk)
						ipv6List.add(nd);
				}
			}
			Common.DTO.node.tree.NetworkCompare netCompare = new Common.DTO.node.tree.NetworkCompare();
			Collections.sort(ipv4List, netCompare);
			Collections.sort(ipv6List, netCompare);
			NetworkTree ipv4NetworkTree = new NetworkTree(new NetworkData(new IPNetwork("0.0.0.0/0")));
			NetworkTree ipv6NetworkTree = new NetworkTree(new NetworkData(new IPNetwork("::/0")));
			NetworkTree.Node node;
			for (NetworkData nd : ipv4List) {

				if ((node = ipv4NetworkTree.getRoot().getParentNode(nd.toString())) == null) {
					ipv4NetworkTree.getRoot().addChildren(new NetworkTree.Node(nd));
				} else {
					node.addChildren(new NetworkTree.Node(nd));
				}
			}
			for (NetworkData nd : ipv6List) {

				if ((node = ipv6NetworkTree.getRoot().getParentNode(nd.toString())) == null) {
					ipv6NetworkTree.getRoot().addChildren(new NetworkTree.Node(nd));
				} else {
					node.addChildren(new NetworkTree.Node(nd));
				}
			}

			StringBuffer ipStrBuf = new StringBuffer();
			StringBuffer ipv4StrBuf = new StringBuffer();
			StringBuffer ipv6StrBuf = new StringBuffer();
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("ip_type", "IPV6");
			tmpMap.put("key", "::/0");
			tmpMap.put("group_name", "IPV6");
			existList.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("ip_type", "IPV4");
			tmpMap.put("key", "0.0.0.0/0");
			tmpMap.put("group_name", "IPV4");
			existList.add(tmpMap);
			ipStrBuf.append("[");
			ipv4NetworkTree.getRoot().getNodeJsonInfo(ipv4StrBuf, existList);
			ipStrBuf.append(ipv4StrBuf);
			ipStrBuf.append(",");
			ipv6NetworkTree.getRoot().getNodeJsonInfo(ipv6StrBuf, existList);
			ipStrBuf.append(ipv6StrBuf);
			ipStrBuf.append("]");

			result.resultValue = ipStrBuf.toString();
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "insertIpCustomGroup", e);
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
