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
public class PageController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
	
	
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
			return "redirect:/main";
		else
			return "redirect:/login";
		// endregion Login Session check
	}

	@Autowired
	private UI_MENU_MASTER_Service_Interface memuMasterService;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String Main(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Main : " + request.getLocalAddr());
		System.out.println("Main Controller");
		logger.info("Join User IP : " + request.getRemoteAddr());
		System.out.println("");
		System.out.println("===============================================");
		System.out.println("Join User IP : " + request.getRemoteAddr());
		System.out.println("===============================================");
		System.out.println("");
		// Session에 로그인 정보가 있는지 체크
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "redirect:login";

		List<HashMap<String, Object>> listMenu = new ArrayList<>();
		listMenu = memuMasterService.select_UI_MENU_MASTER();
		// listMenu = myappService.selectTestTable();

		// region 메뉴 데이터 생성
		StringBuilder sb = new StringBuilder();
		String sMasterCD = null;
		String sSubCD = null;
		String sMasterNameKey = null;
		String ssubNameKey = null;
		for (int i = 0; i < listMenu.size(); i++) {
			System.out.println(listMenu.get(i));
			// System.out.println(listMenu.get(i).get("sSubDiscription"));

			if (sMasterCD == null) {
				sMasterCD = listMenu.get(i).get("master_cd").toString();
				sMasterNameKey = listMenu.get(i).get("master_namekey").toString();
				ssubNameKey = listMenu.get(i).get("subname_key").toString();
				sb.append("<li class='active treeview' id='menu_" + sMasterCD +"'>");
//				sb.append("	<a href='" + listMenu.get(i).get("sMasterURL").toString() + "'>");
				sb.append("	<a onclick='changeframe(&quot;" + listMenu.get(i).get("master_url").toString() + "&quot;, &quot;" + sMasterCD + "&quot;, &quot;" + sMasterNameKey + "&quot;, &quot;" + ssubNameKey + "&quot;);' style='cursor:pointer' >");
				sb.append("		<i class='fa " + listMenu.get(i).get("master_icon").toString() + "'></i>");
				sb.append("		<span>" + LanguageHelper.GetLanguage(listMenu.get(i).get("master_namekey").toString())
						+ "</span>");
				if (sMasterCD.equals("M01")) {
					sb.append("			</a></li>");
				} else {
					sSubCD = listMenu.get(i).get("sub_cd").toString();
					sb.append("		<i class='fa fa-angle-left pull-right'></i></a>");
					sb.append("	<ul class='treeview-menu' name='ulMenu'>");
					sb.append("		<li class='active' id='menu_" + sSubCD +"'>");
//					sb.append("			<a href='" + listMenu.get(i).get("sSubUrl").toString() + "'>");
					sb.append("			<a onclick='changeframe(&quot;" + listMenu.get(i).get("sub_Url").toString() + "&quot;, &quot;" + sSubCD + "&quot;, &quot;" + sMasterNameKey + "&quot;, &quot;" + ssubNameKey + "&quot;);' style='cursor:pointer' >");
					sb.append("				<i class='fa fa-circle-o'></i>");
					sb.append(LanguageHelper.GetLanguage(listMenu.get(i).get("subname_key").toString()));
					sb.append("			</a></li>");
				}
			} else if (sMasterCD.equals(listMenu.get(i).get("master_cd").toString())) {
				// 같은 Master 메뉴 처리
				if (!sMasterCD.equals("M01")) {
					sSubCD = listMenu.get(i).get("sub_cd").toString();
					ssubNameKey = listMenu.get(i).get("subname_key").toString();
					sb.append("		<li id='menu_" + sSubCD +"'>");
//					sb.append("			<a href='" + listMenu.get(i).get("sSubUrl").toString() + "'>");
					sb.append("			<a onclick='changeframe(&quot;" + listMenu.get(i).get("sub_Url").toString() + "&quot;, &quot;" + sSubCD + "&quot;, &quot;" + sMasterNameKey + "&quot;, &quot;" + ssubNameKey + "&quot;);' style='cursor:pointer' >");
					sb.append("				<i class='fa fa-circle-o'></i>");
					sb.append(LanguageHelper.GetLanguage(listMenu.get(i).get("subname_key").toString()));
					sb.append("			</a></li>");
				}
			} else {
				// 다른 Master 메뉴 처리
				sMasterCD = listMenu.get(i).get("master_cd").toString();
				sSubCD = listMenu.get(i).get("sub_cd").toString();
				sMasterNameKey = listMenu.get(i).get("master_namekey").toString();
				ssubNameKey = listMenu.get(i).get("subname_key").toString();
				sb.append("	</ul>");
				sb.append("</li>");
				sb.append("<li class='treeview' id='menu_" + sMasterCD +"'>");
//				sb.append("	<a href='" + listMenu.get(i).get("sMasterURL").toString() + "'>");
				sb.append("	<a style='cursor:pointer' >");
				sb.append("		<i class='fa " + listMenu.get(i).get("master_icon").toString() + "'></i>");
				sb.append("		<span>" + LanguageHelper.GetLanguage(listMenu.get(i).get("master_namekey").toString())
						+ "</span>");
				sb.append("		<i class='fa fa-angle-left pull-right'></i></a>");
				sb.append("	<ul class='treeview-menu' name='ulMenu'>");
				sb.append("		<li class='active' id='menu_" + sSubCD +"'>");
//				sb.append("			<a href='" + listMenu.get(i).get("sSubUrl").toString() + "'>");
				sb.append("			<a onclick='changeframe(&quot;" + listMenu.get(i).get("sub_Url").toString() + "&quot;, &quot;" + sSubCD + "&quot;, &quot;" + sMasterNameKey + "&quot;, &quot;" + ssubNameKey + "&quot;);' style='cursor:pointer' >");
				sb.append("				<i class='fa fa-circle-o'></i>");
				sb.append(LanguageHelper.GetLanguage(listMenu.get(i).get("subname_key").toString()));
				sb.append("			</a></li>");
			}
		}
		sb.append("	</ul>");
		sb.append("</li>");
		System.out.println(sb.toString());
		model.addAttribute("menuHTML", sb.toString());
		// endregion 메뉴 데이터 생성

		return "/main";
	}

	// TEST
	@RequestMapping(value = "/mainTemplate", method = RequestMethod.GET)
	public String MainTemplate(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("mainTemplate Controller");
		return "mainTemplate";
	}

	// TEST
	@RequestMapping(value = "/longPollingSample", method = RequestMethod.GET)
	public void longPollingSample(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("longPollingSample Controller");

		// response.setContentType("text/event-time/n");
		response.setContentType("text/jsvascript/n");
		PrintWriter out = response.getWriter();
		while (true) {
			Date date = new Date();
			out.print("event : server-time \n");
			out.print("date : " + date.toString() + "\n\n");
			out.flush();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// TEST
	@RequestMapping(value = "/ajaxPollingTest", method = RequestMethod.GET)
	public String angularJSSample(ModelMap map) throws InterruptedException {

		return "ajaxPollingTest";
	}
}
