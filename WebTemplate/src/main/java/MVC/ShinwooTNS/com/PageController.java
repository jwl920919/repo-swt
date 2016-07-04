package MVC.ShinwooTNS.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import Common.Helper.CommonHelper;
import Common.Helper.CryptoHelper;
import Common.Helper.LanguageHelper;

@Controller
public class PageController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Initialize(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		logger.info("Initialize : " + request.getLocalAddr());
		System.out.println("Initialize Controller");

		// 암복호화 테스트
		// String Encrypt = CryptoHelper.getEncrypt("신우티엔에스123!");
		// String Decrypt = CryptoHelper.getDecrypt(Encrypt);
		// System.out.println("Encrypt : " + Encrypt);
		// System.out.println("Decrypt : " + Decrypt);

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
			return "main";
		else
			return "redirect:login";
		// endregion Login Session check
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String Main(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Main : " + request.getLocalAddr());
		System.out.println("Main Controller");
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";
		
		return "main";
	}

	//TEST
	@RequestMapping(value = "/mainTemplate", method = RequestMethod.GET)
	public String MainTemplate(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("mainTemplate Controller");		
		return "mainTemplate";
	}

	//TEST
	@RequestMapping(value = "/longPollingSample", method = RequestMethod.GET)
	public String longPollingSample(long timed, HttpServletResponse response) throws Exception {
		System.out.println("longPollingSample Controller");	
		
		
		
		return "mainTemplate";
	}	
}
