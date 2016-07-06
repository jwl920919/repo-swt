package MVC.ShinwooTNS.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;

@Controller
public class PageActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageActionController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
		
	// TEST
	@RequestMapping(value = "/ajaxPollingTest.do", method = RequestMethod.POST)
	public @ResponseBody Object isAnonymous(HttpServletRequest request) {
		System.out.println("ajaxPollingTest.do");
		
	 	String param1 = request.getParameter("param1");
	 	String param2 = request.getParameter("param2");
	 	System.out.println("param1 : " + param1);
	 	System.out.println("param2 : " + param2);
		
		Map<String, Object> mData  = new HashMap<String, Object>();
		mData.put("param1", Integer.parseInt(param1) + 1);
		mData.put("param2", (new Date()).toString());

		result.result = true;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(mData);
		
		result.data = list;
		
		//return date.toString();		
		return gson.toJson(result);
	}
}
