package com.shinwootns.ipm.controller;

public class HelpContent {
	
	private String typeName;
	private String exec;
	private String url;
	private String params;
	private String desc;
	
	public HelpContent(String typeName, String url, String exec, String params, String desc) {
		this.setTypeName(typeName);
		this.setExec(exec);
		this.setUrl(url);
		this.setParams(params);
		this.setDesc(desc);
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getExec() {
		return exec;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}
}
