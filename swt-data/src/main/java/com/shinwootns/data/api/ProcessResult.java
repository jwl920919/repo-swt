package com.shinwootns.data.api;

public class ProcessResult {
	
	private Boolean isErrorOccurred = null;
	private Boolean result = null;
	private String message;
	
	@Override
	public String toString() {
		return (new StringBuilder())
			.append(", result=").append(result)
			.append( (isErrorOccurred == true) ? ", isErrorOccurred="+isErrorOccurred : "")
			.append(", message=").append(message)
			.toString();
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean isErrorOccurred() {
		return isErrorOccurred;
	}
	public void setErrorOccurred(Boolean isErrorOccurred) {
		this.isErrorOccurred = isErrorOccurred;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
}
