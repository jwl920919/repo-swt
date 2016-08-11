package MVC.ShinwooTNS.com;

import Common.Controller.SessionLocaleController;
import Common.Helper.LanguageHelper;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);

	@Resource(name = "LanguageResources")
	private MessageSource messageSource;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		System.out.println(messageSource.getMessage("SystemTitle", null, locale));

		model.addAttribute("Title", messageSource.getMessage("SystemTitle", null, locale));

		String sTitle = LanguageHelper.GetLanguage("Title");
		System.out.println(sTitle);
		model.addAttribute("sTitle", sTitle);

		return "home";
	}

	@RequestMapping(value = "/summit.do", method = RequestMethod.POST)
	public String testSummit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("summit.do");
		
		Enumeration enumeration = request.getParameterNames();
		
		while (enumeration.hasMoreElements()) {
			System.out.println(enumeration.nextElement());
		}

		// 폼에서 컨트롤을 name으로 찾기
		String sLocale = request.getParameter("languageSelect");
		String home = request.getParameter("home");
		String testButton = request.getParameter("testButton");

		SessionLocaleController sessionLocale = new SessionLocaleController();
		sessionLocale.changeLocale(sLocale, request, response);

		System.out.println("uri : " + request.getRequestURI());
		System.out.println("conPath : " + request.getContextPath());
		System.out.println("com : " + request.getRequestURI().substring(request.getContextPath().length()));

		String target = request.getParameter("home");
		if (target == "Home 1") {
			return "home2";
		} else {
			return "home";
		}
	}
}
