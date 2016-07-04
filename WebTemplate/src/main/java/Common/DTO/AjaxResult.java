package Common.DTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AjaxResult {

	/**
	 * <p>
	 * Ajax 리턴의 결과
	 * </p>
	 * 
	 * @return boolean
	 **/
	public boolean result;

	/**
	 * <p>
	 * Ajax 리턴 코드 값
	 * </p>
	 * 
	 * @return int
	 **/
	public int code;

	/**
	 * <p>
	 * Ajax 리턴 description
	 * </p>
	 * 
	 * @return String
	 **/
	public String description;	

	/**
	 * <p>
	 * Ajax 결과 값
	 * </p>
	 * 
	 * @return Object
	 **/
	public Object resultValue;

	/**
	 * <p>
	 * Ajax 결과 값
	 * </p>
	 * 
	 * @return List Array
	 **/
	@JsonProperty("data")
	public List<Map<String, Object>> data = null;
	//new List<LinkedHashMap<String, Object>>();
	
	protected boolean isResult() {
		return result;
	}
	protected void setResult(boolean result) {
		this.result = result;
	}
	protected int getCode() {
		return code;
	}
	protected void setCode(int code) {
		this.code = code;
	}
	protected String getDescription() {
		return description;
	}
	protected void setDescription(String description) {
		this.description = description;
	}
	protected Object getResultValue() {
		return resultValue;
	}
	protected void setResultValue(Object resultValue) {
		this.resultValue = resultValue;
	}
	protected List<Map<String, Object>> getData() {
		return data;
	}
	protected void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	


}
