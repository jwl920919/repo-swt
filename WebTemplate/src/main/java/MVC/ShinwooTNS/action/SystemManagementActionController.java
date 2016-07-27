package MVC.ShinwooTNS.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Common.DTO.AjaxResult;

@Controller
@RequestMapping(value = "/systemManagement/")
public class SystemManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(SystemManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	@RequestMapping(value = "expiryCheck", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object expiryCheck(HttpServletRequest request, HttpSession session) {
		logger.info("expiryCheck : " + request.getLocalAddr());
		init();
		try {
			List<HashMap<String, Object>> jArray = gson.fromJson(request.getReader(),
					new TypeToken<List<HashMap<String, Object>>>() {
					}.getType());
			System.out.println(jArray);
			for(HashMap<String, Object> jObj : jArray) {
				
				Object value = jObj.get("expiry_date");
				String temp = String.format("%.0f", value);
				
				System.out.println(Long.parseLong(temp));
			}
			Calendar calendar = Calendar.getInstance();
//			long expiryDate = Long.parseLong(jObj.get("expiry_date").toString());
//			long now = calendar.getTimeInMillis();
//			long gap = expiryDate - now;
//			Map<String, Object> data = new HashMap<>();
//			data.put("now", now);
//			data.put("gap",gap);
//			if(gap > 0) {
//				result.resultValue = data;
//				result.result = true;
//			} else {
//				result.result = false;
//			}
			
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}

	private void init() {
		result.data = null;
		result.resultValue = null;
	}
}
