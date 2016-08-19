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

import org.apache.log4j.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import Common.DTO.SYSTEM_USER_GROUP_DTO;
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.Helper.ErrorLoggingHelper;
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
	private static final Logger logger = Logger.getLogger(ConfigManagementActionController.class);
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
			HttpServletResponse response, HttpSession session) {
		try {
			init();
			String[] columns = { "user_id", "user_name" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
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
			ErrorLoggingHelper.log(logger, "getSystemUserManagementDatatableDatas", e);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getSystemUserManagementDatatableDatas", e);
		}
	}
	// endregion

	// region getUserInfo
	@RequestMapping(value = "getUserInfo", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getUserInfo(HttpServletRequest request) {
		try {
			init();
			SYSTEM_USER_INFO_DTO systemUserInfo = userInfoService.select_SYSTEM_USER_INFO_ONE_SEARCH(
					(HashMap<String, Object>)
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
			ErrorLoggingHelper.log(logger, "getUserInfo", e1);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region updateUserInfo
	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object updateUserInfo(HttpServletRequest request, HttpSession session) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			String time_zone = parameters.get("time_zone").toString();
			parameters.put("group_id", Integer.parseInt(parameters.get("group_id").toString()));
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));

			int cnt = userInfoService.update_SYSTEM_USER_INFO_ONE_RECORD(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "updateUserInfo", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region checkId
	@RequestMapping(value = "checkId", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object checkId(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "checkId", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region addUser
	@RequestMapping(value = "addUser", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addUser(HttpServletRequest request,HttpSession session) {
		try {
			init();
			HashMap<String, Object> parameters = gson.fromJson(request.getReader(),
					new TypeToken<HashMap<String, Object>>() {
					}.getType());
			int group_id = Integer.parseInt(parameters.get("group_id").toString());
			parameters.remove("group_id");
			parameters.put("group_id", group_id);
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			int cnt = userInfoService.insert_SYSTEM_USER_INFO_ONE_RECORD(parameters);
			if (cnt > -1)
				result.result = true;
			else
				result.result = false;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "addUser", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region deleteUser
	@RequestMapping(value = "deleteUsers", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteUsers(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "deleteUsers", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region getGroupNames
	@RequestMapping(value = "getGroupNames", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getGroupNames(HttpServletRequest request) {
		try {
			init();
			List<SYSTEM_USER_GROUP_DTO> groupsInfoList = groupInfoService.select_SYSTEM_USER_GROUP_INFO();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (SYSTEM_USER_GROUP_DTO sugd : groupsInfoList) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("group_name", sugd.getGroup_name());
				map.put("group_id", sugd.getGroup_id());
				mapList.add(map);
			}
			result.data = mapList;
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getGroupNames", e);
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
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "getPlaceOfBusinessDatatableDatas", e);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getPlaceOfBusinessDatatableDatas", e);
		}
	}
	// endregion

	// region getUserGroupDatatableDatas
	@RequestMapping(value = "getUserGroupDatatableDatas", method = RequestMethod.POST)
	public void getUserGroupDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response,HttpSession session) {
		try {
			init();
			String[] columns = { "site_name", "group_name", "group_desc", "group_id" };
			HashMap<String, Object> parameters = Common.Helper.DatatableHelper.getDatatableParametas(request, columns,
					1);
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			parameters.put("site_master", session.getAttribute("site_master"));
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
			ErrorLoggingHelper.log(logger, "getUserGroupDatatableDatas", e);
		}
	}
	// endregion

	// region addSite
	@RequestMapping(value = "addSite", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addSite(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "addSite", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region addGroup
	@RequestMapping(value = "addGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object addGroup(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "addGroup", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region updateSite
	@RequestMapping(value = "updateSite", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object updateSite(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "updateSite", e);
			result.result = false;
			return gson.toJson(result);
		}

	}
	// endregion

	// region updateGroup
	@RequestMapping(value = "updateGroup", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object updateGroup(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "updateGroup", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region deleteSites
	@RequestMapping(value = "deleteSites", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteSites(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "deleteSites", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region deleteGroups
	@RequestMapping(value = "deleteGroups", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object deleteGroups(HttpServletRequest request) {
		try {
			init();
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
			ErrorLoggingHelper.log(logger, "deleteGroups", e);
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
		try {
			init();
			HttpSession session = request.getSession(true);
			HashMap<String, Object> parameters = new HashMap<>();
			parameters.put("site_id", Integer.parseInt(session.getAttribute("site_id").toString()));
			List<Map<String, Object>> tableInfo = authMenuService.select_AUTH_MENU_MAKE_SEARCH_FOR_SITE(parameters);// body_info
			List<Map<String, Object>> groupNames = authMenuService.select_AUTH_MENU_GROUP_NAMES(parameters);// header
			List<Map<String, Object>> menuMaster = authMenuService.select_AUTH_MENU_M_MASTER();// body-master
			List<Map<String, Object>> menuSub = authMenuService.select_AUTH_MENU_M_SUB();// body-sub
			if (groupNames.size() > 0) {
				StringBuffer sb = new StringBuffer();
				sb.append(
						"<div class='save-box'><input class='save-button btn btn-primary' type='button' value='저장'></div>");
				sb.append("<table id='auth_table' >");
				sb.append("<thead id='auth_table_head'>");
				sb.append("<th colspan=2>메뉴</th>");

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
									tmp.append("<input type='hidden' name='is_changed' value="
											+ (Integer.parseInt(ti.get("auth_menu_id").toString()) == -1 ? true : false)
											+ " />");
									tmp.append(authState.equals("W") ? "읽기/쓰기" : authState.equals("R") ? "읽기" : "권한없음");
									tmp.append("</td>");
									tiRemoveList.add(ti);
								}
							}

							for (Object ti : tiRemoveList) {
								tableInfo.remove(ti);
							}
							subRemoveList.add(ms);
							tmp.append("</tr>");
							// test
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
				sb.append(
						"<div class='save-box'><input class='save-button btn btn-primary' type='button' value='저장'></div>");
				sb.append("<script src='resources/js/configMangement/systemMenuAuthorityManagementTable.js'></script>");
				System.out.println(sb.toString());
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(sb.toString());
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getAuthorityTable", e);
		}
	}

	// endregion

	// region changeGroupsAuthority
	@RequestMapping(value = "changeGroupsAuthority", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object changeGroupsAuthority(HttpServletRequest request) {
		try {
			init();
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());

			for (HashMap<String, Object> parameters : jArray) {
				int auth_menu_id = Integer.parseInt(parameters.get("auth_menu_id").toString());
				int site_id = Integer.parseInt(parameters.get("site_id").toString());
				int group_id = Integer.parseInt(parameters.get("group_id").toString());
				parameters.remove("auth_menu_id");
				parameters.remove("site_id");
				parameters.remove("group_id");
				parameters.put("auth_menu_id", auth_menu_id);
				parameters.put("site_id", site_id);
				parameters.put("group_id", group_id);
				if (auth_menu_id == -1) {
					if (authMenuService.insert_AUTH_MENU(parameters) < 0) {
						throw new Exception();
					}
				} else {
					if (parameters.get("is_changed").toString().equals("true")) {
						if (authMenuService.update_AUTH_MENU(parameters) < 0) {
							throw new Exception();
						}
					}
				}
			}
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "changeGroupsAuthority", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// endregion
	/***************************************************** public *****************************************************/
	// region getSiteNames
	@RequestMapping(value = "getSiteNames", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getSiteNames(HttpServletRequest request) {
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
			ErrorLoggingHelper.log(logger, "getSiteNames", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion

	// region getCurrentSiteInfo
	@RequestMapping(value = "getCurrentSiteInfo", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object getCurrentSiteInfo(HttpServletRequest request, HttpSession session) {
		try {
			init();
			SITE_INFO_DTO sid = siteInfoService
					.select_SITE_INFO_ONE_SEARCH(Integer.parseInt(session.getAttribute("site_id").toString()));
			HashMap<String, Object> map = new HashMap<>();
			map.put("site_name", sid.getSite_name());
			map.put("site_id", sid.getSite_id());
			result.resultValue = map;
			result.result = true;
			return gson.toJson(result);
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "getCurrentSiteInfo", e);
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion
	
	private void init() {
		result.data=null;
		result.resultValue=null;
	}
}
