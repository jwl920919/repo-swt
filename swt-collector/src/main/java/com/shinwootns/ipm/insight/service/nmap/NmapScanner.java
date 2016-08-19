package com.shinwootns.ipm.insight.service.nmap;

import java.util.regex.Matcher;

import org.apache.commons.exec.CommandLine;

import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.common.utils.SystemUtils.CommandOutput;
import com.shinwootns.data.entity.NmapScanIP;
import com.shinwootns.data.regex.RegexPatterns;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;

public class NmapScanner implements Runnable {

	private NmapScanIP ip;
	
	public NmapScanner(NmapScanIP ip) {
		this.ip = ip;
	}
	
	@Override
	public void run() {

		if (ip.getIpType().toUpperCase().equals("IPV4"))
		{
			ScanIPv4();
		}
		else if (ip.getIpType().toUpperCase().equals("IPV6"))
		{
			ScanIPv6();
		}
	}
	
	private void ScanIPv4() {
		// Command
		StringBuilder command = new StringBuilder();
		command.append("nmap \" -O -v ").append(ip.getIpaddr()).append("\"");
		
		// Execute Command
		CommandLine cmd = new CommandLine(command.toString());
		CommandOutput result = SystemUtils.executeCommand(cmd);
		
		if (result.output != null) {
			
			Matcher match = RegexPatterns.NMAP_FINGERPRINT.matcher(result.output);
			
			if (match.find()) {
				
				if (match.groupCount() > 0) {
					
					//for(int t=0; t<=match.groupCount(); t++)
					//	System.out.println(String.format("[%d] %s", t, match.group(t)));
					
					NmapScanIP newip = new NmapScanIP();
					newip.setSiteId( this.ip.getSiteId() );
					newip.setNetwork( this.ip.getNetwork() );
					newip.setIpaddr( this.ip.getIpaddr() );
					newip.setIpNum( this.ip.getIpNum() );
					newip.setIpType(this.ip.getIpType() );
					
					// MAC
					newip.setNmapMacaddr( match.group(1) );
					
					// Vendor
					String vendor = match.group(2);
					if (vendor != null) {
						newip.setNmapVendor( vendor.replace("(", "").replace(")", "").trim() );
					}
					
					// OS
					newip.setNmapOs( match.group(4) );
					
					// Update to DB
					UpdateToDB(newip);
				}
			}
		}
	}
	
	private void ScanIPv6() {
		
		// ...
	}
	
	private void UpdateToDB(NmapScanIP newip) {
		
		if ( newip.getNmapMacaddr() != this.ip.getNmapMacaddr() || 
				newip.getNmapVendor() != this.ip.getNmapVendor() ||
				newip.getNmapOs() != this.ip.getNmapOs())
		{
			// Update when changed
			
			DhcpMapper dhcpMapper = SpringBeanProvider.getInstance().getDhcpMapper();
			if (dhcpMapper != null) {
				dhcpMapper.updateNmapScanIP(newip);
			}
		}
			
		
	}
}
