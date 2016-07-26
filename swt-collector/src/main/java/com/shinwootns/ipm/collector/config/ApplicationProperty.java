package com.shinwootns.ipm.collector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations={"application.yml"}, prefix="ipm")
public class ApplicationProperty {
	
	@Value("${ipm.name}")
	public String name;
	
	@Value("${ipm.version}")
	public String version;
	
	@Value("${ipm.license}")
	public String license;
	
	//{{ Debug Mode
	@Value("${ipm.debug_enable:false}")
	public boolean debugEnable;
	
	@Value("${ipm.debug.enable_recv_syslog:true}")
	public boolean enable_recv_syslog;
	//}} Debug Mode
	
	// Redis
	@Value("${ipm.redis.host:127.0.0.1}")
	public String redisHost;
	
	@Value("${ipm.redis.port:6379}")
	public int redisPort;
	
	@Value("${ipm.redis.password}")
	public String redisPassword;

	@Value("${ipm.redis.timeout:0}")
	public int redisTimeout;
	
	// Rabbitmq
	@Value("${ipm.rabbitmq.host:127.0.0.1}")
	public String rabbitmqHost;
	
	@Value("${ipm.rabbitmq.port:5672}")
    public int rabbitmqPort;
    
	@Value("${ipm.rabbitmq.username}")
    public String rabbitmqUsername;
	
	@Value("${ipm.rabbitmq.password}")
	public String rabbitmqPassword;
	
	@Value("${ipm.rabbitmq.virtual-host:/}")
    public String rabbitmqVHost;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "\n====================================================\n" );
		sb.append( String.format("%-30s = %s\n", "ipm.name", this.name ) );
		sb.append( String.format("%-30s = %s\n", "ipm.version", this.version ) );
		sb.append( String.format("%-30s = %s\n", "ipm.license", this.license ) );
		
		sb.append( String.format("%-30s = %s\n", "ipm.redis.host", this.redisHost ) );
		sb.append( String.format("%-30s = %s\n", "ipm.redis.port", this.redisPort ) );
		sb.append( String.format("%-30s = %s\n", "ipm.redis.timeout", this.redisTimeout ) );
		
		sb.append( String.format("%-30s = %s\n", "ipm.rabbitmq.host", this.rabbitmqHost ) );
		sb.append( String.format("%-30s = %s\n", "ipm.rabbitmq.port", this.rabbitmqPort ) );
		sb.append( String.format("%-30s = %s\n", "ipm.rabbitmq.virtual-host", this.rabbitmqVHost ) );
		
		if (debugEnable) {
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable", (this.debugEnable)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable_recv_syslog", (this.enable_recv_syslog)? "true":"false" ) );
		}
		sb.append( "====================================================\n" );
		
		return sb.toString();
	}
}
