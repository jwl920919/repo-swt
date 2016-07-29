package MVC.ShinwooTNS.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Common.DTO.AjaxResult;
import Common.Helper.CommonHelper;

@Controller
@RequestMapping(value = "/systemManagement/")
public class SystemManagementActionController {
	private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(SystemManagementActionController.class);
	private Gson gson = new Gson();
	private AjaxResult result = new AjaxResult();

	// redisTemplate를 쓰기위해 Autowired 해준다.
	@Autowired
	StringRedisTemplate redisTemplate;

	// region getInfobloxdatas
	/** 4가지 데이터를 모두 redis에서 수집하여 Get Method에 응답<br/>
	 * 한가지라도 수집에 실패하면 result 값에 false을 추가
	 * */
	@RequestMapping(value = "getInfobloxdatas", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public @ResponseBody Object getInfobloxdatas(HttpServletRequest request, HttpSession session) {
		logger.info("getInfobloxdatas : " + request.getLocalAddr());
		init();
		try {
			// json 형식의 데이터를 String 타입으로 받아온다.
			List<Map<String,Object>> obj = new ArrayList<>(); 
			
			//redis에 메시지를 보내 JSON 형식의 String 데이터를 받아온다.
			String json = redisTemplate.opsForValue()
					.get("status:dhcp:device_status:" + session.getAttribute("site_id").toString());
			if (json != null) {
				JSONParser jParser = new JSONParser();
				// json 형식의 String 데이터를 JSONObject로 Parser를 이용하여 Parsing한다.
				JSONObject hwInfo = (JSONObject) jParser.parse(json);
				Calendar calendar = Calendar.getInstance();
				JSONArray licensesInfo = (JSONArray) hwInfo.get("licenses");
				Iterator iterator = licensesInfo.iterator();
				while (iterator.hasNext()) {
					JSONObject jObj = (JSONObject) iterator.next();
					//라이센스 남은 기간 측정
					long expiryDate = CommonHelper.objectToLong(jObj.get("expiry_date")) * 1000;
					long now = calendar.getTimeInMillis();
					long gap = (expiryDate - now) / 1000;
					long gap_day = gap / (24 * 60 * 60);
					long gap_hour = (gap - gap_day * 24 * 60 * 60) / (60 * 60);
					long gap_minute = (gap - gap_day * 24 * 60 * 60 - gap_hour * 60 * 60) / 60;
					long gap_second = (gap - gap_day * 24 * 60 * 60 - gap_hour * 60 * 60 - gap_minute * 60);
					HashMap<String, Object> data = new HashMap<>();
					jObj.put("now", now);
					jObj.put("gap_day", gap_day);
					jObj.put("gap_hour", gap_hour);
					jObj.put("gap_minute", gap_minute);
					jObj.put("gap_second", gap_second);
					if (gap > 0) {
						jObj.put("result", true);
					} else {
						jObj.put("result", false);
					}
				}
				obj.add(hwInfo);
			} else {
				result.result = false;
				return gson.toJson(result);
			}
			json = redisTemplate.opsForValue()
					.get("status:dhcp:vrrp_status:" + session.getAttribute("site_id").toString());
			if (json != null) {
				JSONParser jParser = new JSONParser();
				JSONObject redundancyStatus = (JSONObject) jParser.parse(json);
				obj.add(redundancyStatus);
			} else {
				result.result = false;
				return gson.toJson(result);
			}
			json = redisTemplate.opsForValue()
					.get("status:dhcp:dhcp_counter:" + session.getAttribute("site_id").toString());
			if (json != null) {
				JSONParser jParser = new JSONParser();
				JSONObject dhcpMessagesInfo = (JSONObject) jParser.parse(json);
				StringBuffer sb = new StringBuffer("");
				// json main
				sb.append("{ \"dhcp_msg_info\" : [");
				// label, data, color를 지정
				sb.append("{ \"label\":\"DISCOVERS\",\"data\":");
				sb.append(dhcpMessagesInfo.get("discovers"));
				sb.append(",\"color\": \"#00a65a\"},{ \"label\":\"OFFERS\",\"data\":");
				sb.append(dhcpMessagesInfo.get("offers"));
				sb.append(",\"color\": \"#f39c12\"},{ \"label\":\"REQUESTS\",\"data\":");
				sb.append(dhcpMessagesInfo.get("requests"));
				sb.append(",\"color\": \"#39cccc\"},{ \"label\":\"ACKS\",\"data\":");
				sb.append(dhcpMessagesInfo.get("acks"));
				sb.append(",\"color\": \"#dd4b39\"},{ \"label\":\"NACKS\",\"data\":");
				sb.append(dhcpMessagesInfo.get("nacks"));
				sb.append(",\"color\": \"#00c0ef\"},{ \"label\":\"DECLINES\",\"data\":");
				sb.append(dhcpMessagesInfo.get("declines"));
				sb.append(",\"color\": \"#605ca8\"},{ \"label\":\"INFORMS\",\"data\":");
				sb.append(dhcpMessagesInfo.get("informs"));
				sb.append(",\"color\": \"#d81b60\"},{ \"label\":\"RELEASES\",\"data\":");
				sb.append(dhcpMessagesInfo.get("releases"));
				sb.append(",\"color\": \"#001f3f\"}");
				// json end main
				sb.append("],\"collect_time\":");
				sb.append(dhcpMessagesInfo.get("collect_time"));
				sb.append("}");
				
				obj.add((JSONObject) jParser.parse(sb.toString()));
			} else {
				result.result = false;
				return gson.toJson(result);
			}
			json = redisTemplate.opsForValue()
					.get("status:dhcp:dns_counter:" + session.getAttribute("site_id").toString());
			if (json != null) {
				JSONParser jParser = new JSONParser();
				JSONObject dnsMessagesInfo = (JSONObject) jParser.parse(json);
				StringBuffer sb = new StringBuffer("");
				// json main
				sb.append("{ \"dns_msg_info\" : [");
				// label, data, color를 지정
				sb.append("{ \"label\":\"SUCCESS\",\"data\":");
				sb.append(dnsMessagesInfo.get("success"));
				sb.append(",\"color\": \"#00a65a\"},{ \"label\":\"REFERRAL\",\"data\":");
				sb.append(dnsMessagesInfo.get("referral"));
				sb.append(",\"color\": \"#f39c12\"},{ \"label\":\"NXRRSET\",\"data\":");
				sb.append(dnsMessagesInfo.get("nxrrset"));
				sb.append(",\"color\": \"#39cccc\"},{ \"label\":\"NXDOMAIN\",\"data\":");
				sb.append(dnsMessagesInfo.get("nxdomain"));
				sb.append(",\"color\": \"#dd4b39\"},{ \"label\":\"RECURSION\",\"data\":");
				sb.append(dnsMessagesInfo.get("recursion"));
				sb.append(",\"color\": \"#00c0ef\"},{ \"label\":\"FAILURE\",\"data\":");
				sb.append(dnsMessagesInfo.get("failure"));
				sb.append(",\"color\": \"#605ca8\"}");
				// json end main
				sb.append("],\"collect_time\":");
				sb.append(dnsMessagesInfo.get("collect_time"));
				sb.append("}");
				obj.add((JSONObject) jParser.parse(sb.toString()));
			} else {
				result.result = false;
				return gson.toJson(result);
			}
			
			result.data = obj;
			result.result = true;

			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = false;
			return gson.toJson(result);
		}
	}
	// endregion
	
	/** 
	 * result 객체에 data, resultValue를 모두 담아서 전송하는 경우는 상관이 없으나
	 * 한가지만 보낼 경우 이전 페이지 또는 이전에 사용하였던 데이터가 남아서 같이
	 * 넘어가는 경우가 발생하여 init() 메소드를 항상 상단에서 사용하여 object clear
	 * 과정을 거치고 처리
	*/
	private void init() {
		result.data = null;
		result.resultValue = null;
	}
	
	
	// // region getHwInfo
	// @RequestMapping(value = "getHwInfo", method = RequestMethod.GET, produces
	// = "application/text; charset=utf8")
	// public @ResponseBody Object getHwInfo(HttpServletRequest request,
	// HttpSession session) {
	// logger.info("getHwInfo : " + request.getLocalAddr());
	// init();
	// try {
	// //json 형식의 데이터를 String 타입으로 받아온다.
	// String json =
	// redisTemplate.opsForValue().get("status:dhcp:device_status:"+session.getAttribute("site_id").toString());
	// if (json != null) {
	// JSONParser jParser = new JSONParser();
	// //json 형식의 String 데이터를 JSONObject로 Parser를 이용하여 Parsing한다.
	// JSONObject hwInfo = (JSONObject) jParser.parse(json);
	// Calendar calendar = Calendar.getInstance();
	// JSONArray licensesInfo = (JSONArray) hwInfo.get("licenses");
	// Iterator iterator = licensesInfo.iterator();
	// while (iterator.hasNext()) {
	// JSONObject jObj = (JSONObject) iterator.next();
	// long expiryDate = CommonHelper.objectToLong(jObj.get("expiry_date")) *
	// 1000;
	// long now = calendar.getTimeInMillis();
	// long gap = (expiryDate - now) / 1000;
	// long gap_day = gap / (24 * 60 * 60);
	// long gap_hour = (gap - gap_day * 24 * 60 * 60) / (60 * 60);
	// long gap_minute = (gap - gap_day * 24 * 60 * 60 - gap_hour * 60 * 60) /
	// 60;
	// long gap_second = (gap - gap_day * 24 * 60 * 60 - gap_hour * 60 * 60 -
	// gap_minute * 60);
	// HashMap<String, Object> data = new HashMap<>();
	// jObj.put("now", now);
	// jObj.put("gap_day", gap_day);
	// jObj.put("gap_hour", gap_hour);
	// jObj.put("gap_minute", gap_minute);
	// jObj.put("gap_second", gap_second);
	// if (gap > 0) {
	// jObj.put("result", true);
	// } else {
	// jObj.put("result", false);
	// }
	// }
	// result.resultValue = hwInfo;
	// }
	//
	// result.result = true;
	//
	// return gson.toJson(result);
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.result = false;
	// return gson.toJson(result);
	// }
	// }
	// // endregion

	// region getRedundancyStatus
	// @RequestMapping(value = "getRedundancyStatus", method =
	// RequestMethod.GET, produces = "application/text; charset=utf8")
	// public @ResponseBody Object getRedundancyStatus(HttpServletRequest
	// request, HttpSession session) {
	// logger.info("getRedundancyStatus : " + request.getLocalAddr());
	// init();
	// try {
	// String json =
	// redisTemplate.opsForValue().get("status:dhcp:vrrp_status:"+session.getAttribute("site_id").toString());
	// if (json != null) {
	// JSONParser jParser = new JSONParser();
	// JSONObject redundancyStatus = (JSONObject) jParser.parse(json);
	// result.resultValue = redundancyStatus;
	// }
	// result.result = true;
	//
	// return gson.toJson(result);
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.result = false;
	// return gson.toJson(result);
	// }
	// }
	// endregion

	// region getDhcpMessagesInfo
	// @RequestMapping(value = "getDhcpMessagesInfo", method =
	// RequestMethod.GET, produces = "application/text; charset=utf8")
	// public @ResponseBody Object getDhcpMessagesInfo(HttpServletRequest
	// request, HttpSession session) {
	// logger.info("getDhcpMessagesInfo : " + request.getLocalAddr());
	// init();
	// try {
	// String json =
	// redisTemplate.opsForValue().get("status:dhcp:dhcp_counter:"+session.getAttribute("site_id").toString());
	// if (json != null) {
	// JSONParser jParser = new JSONParser();
	// JSONObject dhcpMessagesInfo = (JSONObject) jParser.parse(json);
	// StringBuffer sb = new StringBuffer("");
	// // json main
	// sb.append("{ \"dhcp_msg_info\" : [");
	// // json sub
	// sb.append("{ \"label\":\"DISCOVERS\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("discovers"));
	// sb.append(",\"color\": \"#00a65a\"},{ \"label\":\"OFFERS\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("offers"));
	// sb.append(",\"color\": \"#f39c12\"},{ \"label\":\"REQUESTS\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("requests"));
	// sb.append(",\"color\": \"#39cccc\"},{ \"label\":\"ACKS\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("acks"));
	// sb.append(",\"color\": \"#dd4b39\"},{ \"label\":\"NACKS\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("nacks"));
	// sb.append(",\"color\": \"#00c0ef\"},{ \"label\":\"DECLINES\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("declines"));
	// sb.append(",\"color\": \"#605ca8\"},{ \"label\":\"INFORMS\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("informs"));
	// sb.append(",\"color\": \"#d81b60\"},{ \"label\":\"RELEASES\",\"data\":");
	// sb.append(dhcpMessagesInfo.get("releases"));
	// sb.append(",\"color\": \"#001f3f\"}");
	// // json end main
	// sb.append("],\"collect_time\":");
	// sb.append(dhcpMessagesInfo.get("collect_time"));
	// sb.append("}");
	// result.resultValue = (JSONObject)jParser.parse(sb.toString());
	// }
	//
	// result.result = true;
	//
	// return gson.toJson(result);
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.result = false;
	// return gson.toJson(result);
	// }
	// }
	// endregion

	// region getDhcpMessagesInfo
	// @RequestMapping(value = "getDnsMessagesInfo", method = RequestMethod.GET,
	// produces = "application/text; charset=utf8")
	// public @ResponseBody Object getDnsMessagesInfo(HttpServletRequest
	// request, HttpSession session) {
	// logger.info("getDnsMessagesInfo : " + request.getLocalAddr());
	// init();
	// try {
	// String json =
	// redisTemplate.opsForValue().get("status:dhcp:dns_counter:"+session.getAttribute("site_id").toString());
	// if (json != null) {
	// JSONParser jParser = new JSONParser();
	// JSONObject dnsMessagesInfo = (JSONObject) jParser.parse(json);
	// StringBuffer sb = new StringBuffer("");
	// // json main
	// sb.append("{ \"dns_msg_info\" : [");
	// // json sub
	// sb.append("{ \"label\":\"SUCCESS\",\"data\":");
	// sb.append(dnsMessagesInfo.get("success"));
	// sb.append(",\"color\": \"#00a65a\"},{ \"label\":\"REFERRAL\",\"data\":");
	// sb.append(dnsMessagesInfo.get("referral"));
	// sb.append(",\"color\": \"#f39c12\"},{ \"label\":\"NXRRSET\",\"data\":");
	// sb.append(dnsMessagesInfo.get("nxrrset"));
	// sb.append(",\"color\": \"#39cccc\"},{ \"label\":\"NXDOMAIN\",\"data\":");
	// sb.append(dnsMessagesInfo.get("nxdomain"));
	// sb.append(",\"color\": \"#dd4b39\"},{
	// \"label\":\"RECURSION\",\"data\":");
	// sb.append(dnsMessagesInfo.get("recursion"));
	// sb.append(",\"color\": \"#00c0ef\"},{ \"label\":\"FAILURE\",\"data\":");
	// sb.append(dnsMessagesInfo.get("failure"));
	// sb.append(",\"color\": \"#605ca8\"}");
	// // json end main
	// sb.append("],\"collect_time\":");
	// sb.append(dnsMessagesInfo.get("collect_time"));
	// sb.append("}");
	// result.resultValue = (JSONObject)jParser.parse(sb.toString());
	// }
	//
	// result.result = true;
	//
	// return gson.toJson(result);
	// } catch (Exception e) {
	// e.printStackTrace();
	// result.result = false;
	// return gson.toJson(result);
	// }
	// }
	//
	// endregion
	
	
}
