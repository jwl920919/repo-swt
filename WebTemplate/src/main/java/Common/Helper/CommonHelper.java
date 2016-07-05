package Common.Helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CommonHelper {
	public String getCookies(HttpServletRequest request, String key_cd) {
		Cookie[] cookies = request.getCookies();
		String sRet = null;

		try {
			if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) { // 쿠키 배열을 반복문으로 돌린다.
					 System.out.println(i + "번째 쿠키 이름 : " + cookies[i].getName()); // 쿠키의 이름을 가져온다.
					 System.out.println(i + "번째 쿠키에 설정된 값 : " + cookies[i].getValue()); // 쿠키의 값을 가져온다.
					 System.out.println(i + "번째 키 이름 : " + key_cd); // 쿠키의 이름을 가져온다.
					 System.out.println(i + "번째 쿠키와 비교 결과 : " + cookies[i].getName().toString().equals(key_cd));
					 
					if (cookies[i].getName().toString().equals(key_cd)) {
						sRet = cookies[i].getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("CommonHelper.getCookies Error : " + e.getMessage());
		}

		return sRet;
	}
	
	private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static String convertTimestampToString(Timestamp timeStamp) {
		return SIMPLE_DATE_FORMAT.format(timeStamp);
	}
}
