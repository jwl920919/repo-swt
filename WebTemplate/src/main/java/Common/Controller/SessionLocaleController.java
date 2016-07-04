/**
 * 
 */
/**
 * @author cmkim
 *
 */
package Common.Controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import Common.Helper.LanguageHelper;

@Controller
public class SessionLocaleController {
	@RequestMapping(value = "/setChangeLocale.do")
	public String changeLocale(@RequestParam(required = false) String locale, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Locale locales = null;
		// 넘어온 파라미터에 ko가 있으면 Locale의 언어를 한국어로 바꿔준다.
		// 기본 Locale은 한국어로 설정한다.
		if (locale.contains("ko")) {
			locales = Locale.KOREAN;
		} else if (locale.contains("en")) {
			locales = Locale.ENGLISH;
		} else if (locale.contains("zh")) {
			locales = Locale.CHINA;
		} else {
			locales = Locale.KOREAN;
		}

		LanguageHelper.getInstance(locales);
		// 세션에 존재하는 Locale을 새로운 언어로 변경해준다.
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locales);
		// 해당 컨트롤러에게 요청을 보낸 주소로 돌아간다.
		String redirectURL = "redirect:" + request.getHeader("referer");
		return redirectURL;
	}
	
	public String changeLocale(String locale, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return changeLocale(locale, null, request, response);
	}
}