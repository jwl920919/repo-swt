package MVC.ShinwooTNS.com;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;

@Controller
@RequestMapping(value = "/systemManagement/")
public class SystemManagementController {
	private final static String parentPath = "/systemManagement/";
	private static final Logger logger = LoggerFactory.getLogger(SystemManagementController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();
	
	@RequestMapping(value = "infobloxStatus", method = RequestMethod.GET)
	public String systemUserManagement(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("infobloxStatus : " + request.getLocalAddr());
		System.out.println("infobloxStatus Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:/login";
		
		return parentPath + "infobloxStatus";
	}
}
