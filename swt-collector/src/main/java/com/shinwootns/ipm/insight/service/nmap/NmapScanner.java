package com.shinwootns.ipm.insight.service.nmap;

import java.util.regex.Matcher;

import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.utils.SystemUtils;
import com.shinwootns.common.utils.SystemUtils.CommandOutput;
import com.shinwootns.data.entity.NmapScanIP;
import com.shinwootns.data.regex.RegexPatterns;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.mapper.DhcpMapper;

public class NmapScanner implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
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
	
	//region Scan IPv4
	private void ScanIPv4() {

		CommandLine cmd = new CommandLine("nmap");
		cmd.addArgument("-O");
		cmd.addArgument("-v");
		cmd.addArgument(ip.getIpaddr().toString(), false);
		
		// Execute Command
		CommandOutput result = SystemUtils.executeCommand(cmd);
		
		if (result.output != null) {

			// Find Mac & Vendor
			NmapScanIP newip = _regex_MAC(result);
			
			if (newip != null) {
				
				// Find OS
				if ( _regex_OS_detail(result, newip) == false ) {
					_regex_OS_guesses(result, newip);
				}
				
				// Update to DB
				UpdateToDB(newip);
			}
			else {
				//_logger.info(String.format("[NAMP-DEBUG] %s - not found info.\n%s", ip.getIpaddr(), result.output));
			}
		}
		else {
			_logger.info(String.format("[NAMP] %s - Failed to execute nmap (output is null).", ip.getIpaddr()));
		}
	}
	//endregion
	
	//region Scan IPv6 (TO-DO)
	private void ScanIPv6() {
		
		// ...
	}
	//endregion
	
	//region - Extract MAC 
	private NmapScanIP _regex_MAC(CommandOutput result) {
		
		Matcher match = RegexPatterns.NMAP_FINGERPRINT_MAC.matcher(result.output);
		
		if (match.find() && match.groupCount() > 0) {
			
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
			
			return newip;
		}
		
		return null;
	}
	//endregion
	
	//region - Extract OS (Detail)
	private boolean _regex_OS_detail(CommandOutput result, NmapScanIP newip) {
		
		Matcher match2 = RegexPatterns.NMAP_FINGERPRINT_OS_DETAIL.matcher(result.output);
		if (match2.find() && match2.groupCount() > 0 ) {
			
			//for(int t=0; t<=match2.groupCount(); t++)
			//	System.out.println(String.format("[%d] %s", t, match2.group(t)));
				
			newip.setNmapOs( match2.group(1) );
				
			return true; 
		}
		
		return false;
	}
	//endregion
	
	//region - Extract OS (Guesses)
	private boolean _regex_OS_guesses(CommandOutput result, NmapScanIP newip) {
		
		Matcher match3 = RegexPatterns.NMAP_FINGERPRINT_OS_GUESS.matcher(result.output);
		
		if (match3.find() && match3.groupCount() > 0) {

			//for(int t=0; t<=match3.groupCount(); t++)
			//	System.out.println(String.format("[%d] %s", t, match3.group(t)));
			
			String[] listOS = match3.group(1).split(",");
			
			if (listOS.length > 0)
			{
				StringBuilder sb = new StringBuilder();
				
				int maxCount = 5;

				for(int t1=0; t1<=listOS.length && t1 < maxCount; t1++) {
					
					if (sb.length() > 0)
						sb.append("/");
					
					sb.append(listOS[t1].replaceAll("\\(\\d+%\\)", "").trim());
				}
				
				newip.setNmapOs(sb.toString());
				
				return true;
			}
		}
		
		return false;
	}
	//endregion
	
	//region Update to DB
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
	//endregion
}
