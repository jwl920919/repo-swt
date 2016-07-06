package MVC.ShinwooTNS.action;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping(value = "getSystemUserManagementDatatableDatas", method = RequestMethod.GET)
	public String getSystemUserManagementDatatableDatas(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSystemUserManagementDatatableDatas : " + request.getLocalAddr());
		System.out.println("getSystemUserManagementDatatableDatas Controller");
		// Session에 로그인 정보가 있는지 체크
//		HttpSession session = request.getSession(true);
//		System.out.println(session.getAttribute("login_chk"));
//		if (session.getAttribute("login_chk") == null)
//			return "redirect:login";
//		
		List<SYSTEM_USER_INFO_DTO> userDataList = userInfoService.select_SYSTEM_USER_INFO();
		
		result.result = true;
		result.resultValue = userDataList;
		try {
			response.getWriter().println("test");
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return gson.toJson(result);
	}
	@RequestMapping(value = "*")
	public void default_go(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSystemUserManagementDatatableDatas : " + request.getLocalAddr());
		System.out.println("getSystemUserManagementDatatableDatas Controller");
		// Session에 로그인 정보가 있는지 체크
//		HttpSession session = request.getSession(true);
//		System.out.println(session.getAttribute("login_chk"));
//		if (session.getAttribute("login_chk") == null)
//			return "redirect:login";
//		
		List<SYSTEM_USER_INFO_DTO> userDataList = userInfoService.select_SYSTEM_USER_INFO();
		
		result.result = true;
		result.resultValue = userDataList;
		try {
			response.getWriter().println("test");
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
