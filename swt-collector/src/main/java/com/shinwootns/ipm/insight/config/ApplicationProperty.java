package com.shinwootns.ipm.insight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations={"application.yml"}, prefix="insight")
public class ApplicationProperty {

	@Value("${insight.site-code}")
	public String siteCode;
	
	@Value("${insight.local_ip}")
	public String local_ip;
	
	@Value("${server.port}")
	public int serverPort;
	
	@Value("${server.session.timeout}")
	public int sessionTimeout;
	
	@Value("${insight.name}")
	public String name;
	
	@Value("${insight.version}")
	public String version;
	
	@Value("${insight.license}")
	public String license;
	
	@Value("${insight.security.user}")
	public String security_user;
	
	@Value("${insight.security.password}")
	public String security_password;
	
	// Cluster info
	@Value("${insight.cluster.mode}")
	public String clusterMode;
	
	@Value("${insight.cluster.index:99}")
	public int clusterIndex;
	
	//{{ Debug Mode
	@Value("${insight.debug_enable:false}")
	public boolean debugEnable;
	
	@Value("${insight.debug.enable_recv_syslog:true}")
	public boolean enable_recv_syslog;
	
	@Value("${insight.debug.force_start_cluster_master:false}")
	public boolean force_start_cluster_master;
	//}} Debug Mode
	
	// Redis
	@Value("${insight.redis.host:127.0.0.1}")
	public String redisHost;
	
	@Value("${insight.redis.port:6379}")
	public int redisPort;
	
	@Value("${insight.redis.password}")
	public String redisPassword;

	@Value("${insight.redis.timeout:0}")
	public int redisTimeout;
	
	// Rabbitmq
	@Value("${insight.rabbitmq.host:127.0.0.1}")
	public String rabbitmqHost;
	
	@Value("${insight.rabbitmq.port:5672}")
    public int rabbitmqPort;
    
	@Value("${insight.rabbitmq.username}")
    public String rabbitmqUsername;
	
	@Value("${insight.rabbitmq.password}")
	public String rabbitmqPassword;
	
	@Value("${insight.rabbitmq.virtual-host:/}")
    public String rabbitmqVHost;
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "\n------------------------------------------------------------\n" );
		sb.append( String.format("%-30s = %s\n", "insight.site-code", this.siteCode ) );
		sb.append( String.format("%-30s = %s\n", "insight.local_ip", this.local_ip ) );
		
		sb.append( String.format("%-30s = %s\n", "server.port", this.serverPort ) );
		sb.append( String.format("%-30s = %s\n", "server.session.timeout", this.sessionTimeout ) );
		
		sb.append( String.format("%-30s = %s\n", "insight.name", this.name ) );
		sb.append( String.format("%-30s = %s\n", "insight.version", this.version ) );
		sb.append( String.format("%-30s = %s\n", "insight.license", this.license ) );
		
		sb.append( String.format("%-30s = %s\n", "insight.redis.host", this.redisHost ) );
		sb.append( String.format("%-30s = %s\n", "insight.redis.port", this.redisPort ) );
		sb.append( String.format("%-30s = %s\n", "insight.redis.timeout", this.redisTimeout ) );
		
		sb.append( String.format("%-30s = %s\n", "insight.rabbitmq.host", this.rabbitmqHost ) );
		sb.append( String.format("%-30s = %s\n", "insight.rabbitmq.port", this.rabbitmqPort ) );
		sb.append( String.format("%-30s = %s\n", "insight.rabbitmq.virtual-host", this.rabbitmqVHost ) );
		
		if (debugEnable) {
			sb.append( "------------------------------------------------------------\n" );
			sb.append( String.format("%-30s = %s\n", "insight.debug.enable", (this.debugEnable)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "insight.debug.enable_recv_syslog", (this.enable_recv_syslog)? "true":"false" ) );
		}
		sb.append( "------------------------------------------------------------" );
		
		return sb.toString();
	}
}
