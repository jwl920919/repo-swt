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

	//===================================
	// RabbitMQ
	//===================================
	@Value("${ipm.rabbitmq.host}")
	private String rabbitmqHost;
	
	@Value("${ipm.rabbitmq.port}")
	private int rabbitmqPort;
	
	@Value("${ipm.rabbitmq.username}")
	private String rabbitmqUsername;
	
	@Value("${ipm.rabbitmq.password}")
	private String rabbitmqPassword;
	
	@Value("${ipm.rabbitmq.vhost}")
	private String rabbitmqVhost;

	
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

	public String getRabbitmqHost() {
		return rabbitmqHost;
	}

	public void setRabbitmqHost(String rabbitmqHost) {
		this.rabbitmqHost = rabbitmqHost;
	}

	public int getRabbitmqPort() {
		return rabbitmqPort;
	}

	public void setRabbitmqPort(int rabbitmqPort) {
		this.rabbitmqPort = rabbitmqPort;
	}

	public String getRabbitmqUsername() {
		return rabbitmqUsername;
	}

	public void setRabbitmqUsername(String rabbitmqUsername) {
		this.rabbitmqUsername = rabbitmqUsername;
	}

	public String getRabbitmqPassword() {
		return rabbitmqPassword;
	}

	public void setRabbitmqPassword(String rabbitmqPassword) {
		this.rabbitmqPassword = rabbitmqPassword;
	}

	public String getRabbitmqVhost() {
		return rabbitmqVhost;
	}

	public void setRabbitmqVhost(String rabbitmqVhost) {
		this.rabbitmqVhost = rabbitmqVhost;
	}
}
