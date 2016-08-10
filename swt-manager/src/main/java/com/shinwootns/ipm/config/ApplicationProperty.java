package com.shinwootns.ipm.config;

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
	
	@Value("${ipm.security.user}")
	public String security_user;
	
	@Value("${ipm.security.password}")
	public String security_password;
	
	// Cluster info
	@Value("${ipm.cluster.mode}")
	public String clusterMode;
	
	@Value("${ipm.cluster.slave-index:99}")
	public int clusterSalveIndex;
	
	//{{ Debug Mode
	@Value("${insight.debug.force_start_cluster_master:false}")
	public boolean force_start_cluster_master;
	
	@Value("${ipm.debug.enable:false}")
	public boolean debugEnable;
	
	@Value("${ipm.debug.enable_recv_syslog:true}")
	public boolean enableRecvSyslog;
	
	@Value("${ipm.debug.enable_insert_event:true}")
	public boolean enableInsertEvent;
	
	@Value("${ipm.debug.enable_device_collect:true}")
	public boolean enableDeviceCollect;
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
		
		sb.append( "\n------------------------------------------------------------\n" );
		sb.append( String.format("%-30s = %s\n", "ipm.name", this.name ) );
		sb.append( String.format("%-30s = %s\n", "ipm.version", this.version ) );
		sb.append( String.format("%-30s = %s\n", "ipm.license", this.license ) );
		sb.append( String.format("%-30s = %s\n", "ipm.cluster.mode", this.clusterMode ) );
		sb.append( String.format("%-30s = %s\n", "ipm.cluster.slave-index", this.clusterSalveIndex ) );
		
		sb.append( String.format("%-30s = %s\n", "ipm.redis.host", this.redisHost ) );
		sb.append( String.format("%-30s = %s\n", "ipm.redis.port", this.redisPort ) );
		sb.append( String.format("%-30s = %s\n", "ipm.redis.timeout", this.redisTimeout ) );
		
		sb.append( String.format("%-30s = %s\n", "ipm.rabbitmq.host", this.rabbitmqHost ) );
		sb.append( String.format("%-30s = %s\n", "ipm.rabbitmq.port", this.rabbitmqPort ) );
		sb.append( String.format("%-30s = %s\n", "ipm.rabbitmq.virtual-host", this.rabbitmqVHost ) );
		
		
		if (debugEnable) {
			sb.append( "------------------------------------------------------------\n" );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable", (this.debugEnable)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable_recv_syslog", (this.enableRecvSyslog)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable_insert_event", (this.enableInsertEvent)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable_device_collect", (this.enableDeviceCollect)? "true":"false" ) );
		}
		sb.append( "------------------------------------------------------------" );
		
		return sb.toString();
	}
}