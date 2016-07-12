package com.shinwootns.ipm;

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
	
	// Cluster info
	@Value("${ipm.cluster.mode}")
	public String clusterMode;
	
	@Value("${ipm.cluster.slave-index:99}")
	public int clusterSalveIndex;
	
	//{{ Debug Mode
	@Value("${ipm.debug.enable:false}")
	public boolean debugEnable;
	
	@Value("${ipm.debug.enable_recv_syslog:true}")
	public boolean enableRecvSyslog;
	
	@Value("${ipm.debug.enable_insert_event:true}")
	public boolean enableInsertEvent;
	//}} Debug Mode
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "====================================================\n" );
		sb.append( String.format("%-30s = %s\n", "ipm.name", this.name ) );
		sb.append( String.format("%-30s = %s\n", "ipm.version", this.version ) );
		sb.append( String.format("%-30s = %s\n", "ipm.license", this.license ) );
		sb.append( String.format("%-30s = %s\n", "ipm.cluster.mode", this.clusterMode ) );
		sb.append( String.format("%-30s = %s\n", "ipm.cluster.slave-index", this.clusterSalveIndex ) );
		
		if (debugEnable) {
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable", (this.debugEnable)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable_recv_syslog", (this.enableRecvSyslog)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable_insert_event", (this.enableInsertEvent)? "true":"false" ) );
		}
		sb.append( "====================================================\n" );
		
		return sb.toString();
	}
}