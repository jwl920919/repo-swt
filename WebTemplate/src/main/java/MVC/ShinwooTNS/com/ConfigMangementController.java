package MVC.ShinwooTNS.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;
import Common.ServiceInterface.SYSTEM_USER_INFO_Service_Interface;

@Controller
@RequestMapping(value = "/configMangement/")
public class ConfigMangementController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static String parentPath = "/configMangement/";
	private static final Logger logger = LoggerFactory.getLogger(ConfigMangementController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	
	@Autowired
	private SYSTEM_USER_INFO_Service_Interface userInfoService;
	
	@RequestMapping(value = "systemUserManagement", method = RequestMethod.GET)
	public String Main(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("systemUserManagement : " + request.getLocalAddr());
		System.out.println("systemUserManagement Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";
		
		List<Common.DTO.SYSTEM_USER_INFO_DTO> userList = new ArrayList<>();
		userList = userInfoService.select_SYSTEM_USER_INFO();
		for(Common.DTO.SYSTEM_USER_INFO_DTO sui : userList){
			System.out.println(sui);
		}
		
		return parentPath + "systemUserManagement";
	}
}
