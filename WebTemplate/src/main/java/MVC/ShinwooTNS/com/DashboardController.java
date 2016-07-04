package MVC.ShinwooTNS.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import Common.Controller.SessionLocaleController;
import Common.DTO.AjaxResult;
import Common.Helper.CommonHelper;
import Common.Helper.LanguageHelper;
import Common.ServiceInterface.UI_MENU_MASTER_Service_Interface;
import net.sf.json.JSON;
import scala.annotation.meta.setter;

@Controller
public class DashboardController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
	
	@RequestMapping(value = "/dashboard/dashboard", method = RequestMethod.GET)
	public String Main(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("dashboard : " + request.getLocalAddr());
		System.out.println("dashboard Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		return "/dashboard/dashboard";
	}
}
