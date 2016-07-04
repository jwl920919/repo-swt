package Common.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONHelper {

	// region Instance
	private static JSONHelper instance = new JSONHelper();

	private JSONHelper() {
	}

	public static JSONHelper getInstance() {
		return instance;
	}
	// endregion

	// JSONObject를 Map<String, Object>로 변환해서 반환하는 메서드
	public static Map<String, Object> JSONObjecttoMap(JSONObject object) throws JarException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keySet().iterator();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = JSONObjecttoList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = JSONObjecttoMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	// JSONObject를 List<Object>로 변환해서 반환하는 메서드
	public static List<Object> JSONObjecttoList(JSONArray array) throws JarException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = JSONObjecttoList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = JSONObjecttoMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

}
