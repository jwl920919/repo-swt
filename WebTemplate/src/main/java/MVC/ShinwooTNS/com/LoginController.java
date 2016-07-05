package MVC.ShinwooTNS.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Common.Helper.DBHelper;
import Common.Helper.LanguageHelper;

@Controller
public class LoginController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String Login(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Login : " + request.getLocalAddr());
		System.out.println("Login Controller");

		HttpSession session = request.getSession(true);
		DBHelper dbHelper = new DBHelper();

		try {
			String id = request.getParameter("txtID");
			String pw = request.getParameter("txtPassword");
			System.out.println("Login ID : " + id);
			System.out.println("Login PW : " + pw);
			
			if (id != null && pw != null && !id.isEmpty() && !pw.isEmpty()) {
				String sql = "SELECT * FROM \"system_user_info\" where \"user_id\" = '" + id + "';";
				System.out.println("Query : " + sql);
				ResultSet rs = dbHelper.executeQuery(sql);
				
				rs.last();
				System.out.println("count : " + rs.getRow());
				
				if (rs.getRow() > 0) {
					rs.beforeFirst();
					
					boolean bSignCheck = false;
					while (rs.next()) {
						System.out.println("db Password : " + rs.getString("user_pw"));
						System.out.println("UR Password : " + pw);
						System.out.println("UR sDeptName : " + rs.getString("dept_name").trim());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
						String date = sdf.format(rs.getDate("insert_date"));
						System.out.println("user_info dateTime : " + date.toString());
						
						if (rs.getString("user_pw").trim().equals(pw)) {
							session.setAttribute("login_chk", true);// Session에 "login_chk"로 값을 저장.
							session.setAttribute("site_id", rs.getString("site_id").trim());// Session에 "login_chk"로 값을 저장.
							session.setAttribute("user_seq", rs.getString("user_seq").trim());// Session에 "login_chk"로 값을 저장.
							session.setAttribute("user_id", rs.getString("user_id").trim());// Session에 "login_chk"로 값을 저장.
							session.setAttribute("user_name", rs.getString("user_name").trim());// Session에 "login_chk"로 값을 저장.							
							bSignCheck = true;
						}
					}
					if (!bSignCheck) {
						//비밀번호가 일치하지 않습니다.
						System.out.println("비밀번호가 일치하지 않습니다.");
						model.addAttribute("errorMessage", LanguageHelper.GetLanguage("Passwordsdonotmatch"));
						model.addAttribute("txtID", id);
						return "/loginManagement/login";
					}
				} else {
					//일치하는 아이디가 없습니다.
					System.out.println("일치하는 아이디가 없습니다.");
					model.addAttribute("errorMessage", LanguageHelper.GetLanguage("IDdonotmatch"));
					model.addAttribute("txtID", "");
					return "/loginManagement/login";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.close();
		}

		System.out.println("login_chk : " + session.getAttribute("login_chk"));
		if (session.getAttribute("login_chk") == null)
			return "/loginManagement/login";
		else
			return "redirect:/main";
	}

	@RequestMapping(value = "/signOut", method = { RequestMethod.GET, RequestMethod.POST })
	public String SignOut(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("SignOut : " + request.getLocalAddr());
		System.out.println("SignOut Controller");

		HttpSession session = request.getSession(true);
		session.removeAttribute("login_chk");// Session에 "login_chk"로 값 삭제.
		session.removeAttribute("site_id");// Session에 "login_chk"로 값 삭제.
		session.removeAttribute("user_seq");// Session에 "login_chk"로 값 삭제.
		session.removeAttribute("user_id");// Session에 "login_chk"로 값 삭제.
		session.removeAttribute("user_name");// Session에 "login_chk"로 값 삭제.	
		
		return "redirect:/";
	}
}
