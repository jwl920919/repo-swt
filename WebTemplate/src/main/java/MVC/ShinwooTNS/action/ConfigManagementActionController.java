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
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.Helper.LanguageHelper;
import Common.ServiceInterface.AUTH_MENU_Service_interface;
import Common.ServiceInterface.SITE_INFO_Service_interface;
import Common.ServiceInterface.SYSTEM_USER_GROUP_INFO_Service_interface;
import Common.ServiceInterface.SYSTEM_USER_INFO_Service_Interface;

@Controller
@RequestMapping(value = "/configManagement/")
public class ConfigManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(ConfigManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	// region Autowired
	@Autowired
	private SYSTEM_USER_INFO_Service_Interface userInfoService;
	@Autowired
	private SITE_INFO_Service_interface siteInfoService;
	@Autowired
	private SYSTEM_USER_GROUP_INFO_Service_interface groupInfoService;
	@Autowired
	private AUTH_MENU_Service_interface authMenuService;
	// endregion

	/***************************************************** systemUserManagement *****************************************************/
	// region systemUserManagement

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
			result.result = false;
			return gson.toJson(result);
		}
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
			result.result = false;
			return gson.toJson(result);
		}

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
			result.result = false;
			return gson.toJson(result);
		}
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
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// endregion

	/***************************************************** systemGroupManagement *****************************************************/
	// region systemGroupManagement

	// region getPlaceOfBusinessDatatableDatas
	@RequestMapping(value = "getPlaceOfBusinessDatatableDatas", method = RequestMethod.POST)
	public void getPlaceOfBusinessDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getPlaceOfBusinessDatatableDatas : " + request.getLocalAddr());
		System.out.println("getPlaceOfBusinessDatatableDatas Controller");
		try {
			String[] columns = { "site_name", "site_code", "description", "site_id" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);

			List<SITE_INFO_DTO> siteDataList = siteInfoService.select_SITE_INFO_CONDITIONAL_SEARCH(parameters);

			JSONArray jsonArray = new JSONArray();
			for (SITE_INFO_DTO sid : siteDataList) {
				JSONObject jObj = new JSONObject();
				jObj.put(columns[0], sid.getSite_name());
				jObj.put(columns[1], sid.getSite_code());
				jObj.put(columns[2], sid.getDescription());
				jObj.put(columns[3], sid.getSite_id());
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
			String[] columns = { "site_name", "group_name", "group_desc", "group_id" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			List<Map<String, Object>> siteDataList = groupInfoService
					.select_SYSTEM_USER_GROUP_INFO_CONDITIONAL_SEARCH(parameters);
			for (Map<String, Object> siteData : siteDataList) {
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

	// region addSite
	@RequestMapping(value = "addSite", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addSite(HttpServletRequest request) {
		logger.info("addSite : " + request.getLocalAddr());
		try {
			// site_name, site_code, description
			int cnt = siteInfoService.insert_SITE_INFO_ONE_RECORD(
					gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {
					}.getType()));
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region addGroup
	@RequestMapping(value = "addGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addGroup(HttpServletRequest request) {
		logger.info("addGroup : " + request.getLocalAddr());
		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			int site_id = Integer.parseInt(parameters.get("site_id").toString());
			parameters.remove("site_id");
			parameters.put("site_id", site_id);
			int cnt = groupInfoService.insert_SYSTEM_USER_GROUP_INFO_ONE_RECORD(parameters);

			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region updateSite
	@RequestMapping(value = "updateSite", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object updateSite(HttpServletRequest request) {
		logger.info("updateSite : " + request.getLocalAddr());
		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			int site_id = Integer.parseInt(parameters.get("site_id").toString());
			parameters.remove("site_id");
			parameters.put("site_id", site_id);

			int cnt = siteInfoService.update_SITE_INFO_ONE_RECORD(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region updateGroup
	@RequestMapping(value = "updateGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object updateGroup(HttpServletRequest request) {
		logger.info("updateGroup : " + request.getLocalAddr());
		try {
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			int group_id = Integer.parseInt(parameters.get("group_id").toString());
			parameters.remove("group_id");
			parameters.put("group_id", group_id);
			int site_id = Integer.parseInt(parameters.get("site_id").toString());
			parameters.remove("site_id");
			parameters.put("site_id", site_id);

			int cnt = groupInfoService.update_SYSTEM_USER_GROUP_INFO_ONE_RECORD(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region deleteSites
	@RequestMapping(value = "deleteSites", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteSites(HttpServletRequest request) {
		logger.info("deleteSites : " + request.getLocalAddr());
		try {
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());

			ArrayList<Integer> userIdList = new ArrayList<Integer>();
			for (HashMap<String, Object> map : jArray) {
				userIdList.add(Integer.parseInt(map.get("site_id").toString()));
			}
			HashMap<String, Object> parameter = new HashMap<>();
			parameter.put("list", userIdList);
			int cnt = siteInfoService.delete_SITE_INFO_RECORDS(parameter);
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

	// region deleteGroups
	@RequestMapping(value = "deleteGroups", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteGroups(HttpServletRequest request) {
		logger.info("deleteGroups : " + request.getLocalAddr());
		try {
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());

			ArrayList<Integer> groupIdList = new ArrayList<Integer>();
			for (HashMap<String, Object> map : jArray) {
				groupIdList.add(Integer.parseInt(map.get("group_id").toString()));
			}
			HashMap<String, Object> parameter = new HashMap<>();
			parameter.put("list", groupIdList);
			int cnt = groupInfoService.delete_SYSTEM_USER_GROUP_INFO_RECORDS(parameter);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// endregion

	/***************************************************** systemMenuAuthorityManagement *****************************************************/
	// region systemMenuAuthorityManagement
	
	// region getAuthorityTable
	@RequestMapping(value = "getAuthorityTable", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public void getAuthorityTable(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getAuthorityTable : " + request.getLocalAddr());
		System.out.println("getAuthorityTable Controller");
		try {
			HttpSession session = request.getSession(true);
			HashMap<String, Object> parameters = new HashMap<>();
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			List<Map<String, Object>> tableInfo = authMenuService.select_AUTH_MENU_MAKE_SEARCH_FOR_SITE(parameters);// body_info
			List<Map<String, Object>> groupNames = authMenuService.select_AUTH_MENU_GROUP_NAMES(parameters);// header
			List<Map<String, Object>> menuMaster = authMenuService.select_AUTH_MENU_M_MASTER();// body-master
			List<Map<String, Object>> menuSub = authMenuService.select_AUTH_MENU_M_SUB();// body-sub
			if (groupNames.size() > 0) {
				StringBuffer sb = new StringBuffer();
				sb.append("<div class='save-box'><input class='save-button btn btn-primary' type='button' value='저장'></div>");
				sb.append("<table id='auth_table' >");
				sb.append("<thead id='auth_table_head'>");
				sb.append("<th colspan=2></th>");

				Iterator<Map<String, Object>> groupNamesIterator = groupNames.iterator();
				while (groupNamesIterator.hasNext()) {
					Map<String, Object> groupName = groupNamesIterator.next();
					sb.append("<th>");
					sb.append(groupName.get("group_name").toString());
					sb.append("</th>");
				}
				int count = 0;
				sb.append("</thead>");
				sb.append("<tbody id='auth_table_body'>");
				for (Map<String, Object> mm : menuMaster) {
					count++;
					List<Object> subRemoveList = new ArrayList<>();
					StringBuffer tmp = new StringBuffer();

					for (Map<String, Object> ms : menuSub) {
						if (mm.get("master_cd").toString().equals(ms.get("master_cd").toString())) {
							List<Object> tiRemoveList = new ArrayList<>();
							tmp.append("<tr>");
							tmp.append("<td style='width:240px;background-color: #efefef;' name=sub_menu");
							tmp.append(count);
							tmp.append(">");
							tmp.append("<input type='hidden' name='sub_cd' value='");
							tmp.append(ms.get("sub_cd"));
							tmp.append("' />");
							tmp.append(LanguageHelper.GetLanguage(ms.get("subname_key").toString()));
							tmp.append("</td>");
							for (Map<String, Object> ti : tableInfo) {
								if (ti.get("master_cd").toString().equals(mm.get("master_cd").toString())
										&& ti.get("sub_cd").toString().equals(ms.get("sub_cd").toString())) {
									tmp.append("<td style='width:240px' name=group_grant");
									tmp.append(count);
									tmp.append(">");
									tmp.append("<input type='hidden' name='auth_menu_id' value='");
									tmp.append(ti.get("auth_menu_id"));
									tmp.append("' />");
									String authState = ti.get("auth_state").toString();
									tmp.append("<input type='hidden' name='auth_state' value='");
									tmp.append(authState);
									tmp.append("' />");
									tmp.append("<input type='hidden' name='site_id' value='");
									tmp.append(ti.get("site_id"));
									tmp.append("' />");
									tmp.append("<input type='hidden' name='group_id' value='");
									tmp.append(ti.get("group_id"));
									tmp.append("' />");
									tmp.append(authState.equals("W")?"읽기/쓰기":authState.equals("R")?"읽기":"권한없음");
									tmp.append("</td>");
									tiRemoveList.add(ti);
								}
							}

							for (Object ti : tiRemoveList) {
								tableInfo.remove(ti);
							}
							subRemoveList.add(ms);
							tmp.append("</tr>");
							//test
						}
					}
					sb.append("<td style='width:180px;background-color: #efefef;' name=main_menu");
					sb.append(count);
					sb.append(" rowspan='");
					sb.append(subRemoveList.size() + 1);// 자기자신 row
					sb.append("'>");
					sb.append("<input type='hidden' name='master_cd' value='");
					sb.append(mm.get("master_cd"));
					sb.append("' />");
					sb.append(LanguageHelper.GetLanguage(mm.get("master_namekey").toString()));
					sb.append("</td>");
					sb.append(tmp.toString());
					for (Object s : subRemoveList) {
						tableInfo.remove(s);
					}
				}
				sb.append("</tbody>");
				sb.append("</table>");
				sb.append("<div class='save-box'><input class='save-button btn btn-primary' type='button' value='저장'></div>");
				sb.append("<script src='resources/js/configMangement/systemMenuAuthorityManagementTable.js'></script>");
				System.out.println(sb.toString());
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(sb.toString());
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	// endregion
	
	// region changeGroupsAuthority
	@RequestMapping(value = "changeGroupsAuthority", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object changeGroupsAuthority(HttpServletRequest request) {
		logger.info("changeGroupsAuthority : " + request.getLocalAddr());
		try {
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());
			for(HashMap<String, Object> m : jArray) {
				Iterator<String> keys = m.keySet().iterator();
				while(keys.hasNext()){
					String key = keys.next();
					System.out.print(key+"::"+m.get(key)+"\t");
				}
				System.out.println();
			}
//			ArrayList<Integer> userIdList = new ArrayList<Integer>();
//			for (HashMap<String, Object> map : jArray) {
//				userIdList.add(Integer.parseInt(map.get("site_id").toString()));
//			}
//			HashMap<String, Object> parameter = new HashMap<>();
//			parameter.put("list", userIdList);
//			int cnt = siteInfoService.delete_SITE_INFO_RECORDS(parameter);
//			if (cnt > -1)
//				result.result = true;
//			else
//				result.result = false;
			result.result=true;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion
	/***************************************************** public *****************************************************/
	// region getSiteNames
	@RequestMapping(value = "getSiteNames", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getSiteNames(HttpServletRequest request) {
		logger.info("getSiteNames : " + request.getLocalAddr());
		try {
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

}
