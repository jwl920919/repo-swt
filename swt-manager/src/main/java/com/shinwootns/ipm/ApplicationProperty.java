package com.shinwootns.ipm;

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
	
	// Debug Mode
	@Value("${ipm.debug_enable:false}")
	private boolean debug_enable;
	
	@Value("${ipm.debug_recv_syslog_enable:true}")
	private boolean recv_syslog_enable;
	
	@Value("${ipm.debug_insert_event_enable:true}")
	private boolean insert_event_enable;

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

	public boolean isRecv_syslog_enable() {
		if (this.debug_enable == false)
			return true;
		
		return recv_syslog_enable;
	}

	public void setRecv_syslog_enable(boolean recv_syslog_enable) {
		this.recv_syslog_enable = recv_syslog_enable;
	}

	public boolean isInsert_event_enable() {
		if (this.debug_enable == false)
			return true;
		return insert_event_enable;
	}

	public void setInsert_event_enable(boolean insert_event_enable) {
		this.insert_event_enable = insert_event_enable;
	}
}