package MVC.ShinwooTNS.com;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;

@Controller
@RequestMapping(value = "/configManagement/")
public class ConfigMangementController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static String parentPath = "/configManagement/";
	private static final Logger logger = Logger.getLogger(ConfigMangementController.class);
	
	@RequestMapping(value = "systemUserManagement", method = RequestMethod.GET)
	public String systemUserManagement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("systemUserManagement : " + request.getLocalAddr());
		System.out.println("systemUserManagement Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		return parentPath + "systemUserManagement";
	}
	
	@RequestMapping(value = "systemGroupManagement", method = RequestMethod.GET)
	public String systemGroupManagement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("systemGroupManagement : " + request.getLocalAddr());
		System.out.println("systemGroupManagement Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		return parentPath + "systemGroupManagement";
	}
	
	@RequestMapping(value = "systemMenuAuthorityManagement", method = RequestMethod.GET)
	public String systemMenuAuthorityManagement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("systemMenuAuthorityManagement : " + request.getLocalAddr());
		System.out.println("systemMenuAuthorityManagement Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		return parentPath + "systemMenuAuthorityManagement";
	}
	@RequestMapping(value = "systemGroupManagementIntegration", method = RequestMethod.GET)
	public String systemGroupManagementIntegration(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("systemGroupManagementIntegration : " + request.getLocalAddr());
		System.out.println("systemGroupManagementIntegration Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		if (!session.getAttribute("site_master").toString().toLowerCase().equals("t")){
			return "redirect:/login";
		}
		
		return parentPath + "systemGroupManagementIntegration";
	}
	@RequestMapping(value = "systemGroupManagementNotIntegration", method = RequestMethod.GET)
	public String systemGroupManagementNotIntegration(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("systemGroupManagementNotIntegration : " + request.getLocalAddr());
		System.out.println("systemGroupManagementNotIntegration Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		return parentPath + "systemGroupManagementNotIntegration";
	}
}
