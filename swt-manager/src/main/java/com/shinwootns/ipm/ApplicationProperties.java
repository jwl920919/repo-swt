package com.shinwootns.ipm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;


@PropertySources({
	@PropertySource(value="file:application.properties", ignoreResourceNotFound=true),
	@PropertySource("classpath:application.properties")
})

@Component
@ConfigurationProperties(prefix = "ipm")
public class ApplicationProperties {
	
	//===================================
	// Name, Version
	//===================================
	@Value("${ipm.name}")
	private String name;
	@Value("${ipm.version}")
	private String version;


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
}
