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
import net.sf.json.JSON;
import scala.annotation.meta.setter;

@Controller
public class PageController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Initialize(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		logger.info("Initialize : " + request.getLocalAddr());
		System.out.println("Initialize Controller");

		// region 다국어 Class Instance
		// 각자 다국어 설정을 체크 필요
		String strFileName = (new CommonHelper()).getCookies(request, "Language");
		if (strFileName != null) {
			LanguageHelper.getInstance(strFileName);
		} else {
			String sLanguageValue = LanguageHelper.getInstance(locale).getFileName();
			Cookie cookie = new Cookie("Language", sLanguageValue);
			cookie.setMaxAge(365 * 24 * 60 * 60); // 1년
			response.addCookie(cookie);
		}
		// endregion 다국어 Class Instance

		// region Login Session check
		System.out.println(MessageFormat.format("Login Check : {0}", session.getAttribute("login_chk")));

		if (session.getAttribute("login_chk") != null)
			return "redirect:/ipRequestMain";
		else
			return "redirect:/login";
		// endregion Login Session check
	}

	@RequestMapping(value = "/ipRequestMain", method = RequestMethod.GET)
	public String ipRequestMain(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("ipRequestMain : " + request.getLocalAddr());
		System.out.println("ipRequestMain Controller");
		logger.info("Join User IP : " + request.getRemoteAddr());
		System.out.println("");
		System.out.println("===============================================");
		System.out.println("Join User IP : " + request.getRemoteAddr());
		System.out.println("===============================================");
		System.out.println("");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		return "/ipRequestMain";
	}

	@RequestMapping(value = "/ipRequestList", method = RequestMethod.GET)
	public String ipRequestList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("ipRequestList : " + request.getLocalAddr());
		System.out.println("ipRequestList Controller");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		return "/ipRequestList";
	}
}
