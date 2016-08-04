package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;
import Common.DTO.SITE_INFO_DTO;
import Common.ServiceInterface.SITE_INFO_Service_interface;

@Controller
public class PageActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageActionController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
		
	@Autowired
	private SITE_INFO_Service_interface siteInfoService;
	
	@RequestMapping(value = "/set_Sesstion", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object set_Sesstion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("set_Sesstion");
		logger.info("set_Sesstion : " + request.getLocalAddr());
		
		try {		
			HttpSession session = request.getSession(true);

			String oldSite_id = session.getAttribute("site_id").toString();
			String oldSite_name = session.getAttribute("site_name").toString();
			String oldSite_master = session.getAttribute("site_master").toString();

			session.setAttribute("site_id", request.getParameter("site_id"));// Session에 "login_chk"로 값을 저장.
			session.setAttribute("site_name", request.getParameter("site_name"));// Session에 "login_chk"로 값을 저장.
			session.setAttribute("site_master", request.getParameter("site_master"));// Session에 "login_chk"로 값을 저장.

			String newSite_id = session.getAttribute("site_id").toString();
			String newSite_name = session.getAttribute("site_name").toString();
			String newSite_master = session.getAttribute("site_master").toString();
			
			HashMap<String, String> sessionMap = new HashMap<String, String>();
			sessionMap.put("oldSite_id", oldSite_id);
			sessionMap.put("oldSite_name", oldSite_name);
			sessionMap.put("oldSite_master", oldSite_master);
			sessionMap.put("newSite_id", newSite_id);
			sessionMap.put("newSite_name", newSite_name);
			sessionMap.put("newSite_master", newSite_master);
			
			result.result = true;
			result.resultValue = sessionMap;
		}  catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/session_Maintain", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String session_Maintain(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("session_Maintain");
		logger.info("session_Maintain : " + request.getLocalAddr());
		result = new AjaxResult();
		
		try {
			HttpSession session = request.getSession(true);
			System.out.println(session.getAttribute("login_chk"));
			if (session.getAttribute("login_chk") == null)
			{
				return "redirect:login";
			}
			result.result = true;

		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return "redirect:login";
		}
		return gson.toJson(result);
	}
	
	@RequestMapping(value = "/select_site_info", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object select_site_info(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("select_site_info");
		logger.info("select_site_info : " + request.getLocalAddr());
		result = new AjaxResult();
		
		try {									
			List<SITE_INFO_DTO> siteInfoList = siteInfoService.select_SITE_INFO();
			
			result.result = true;
			result.resultValue = siteInfoList;
		}  catch (Exception e) {
			result.result = false;
			result.errorMessage = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return gson.toJson(result);
	}

	
	// TEST
	@RequestMapping(value = "/ajaxPollingTest.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public @ResponseBody Object isAnonymous(HttpServletRequest request) {
		System.out.println("ajaxPollingTest.do");
		
	 	String param1 = request.getParameter("param1");
	 	String param2 = request.getParameter("param2");
	 	System.out.println("param1 : " + param1);
	 	System.out.println("param2 : " + param2);
		
		Map<String, Object> mData  = new HashMap<String, Object>();
		mData.put("param1", Integer.parseInt(param1) + 1);
		mData.put("param2", "현재시간 : " + (new Date()).toString());

		result.result = true;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(mData);
		
		result.data = list;
		
		//return date.toString();		
		return gson.toJson(result);
	}
}
