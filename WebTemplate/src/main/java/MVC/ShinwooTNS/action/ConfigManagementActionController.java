package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;
import Common.DTO.SYSTEM_USER_INFO_DTO;
import Common.ServiceInterface.SYSTEM_USER_INFO_Service_Interface;

@Controller
@RequestMapping(value = "/configManagement/")
public class ConfigManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(ConfigManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	@Autowired
	private SYSTEM_USER_INFO_Service_Interface userInfoService;
	private final static String[] USER_COLUMNS = { "user_id", "user_name" };

	@RequestMapping(value = "getSystemUserManagementDatatableDatas", method = RequestMethod.POST)
	public void getSystemUserManagementDatatableDatas(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getSystemUserManagementDatatableDatas : " + request.getLocalAddr());
		System.out.println("getSystemUserManagementDatatableDatas Controller");
		try {
			String orderColumn = USER_COLUMNS[Integer.parseInt(request.getParameter("order[0][column]")) - 1],
					orderType = request.getParameter("order[0][dir]"),
					searchValue = request.getParameter("search[value]");
			int startIndex = Integer.parseInt(request.getParameter("start")),
					length = Integer.parseInt(request.getParameter("length"));
			HashMap<String, Object> parameters = new HashMap<>();
			parameters.put("searchValue", searchValue + "%");
			parameters.put("orderColumn", orderColumn);
			parameters.put("orderType", orderType);
			parameters.put("startIndex", startIndex);
			parameters.put("length", length);
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
			// dbHelper.close();
			int totalCount = userInfoService.select_SYSTEM_USER_INFO_CONDITIONAL_SEARCH_TOTAL_COUNT(parameters);
			StringBuffer sb = new StringBuffer("");
			sb.append(request.getParameter("callback"));
			sb.append("({");
			sb.append("\"draw\": ");
			sb.append(request.getParameter("draw"));
			sb.append(',');
			sb.append("\"recordsFiltered\": ");
			sb.append(totalCount);
			sb.append(',');
			sb.append("\"recordsTotal\": ");
			sb.append(totalCount);
			sb.append(',');
			sb.append("\"data\": ");
			sb.append(jsonArray);
			sb.append(',');
			sb.append("})");
			response.setContentType("Application/json;charset=utf-8");

			response.getWriter().println(sb.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
