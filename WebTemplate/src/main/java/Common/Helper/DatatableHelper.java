package Common.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class DatatableHelper {
	/**
	 * columns = 테이블 상단의 컬럼들을 순서대로 입력, startCoulumnsIndex = ex) 테이블 처음 컬럼이
	 * 체크박스라면 처리가 필요없기 때문에 1
	 */
	public static HashMap<String, Object> getDatatableParametas(HttpServletRequest request, String[] columns,
			int startCoulumnsIndex) {
		try {
			String orderColumn = columns[Integer.parseInt(request.getParameter("order[0][column]"))
					- startCoulumnsIndex], orderType = request.getParameter("order[0][dir]"),
					searchValue = request.getParameter("search[value]");
			int startIndex = Integer.parseInt(request.getParameter("start")),
					length = Integer.parseInt(request.getParameter("length"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("searchValue", searchValue);
			parameters.put("orderColumn", orderColumn);
			parameters.put("orderType", orderType);
			parameters.put("startIndex", startIndex);
			parameters.put("length", length);
			return parameters;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <E> String makeCallback(HttpServletRequest request, E data, int total) {
		try {
			StringBuffer sb = new StringBuffer("");
			sb.append(request.getParameter("callback"));
			sb.append("({");
			sb.append("\"draw\": ");
			sb.append(request.getParameter("draw"));
			sb.append(',');
			sb.append("\"recordsFiltered\": ");
			sb.append(total);
			sb.append(',');
			sb.append("\"recordsTotal\": ");
			sb.append(total);
			sb.append(',');
			sb.append("\"data\": ");
			sb.append(data);
			sb.append(',');
			sb.append("})");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static <E> String makeCallback(HttpServletRequest request, E data, int total, List<Map<String,Object>> cds) {
		try {
			StringBuffer sb = new StringBuffer("");
			sb.append(request.getParameter("callback"));
			sb.append("({");
			sb.append("\"draw\": ");
			sb.append(request.getParameter("draw"));
			sb.append(',');
			sb.append("\"recordsFiltered\": ");
			sb.append(total);
			sb.append(',');
			sb.append("\"recordsTotal\": ");
			sb.append(total);
			sb.append(',');
			sb.append("\"data\": ");
			sb.append(data);
			sb.append(',');
			for(Map<String,Object> cd : cds) {
				sb.append('\"');
				sb.append(cd.get("key"));
				sb.append("\": ");
				sb.append(cd.get("value"));
				sb.append(',');
			}
			sb.append("})");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
