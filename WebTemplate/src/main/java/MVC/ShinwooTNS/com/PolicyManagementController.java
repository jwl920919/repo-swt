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
@RequestMapping(value = "/policyManagement/")
public class PolicyManagementController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static String parentPath = "/policyManagement/";
	private static final Logger logger = Logger.getLogger(PolicyManagementController.class);
	
	@RequestMapping(value = "accessPolicy", method = RequestMethod.GET)
	public String accessPolicy(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("accessPolicy : " + request.getLocalAddr());
		System.out.println("accessPolicy Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		return parentPath + "accessPolicy";
	}	
}
