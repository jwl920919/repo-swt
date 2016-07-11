package com.shinwootns.ipm.collector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations={"application.yml"}, prefix="ipm")
public class ApplicationProperty {
	
	@Value("${ipm.name}")
	private String name;
	
	@Value("${ipm.version}")
	private String version;
	
	@Value("${ipm.license}")
	private String license;
	
	@Value("${ipm.debug_enable:false}")
	private boolean debug_enable;
	
	@Value("${ipm.debug.insert_syslog_enable:true}")
	private boolean insert_syslog_enable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public boolean isDebug_enable() {
		return debug_enable;
	}

	public void setDebug_enable(boolean debug_enable) {
		this.debug_enable = debug_enable;
	}

	public boolean isInsert_syslog_enable() {
		if (this.debug_enable == false)
			return true;
		
		return insert_syslog_enable;
	}

	public void setInsert_syslog_enable(boolean insert_syslog_enable) {
		this.insert_syslog_enable = insert_syslog_enable;
	}
}
