package MVC.ShinwooTNS.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import Common.DTO.AjaxResult;
import Common.Helper.CommonHelper;
import Common.Helper.ErrorLoggingHelper;
import Common.Helper.LanguageHelper;
import Common.ServiceInterface.UI_MENU_MASTER_Service_Interface;

@Controller
public class PageController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = Logger.getLogger(PageController.class);
	private Gson gson = new Gson();		
	private AjaxResult result = new AjaxResult();
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Initialize(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		//logger.info("Initialize : " + request.getLocalAddr());
		System.out.println("Initialize Controller");

		// 암복호화 테스트
		// String Encrypt = CryptoHelper.getEncrypt("신우티엔에스123!");
		// String Decrypt = CryptoHelper.getDecrypt(Encrypt);
		// System.out.println("Encrypt : " + Encrypt);
		// System.out.println("Decrypt : " + Decrypt);

		// region 다국어 Class Instance
		// 각자 다국어 설정을 체크 필요
		try {
			String strFileName = (new CommonHelper()).getCookies(request, "Language");
			if (strFileName != null) {
				LanguageHelper.getInstance(strFileName);
			} else {
				String sLanguageValue = LanguageHelper.getInstance(locale).getFileName();
				Cookie cookie = new Cookie("Language", sLanguageValue);
				cookie.setMaxAge(365 * 24 * 60 * 60); // 1년
				response.addCookie(cookie);
			}
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "Initialize Controller", e);
		}
		// endregion 다국어 Class Instance

		// region Login Session check
		//System.out.println(MessageFormat.format("Login Check : {0}", session.getAttribute("login_chk")));

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
		//logger.info("Main : " + request.getLocalAddr());
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

		List<HashMap<String, Object>> listMenu = new ArrayList<HashMap<String, Object>>();
		listMenu = memuMasterService.select_UI_MENU_MASTER();
		// listMenu = myappService.selectTestTable();

		// region 메뉴 데이터 생성
		try {			
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
					sb.append("		<span>" + LanguageHelper.GetLanguage(listMenu.get(i).get("master_namekey").toString()) + "</span>");
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
			
			String sMemu ="<li class='active treeview' id='menu_M01'><a onclick='changeframe(&quot;/dashboard/dashboard&quot;, &quot;M01&quot;, &quot;dashboard&quot;, &quot;systemState&quot;);' style='cursor:pointer' ><i class='fa fa-dashboard'></i><span>데시보드</span></a></li></ul></li><li class='treeview' id='menu_M02'><a style='cursor:pointer' ><i class='fa fa-laptop'></i><span>IP 관리</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;/ipManagement/staticIPStatus&quot;, &quot;S01&quot;, &quot;ipManagement&quot;, &quot;staticIPStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>IP 현황</a></li><li id='menu_S03'><a onclick='changeframe(&quot;/ipManagement/leaseIPStatus&quot;, &quot;S03&quot;, &quot;ipManagement&quot;, &quot;leaseIPStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>리스 IP 현황</a></li><li id='menu_S04'><a onclick='changeframe(&quot;/ipManagement/ipCertifyStatus&quot;, &quot;S04&quot;, &quot;ipManagement&quot;, &quot;ipCertifyStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>IP 신청/승인 현황</a></li><li id='menu_S05'><a onclick='changeframe(&quot;/ipManagement/unusedIPStatus&quot;, &quot;S05&quot;, &quot;ipManagement&quot;, &quot;unusedIPStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>미사용 IP 현황</a></li><li id='menu_S06'><a onclick='changeframe(&quot;#&quot;, &quot;S06&quot;, &quot;ipManagement&quot;, &quot;ipFilterStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>DHCP IP 필터 현황</a></li><li id='menu_S07'><a onclick='changeframe(&quot;/ipManagement/blackListStatus&quot;, &quot;S07&quot;, &quot;ipManagement&quot;, &quot;blackListStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>Black List 현황</a></li></ul></li><li class='treeview' id='menu_M04'><a style='cursor:pointer' ><i class='fa fa-book'></i><span>이력관리</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;#&quot;, &quot;S01&quot;, &quot;historyManagement&quot;, &quot;leaseHistorySearch&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>사용자별 리스 이력 조회</a></li><li id='menu_S02'><a onclick='changeframe(&quot;#&quot;, &quot;S02&quot;, &quot;historyManagement&quot;, &quot;dhcpEventSearch&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>이벤트별 DHCP 이력 조회</a></li><li id='menu_S03'><a onclick='changeframe(&quot;#&quot;, &quot;S03&quot;, &quot;historyManagement&quot;, &quot;ipAssignmentHistory&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>할당 IP 정보 조회</a></li><li id='menu_S04'><a onclick='changeframe(&quot;#&quot;, &quot;S04&quot;, &quot;historyManagement&quot;, &quot;eventLog&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>이벤트로그</a></li></ul></li><li class='treeview' id='menu_M05'><a style='cursor:pointer' ><i class='fa fa-balance-scale'></i><span>정책관리</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;/policyManagement/accessPolicy&quot;, &quot;S01&quot;, &quot;policyManagement&quot;, &quot;accessPolicy&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>제한 정책 설정</a></li><li id='menu_S02'><a onclick='changeframe(&quot;#&quot;, &quot;S02&quot;, &quot;policyManagement&quot;, &quot;userCertifySetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>사용자 인증 설정</a></li></ul></li><li class='treeview' id='menu_M06'><a style='cursor:pointer' ><i class='fa fa-server'></i><span>시스템관리</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;/systemManagement/infobloxStatus&quot;, &quot;S01&quot;, &quot;systemManagement&quot;, &quot;infobloxStatus&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>Infoblox현황정보</a></li><li id='menu_S02'><a onclick='changeframe(&quot;#&quot;, &quot;S02&quot;, &quot;systemManagement&quot;, &quot;dhcp/dnsSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>DHCP/DNS설정</a></li><li id='menu_S03'><a onclick='changeframe(&quot;/systemManagement/blackListSetting&quot;, &quot;S03&quot;, &quot;systemManagement&quot;, &quot;blackListSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>Black List 기능 설정</a></li></ul></li><li class='treeview' id='menu_M07'><a style='cursor:pointer' ><i class='fa fa-files-o'></i><span>리포트</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;#&quot;, &quot;S01&quot;, &quot;report&quot;, &quot;operatingReport&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>운영현황</a></li><li id='menu_S02'><a onclick='changeframe(&quot;#&quot;, &quot;S02&quot;, &quot;report&quot;, &quot;userCertifyReport&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>사용자 인증 정보</a></li><li id='menu_S03'><a onclick='changeframe(&quot;#&quot;, &quot;S03&quot;, &quot;report&quot;, &quot;useStatusReport&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>HW, OS, 서비스별 사용현황</a></li></ul></li><li class='treeview' id='menu_M08'><a style='cursor:pointer' ><i class='fa fa-cog'></i><span>환경설정</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;/configManagement/systemUserManagement&quot;, &quot;S01&quot;, &quot;configMangement&quot;, &quot;systemUserManagement&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>시스템 사용자 관리</a></li><li id='menu_S02'><a onclick='changeframe(&quot;/configManagement/systemGroupManagement&quot;, &quot;S02&quot;, &quot;configMangement&quot;, &quot;systemGroupManagement&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>시스템 그룹 관리</a></li><li id='menu_S03'><a onclick='changeframe(&quot;/configManagement/systemMenuAuthorityManagement&quot;, &quot;S03&quot;, &quot;configMangement&quot;, &quot;systemMenuAuthrity&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>메뉴권한 관리</a></li><li id='menu_S04'><a onclick='changeframe(&quot;#&quot;, &quot;S04&quot;, &quot;configMangement&quot;, &quot;dashboardSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>데시보드 설정</a></li><li id='menu_S05'><a onclick='changeframe(&quot;#&quot;, &quot;S05&quot;, &quot;configMangement&quot;, &quot;notificationSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>통보설정</a></li><li id='menu_S06'><a onclick='changeframe(&quot;#&quot;, &quot;S06&quot;, &quot;configMangement&quot;, &quot;approvalManagement&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>결재관리</a></li><li id='menu_S07'><a onclick='changeframe(&quot;#&quot;, &quot;S07&quot;, &quot;configMangement&quot;, &quot;systemBannerSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>시스템 베너 설정</a></li><li id='menu_S08'><a onclick='changeframe(&quot;#&quot;, &quot;S08&quot;, &quot;configMangement&quot;, &quot;systemLicenseSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>시스템 라이센스 설정</a></li><li id='menu_S09'><a onclick='changeframe(&quot;#&quot;, &quot;S09&quot;, &quot;configMangement&quot;, &quot;systemHASetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>시스템 이중화설정</a></li></ul></li><li class='treeview' id='menu_M09'><a style='cursor:pointer' ><i class='fa fa-server'></i><span>노드</span><i class='fa fa-angle-left pull-right'></i></a><ul class='treeview-menu' name='ulMenu'><li class='active' id='menu_S01'><a onclick='changeframe(&quot;/management/customGroupSetting&quot;, &quot;S01&quot;, &quot;node&quot;, &quot;customGroupSetting&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>노드그룹관리</a></li><li id='menu_S02'><a onclick='changeframe(&quot;/management/viewNodeInfo&quot;, &quot;S02&quot;, &quot;node&quot;, &quot;viewNodeInfo&quot;);' style='cursor:pointer' ><i class='fa fa-circle-o'></i>노드정보조회</a></li></ul></li>";
						
			model.addAttribute("menuHTML", sb.toString());
		} catch (Exception e) {
			ErrorLoggingHelper.log(logger, "main Menu Make", e);
		}
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
