package MVC.ShinwooTNS.action;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.*;
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
import Common.Helper.ErrorLoggingHelper;

@Controller
@RequestMapping(value="/dashboard/")
public class DashboardActionController {
	private final static String parentPath = "/dashboard/";
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = Logger.getLogger(DashboardActionController.class);
	private Gson gson = new Gson();
	
	@RequestMapping(value = "select", method = RequestMethod.POST)
	public String Main(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("dashboard : " + request.getLocalAddr());
		System.out.println("dashboard Controller");			
		AjaxResult result = new AjaxResult();
		
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";
		
		return gson.toJson(result);
	}
	
	// redisTemplate를 쓰기위해 Autowired 해준다.
		@Autowired
		StringRedisTemplate redisTemplate;
		
		/**
		 * 세그먼트별 리스IP 할당 현황
		 * */
		// region segmentLeasingIPAssigned_Data
		@RequestMapping(value = "segmentLeasingIPAssigned_Data", method = RequestMethod.POST, produces = "application/text; charset=utf8")
		public @ResponseBody Object segmentLeasingIPAssigned_Data(HttpServletRequest request, HttpSession session) {
			logger.info("segmentLeasingIPAssigned_Data : " + request.getLocalAddr());
			AjaxResult result = new AjaxResult();
			
			try {
				// json 형식의 데이터를 String 타입으로 받아온다.
				HashMap<String, Object> parameters = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {}.getType());
				String siteID = session.getAttribute("site_id").toString();
				String json = "";
				
				//redis에 메시지를 보내 JSON 형식의 String 데이터를 받아온다.
				//System.out.println("siteID : " + siteID + ", m_ipType :" + m_ipType);
//				if ( Integer.parseInt(siteID) <= 1) {
//					json = redisTemplate.opsForValue().get("status:dashboard:lease_ipv6_status");
//				}
//				else {
					json = redisTemplate.opsForValue().get("status:dashboard:network_ip_status:" + session.getAttribute("site_id").toString());
//				}

				System.out.println("");
				System.out.println("");
				System.out.println("network_ip_status json : " + json);
				System.out.println("");
				System.out.println("");
				
				result.resultValue = json;
				result.result = true;
				return gson.toJson(result);
			} catch (Exception e) {
				e.printStackTrace();
				ErrorLoggingHelper.log(logger, "segmentLeasingIPAssigned_Data", e);
				result.result = false;
				return gson.toJson(result);
			}
		}
		// endregion

		
		/**
		 * IPv4 고정, 리스, 미사용 현황
		 * */
		// region assignmentIPv4Status_Data
		@RequestMapping(value = "assignmentIPv4Status_Data", method = RequestMethod.POST, produces = "application/text; charset=utf8")
		public @ResponseBody Object assignmentIPv4Status_Data(HttpServletRequest request, HttpSession session) {
			//logger.info("assignmentIPv4Status_Data : " + request.getLocalAddr());			
			AjaxResult result = new AjaxResult();
			
			try {
				// json 형식의 데이터를 String 타입으로 받아온다.
				HashMap<String, Object> parameters = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {}.getType());
				String siteID = session.getAttribute("site_id").toString();
				String json = "";
				
				//redis에 메시지를 보내 JSON 형식의 String 데이터를 받아온다.
				//System.out.println("siteID : " + siteID + ", m_ipType :" + m_ipType);
				if ( Integer.parseInt(siteID) <= 1) {
					json = redisTemplate.opsForValue().get("status:dashboard:lease_ipv4_status");
				}
				else {
					json = redisTemplate.opsForValue().get("status:dashboard:lease_ipv4_status:" + session.getAttribute("site_id").toString());
				}

				System.out.println("");
				System.out.println("");
				System.out.println("status:dashboard:lease_ipv4_status json : " + json);
				System.out.println("");
				System.out.println("");
				
				result.resultValue = json;
				result.result = true;
				return gson.toJson(result);
			} catch (Exception e) {
				e.printStackTrace();
				ErrorLoggingHelper.log(logger, "assignmentIPv4Status_Data", e);
				result.result = false;
				return gson.toJson(result);
			}
		}
		// endregion
		
		/**
		 * IPv64 고정, 리스, 미사용 현황
		 * */
		// region assignmentIPv6Status_Data
		@RequestMapping(value = "assignmentIPv6Status_Data", method = RequestMethod.POST, produces = "application/text; charset=utf8")
		public @ResponseBody Object assignmentIPv6Status_Data(HttpServletRequest request, HttpSession session) {
			//logger.info("assignmentIPv6Status_Data : " + request.getLocalAddr());
			AjaxResult result = new AjaxResult();
			
			try {
				// json 형식의 데이터를 String 타입으로 받아온다.
				HashMap<String, Object> parameters = gson.fromJson(request.getReader(), new TypeToken<HashMap<String, Object>>() {}.getType());
				String siteID = session.getAttribute("site_id").toString();
				String json = "";
				
				//redis에 메시지를 보내 JSON 형식의 String 데이터를 받아온다.
				//System.out.println("siteID : " + siteID + ", m_ipType :" + m_ipType);
				if ( Integer.parseInt(siteID) <= 1) {
					json = redisTemplate.opsForValue().get("status:dashboard:lease_ipv6_status");
				}
				else {
					json = redisTemplate.opsForValue().get("status:dashboard:lease_ipv6_status:" + session.getAttribute("site_id").toString());
				}

				System.out.println("");
				System.out.println("");
				System.out.println("status:dashboard:lease_ipv6_status json : " + json);
				System.out.println("");
				System.out.println("");
				
				result.resultValue = json;
				result.result = true;
				return gson.toJson(result);
			} catch (Exception e) {
				e.printStackTrace();
				ErrorLoggingHelper.log(logger, "assignmentIPv6Status_Data", e);
				result.result = false;
				return gson.toJson(result);
			}
		}
		// endregion

}
