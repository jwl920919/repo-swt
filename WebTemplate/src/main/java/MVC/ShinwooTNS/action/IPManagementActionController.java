package MVC.ShinwooTNS.action;

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

import Common.DTO.AjaxResult;
import Common.DTO.SYSTEM_USER_INFO_DTO;
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
	
	@RequestMapping(value = "/staticIPStatus_Segment_Select", method = RequestMethod.POST, produces = "application/text; charset=utf8")
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
}
