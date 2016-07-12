package MVC.ShinwooTNS.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;
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
	public @ResponseBody Object staticIPStatus_Segment_Select(HttpServletRequest request) {
		System.out.println("staticIPStatus_Segment_Select");
		
//	 	String param1 = request.getParameter("param1");
//	 	String param2 = request.getParameter("param2");
//	 	System.out.println("param1 : " + param1);
//	 	System.out.println("param2 : " + param2);
//		
//		Map<String, Object> mData  = new HashMap<String, Object>();
//		mData.put("param1", Integer.parseInt(param1) + 1);
//		mData.put("param2", "현재시간 : " + (new Date()).toString());
//
//		result.result = true;
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		list.add(mData);
//		
//		result.data = list;
		
		try {
			if (result == null) {
				result = new AjaxResult();
			}

			List<Map<String, Object>> listMap = new ArrayList<>();
			listMap = ipManagementService.select_IP_MANAGEMENT_SEGMENT();
			result.result = true;
			result.data = listMap;

		} catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
		}
		//return date.toString();		
		return gson.toJson(result);
	}
}
