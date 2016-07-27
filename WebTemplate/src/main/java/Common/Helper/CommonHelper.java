package Common.Helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CommonHelper {

	/**
	 * <p>
	 * 설정되어 있는 쿠키 값을 반환한다.
	 * </p>
	 * 
	 * @param request,key_cd
	 * @return String 설정 값
	 **/
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
	/**
	 * <p>
	 * Timestamp 데이터를 "yyyy-MM-dd hh:mm:ss" 포멧으로 변환한다.
	 * </p>
	 * 
	 * @param Timestamp
	 * @return String "yyyy-MM-dd hh:mm:ss"
	 **/
	public static String convertTimestampToString(Timestamp timeStamp) {
		return SIMPLE_DATE_FORMAT.format(timeStamp);
	}
	
	/**
	 * <p>
	 * ipv4를 long 값으로 반환한다.
	 * </p>
	 * 
	 * @param String
	 * @return long
	 **/
	public static long IPv4ToLong(String ipAddress) { 
		long result = 0; 
		String[] ipArray = ipAddress.split("\\."); 
		if (ipArray.length == 4) { 
			for (int i = 3; i >= 0; i--) { 
				long ip = Long.parseLong(ipArray[3 - i]); 
				// left shifting 24,16,8,0 and bitwise OR 
	
	
				// 1. 192 << 24 
				// 1. 168 << 16 
				// 1. 1 << 8 
				// 1. 2 << 0 
				result |= ip << (i * 8); 
			} 
		} 
		return result; 
	} 

	
	/**
	 * <p>
	 * long 값을 ipv4로 반환한다.
	 * </p>
	 * 
	 * @param long
	 * @return String
	 **/
	public static String longToIPv4(long ipNumber) { 
		StringBuilder sb = new StringBuilder(); 	 
		sb.append((ipNumber >> 24) & 0xFF); 
		sb.append("."); 
		sb.append((ipNumber >> 16) & 0xFF); 
		sb.append("."); 
		sb.append((ipNumber >> 8) & 0xFF); 
		sb.append("."); 
		sb.append((ipNumber & 0xFF)); 
		return sb.toString(); 
	} 
	
	/**
	 * <p>
	 * object 값을 long으로 반환한다.
	 * 처리할 수 없는 에러 발생시 Exception을 발생한다.
	 * </p>
	 * 
	 * @param Object
	 * @return long
	 * @throws Exception
	 **/
	public static long objectToLong(Object value) throws Exception {
		
		if (value instanceof Double || value instanceof Float) {
			return Long.parseLong(String.format("%.0f", value));
		}
		else if (value instanceof Integer) {
			return (long)value;
		}
		
		return Long.parseLong(value.toString());
	}
}












