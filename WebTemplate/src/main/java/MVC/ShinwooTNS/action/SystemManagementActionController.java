package MVC.ShinwooTNS.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Common.DTO.AjaxResult;
import Common.Helper.CommonHelper;

@Controller
@RequestMapping(value = "/systemManagement/")
public class SystemManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(SystemManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	@Autowired StringRedisTemplate redisTemplate;
	
	@RequestMapping(value = "expiryCheck", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object expiryCheck(HttpServletRequest request, HttpSession session) {
		logger.info("expiryCheck : " + request.getLocalAddr());
		init();
		try {
			String json = redisTemplate.opsForValue().get("status:device:dhcp:1");
			if(json!=null) {
				HashMap<String, Object> hwInfo = gson.fromJson(json,
						new TypeToken<HashMap<String, Object>>() {
						}.getType());
//				Calendar calendar = Calendar.getInstance();
				List<HashMap<String, Object>> licensesInfo = gson.fromJson(hwInfo.get("licenses").toString(),
						new TypeToken<List<HashMap<String, Object>>>() {
						}.getType());
				List<Map<String, Object>> resultArray = new ArrayList<>();
				System.out.println(licensesInfo);
//				while (iterator.hasNext()) {
//					JSONObject jObj = (JSONObject) iterator.next();
//					long expiryDate = CommonHelper.objectToLong(jObj.get("expiry_date"))*1000;
//					long now = calendar.getTimeInMillis();
//					long gap = expiryDate - now;
//					HashMap<String, Object> data = new HashMap<>();
//					data.put("type", jObj.get("type"));
//					data.put("now", now);
//					data.put("gap", gap);
//					if (gap > 0) {
//						data.put("result", true);
//					} else {
//						data.put("result", false);
//					}
//					resultArray.add(data);
//				}
				result.data = resultArray;
			}
			
			result.result = true;

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
