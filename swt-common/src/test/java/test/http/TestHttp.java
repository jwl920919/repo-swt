package test.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.entity.ContentType;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.StringUtils;

public class TestHttp {

	private static final Logger logger = Logger.getLogger(TestHttp.class);

	@Test
	public static void testHttpClient() {
		
		BasicConfigurator.configure();

		Scanner sc = new Scanner(System.in);

		HttpClient restClient = new HttpClient(logger);
		
		restClient.setEncoding("UTF-8");
		
		try {

			String baseURL = "https://192.168.1.11";
			String id = "admin";
			String pwd = "infoblox";

			String sTestMac = "10:41:7f:54:39:8f";
			String sFilterName = "ipt_test";
			String sUserName = "전상수-테스트";

			// Connect
			if (restClient.Connect_Https(baseURL, id, pwd)) {

				System.out.println("Connection OK. (" + baseURL + ")");

				String value;
				Map params = new HashMap<String, String>();

				while (true) {
					System.out.println("\n[Command List] : network, filter, get, put, delete, exit\n");
					System.out.print(">>");

					String command = sc.nextLine().toLowerCase();

					if (command.equals("network")) {
						// Get Network List
						System.out.println("[GET] Network Info");

						params.clear();
						params.put("_return_type", "json-pretty");

						value = restClient.Get("/wapi/v1.0/network", params);
						
						value = StringUtils.unescapeUnicodeString(value);
						
						if (value != null)
							System.out.println(value);
					} else if (command.equals("filter")) {
						// Get Filter Info
						System.out.println("[GET] Filter Info");

						params.clear();
						params.put("_return_type", "json-pretty");

						value = restClient.Get("/wapi/v2.3/filtermac", params);
						
						value = StringUtils.unescapeUnicodeString(value);

						if (value != null)
							System.out.println(value);
					} else if (command.equals("get")) {
						// Get MAC Filter
						System.out.println("[GET] MAC Filter");

						params.clear();
						params.put("mac", sTestMac);
						params.put("_return_fields", "filter,mac,username");
						params.put("_return_type", "json-pretty");

						value = restClient.Get("/wapi/v1.0/macfilteraddress", params);
						
						value = StringUtils.unescapeUnicodeString(value);

						if (value != null)
							System.out.println(value);
					} else if (command.equals("put")) {
						// ==================================================
						// #4. Insert MAC Filter
						System.out.println("[PUT] Insert MAC Filter");

						params.clear();
						params.put("mac", sTestMac);
						params.put("filter", sFilterName);
						params.put("username", sUserName);
						params.put("_return_type", "json-pretty");

						value = restClient.Post("/wapi/v1.0/macfilteraddress", ContentType.APPLICATION_JSON, params);
						
						value = StringUtils.unescapeUnicodeString(value);

						if (value != null)
							System.out.println(value);
					} else if (command.equals("delete")) {
						// Get MAC Filter
						System.out.println("[DELETE] MAC Filter");

						params.clear();
						params.put("mac", sTestMac);
						params.put("_return_fields", "filter,mac,username");
						params.put("_return_type", "json-pretty");

						value = restClient.Get("/wapi/v1.0/macfilteraddress", params);
						
						value = StringUtils.unescapeUnicodeString(value);

						// Get MAC Filter URL
						String ref = "";
						if (value != null) {
							ref = getValueFromJSONArray(value, "_ref");
						}

						// Delete MAC Filter
						if (ref != null && ref.length() > 0) {
							String deleteURL = String.format("/wapi/v1.0/%s", ref);

							value = restClient.Delete(deleteURL, null, null);
							
							value = StringUtils.unescapeUnicodeString(value);

							if (value != null)
								System.out.println(value);
						}
					} else if (command.equals("exit")) {
						System.out.println("Exited.");
						break;
					}
				}

			} else {
				System.out.println("Connection failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		restClient.Close();

		System.out.println("Terminated.");

	}

	private static String getValueFromJSONArray(String jsonText, String key) {
		String returnValue = "";

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(jsonText);

			if (obj != null && obj instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) obj;

				if (jsonArray.size() > 0) {
					JSONObject jobj = (JSONObject) jsonArray.get(0);

					if (jobj.containsKey(key)) {
						returnValue = (String) jobj.get(key);
					}
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
			returnValue = "";
		}

		return returnValue;
	}
}
