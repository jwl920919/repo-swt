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
@RequestMapping(value = "/ipManagement/")
public class IPManagementController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static String parentPath = "/ipManagement/";
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
	
	@RequestMapping(value = "staticIPStatus", method = RequestMethod.GET)
	public String staticIPStatus(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("staticIPStatus : " + request.getLocalAddr());
		System.out.println("IPManagenentController staticIPStatus");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		
		return parentPath + "staticIPStatus";
	}
	
	@RequestMapping(value = "leaseIPStatus", method = RequestMethod.GET)
	public String leaseIPStatus(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("leaseIPStatus : " + request.getLocalAddr());
		System.out.println("IPManagenentController leaseIPStatus");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		
		return parentPath + "leaseIPStatus";
	}
	
	@RequestMapping(value = "ipCertifyStatus", method = RequestMethod.GET)
	public String ipCertifyStatus(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("ipCertifyStatus : " + request.getLocalAddr());
		System.out.println("IPManagenentController ipCertifyStatus");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		
		return parentPath + "ipCertifyStatus";
	}
	
	@RequestMapping(value = "blackListStatus", method = RequestMethod.GET)
	public String blackListStatus(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("blackListStatus : " + request.getLocalAddr());
		System.out.println("IPManagenentController blackListStatus");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		
		return parentPath + "blackListStatus";
	}
}
