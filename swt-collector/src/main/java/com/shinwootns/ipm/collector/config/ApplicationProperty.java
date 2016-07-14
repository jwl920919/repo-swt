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
	
	@Value("${ipm.debug_enable:false}")
	public boolean debugEnable;
	
	@Value("${ipm.debug.insert_syslog_enable:true}")
	public boolean insert_syslog_enable;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "\n====================================================\n" );
		sb.append( String.format("%-30s = %s\n", "ipm.name", this.name ) );
		sb.append( String.format("%-30s = %s\n", "ipm.version", this.version ) );
		sb.append( String.format("%-30s = %s\n", "ipm.license", this.license ) );
		
		if (debugEnable) {
			sb.append( String.format("%-30s = %s\n", "ipm.debug.enable", (this.debugEnable)? "true":"false" ) );
			sb.append( String.format("%-30s = %s\n", "ipm.debug.insert_syslog_enable", (this.insert_syslog_enable)? "true":"false" ) );
		}
		sb.append( "====================================================\n" );
		
		return sb.toString();
	}
}
