package MVC.ShinwooTNS.com;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;

@Controller
@RequestMapping(value="/dashboard/")
public class DashboardController {
	private final static String parentPath = "/dashboard/";
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = Logger.getLogger(DashboardController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
	
	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String Main(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("dashboard : " + request.getLocalAddr());
		System.out.println("dashboard Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		return parentPath + "dashboard";
	}
}
