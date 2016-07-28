package test.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.common.utils.StringUtils;

public class TestHttp {

	private final Logger _logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testHttp() {
		
		Scanner sc = new Scanner(System.in);

		HttpClient restClient = new HttpClient();
		
		restClient.setEncoding("UTF-8");
		
		try {

			String baseURL = "https://192.168.1.11";
			String id = "admin";
			String pwd = "infoblox";

			String sTestMac = "10:41:7f:54:39:8f";
			String sFilterName = "BlackList";
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
							ref = getValueFromJsonArray(value, "_ref");
							//JsonUtils.getValueToString(value,  "_ref", "");
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
			
			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		restClient.Close();

		System.out.println("Terminated.");

	}

	private static String getValueFromJsonArray(String jsonText, String key) {
		
		String returnValue = "";

		JsonParser parser = new JsonParser();

		try {

			JsonElement ele = parser.parse(jsonText);

			if (ele != null && ele instanceof JsonArray) {
				JsonArray jsonArray = ele.getAsJsonArray();

				if (jsonArray.size() > 0) {
					JsonObject valueObj = jsonArray.get(0).getAsJsonObject();

					if (valueObj.has(key)) {
						returnValue = valueObj.get(key).getAsString();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = "";
		}

		return returnValue;
	}
}
