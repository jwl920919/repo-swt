package com.shinwootns.ipm.insight.service.syslog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.data.regex.RegexPatterns;

public class SyslogHandlerEx {
	
	//region [public] processSyslog
	public DhcpMessage processSyslog(SyslogEntity syslog)
	{
		// DHCP Message
		Pattern dhcpPattern = Pattern.compile("dhcpd\\[\\d+\\]: (DHCP[A-Z]+) ");
		
		// Get DHCP Type
		Matcher match = dhcpPattern.matcher(syslog.getData());
		
		if (match.find() && match.groupCount() >= 1 ) {
			
			String dhcpType = match.group(1);
			int endPos = match.end();
			
			if ( dhcpType == null )
				return null;
			
			// DHCPDISCOVER
			if (dhcpType.equals("DHCPDISCOVER"))
				return _processDHCPDISCOVER(dhcpType, syslog, endPos);
			// DHCPOFFER
			else if (dhcpType.equals("DHCPOFFER"))
				return _processDHCPOFFER(dhcpType, syslog, endPos);
			// DHCPREQUEST
			else if (dhcpType.equals("DHCPREQUEST"))
				return _processDHCPREQUEST(dhcpType, syslog, endPos);
			// DHCPACK
			else if (dhcpType.equals("DHCPACK"))
				return _processDHCPACK(dhcpType, syslog, endPos);
			// DHCPNACK
			else if (dhcpType.equals("DHCPNACK"))
				return _processDHCPNACK(dhcpType, syslog, endPos);
			// DHCPINFORM
			else if (dhcpType.equals("DHCPINFORM"))
				return _processDHCPINFORM(dhcpType, syslog, endPos);
			// DHCPEXPIRE
			else if (dhcpType.equals("DHCPEXPIRE"))
				return _processDHCPEXPIRE(dhcpType, syslog, endPos);
			// DHCPRELEASE
			else if (dhcpType.equals("DHCPRELEASE"))
				return _processDHCPRELEASE(dhcpType, syslog, endPos);
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPDISCOVER
	private DhcpMessage _processDHCPDISCOVER(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPDISCOVER]
        // from 00:26:66:d1:69:69 via eth1 : network 192.168.1.0 / 24: no free leases
        // from 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 uid 01:00:19:99:e4:ea:7f
		
		Matcher match = RegexPatterns.DHCP_DISCOVER.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] from 90:00:db:b9:c6:31 (android-bb3528a07c47750a) via
				[1] 90:00:db:b9:c6:31
				[2] (android-bb3528a07c47750a)
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// Mac
				dhcp.setMac(match.group(1).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(2) != null)
					dhcp.setHostname(match.group(2).replace("(", "").replace(")", "").trim());
				
				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPOFFER
	private DhcpMessage _processDHCPOFFER(String dhcpType, SyslogEntity syslog, int startPos) {

		// [DHCPOFFER]
        // DHCPOFFER on 192.168.1.20 to 00:26:66:d1:69:69 via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:00:26:66:d1:69:69
        // DHCPOFFER on 192.168.1.12 to 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 relay eth1 lease-duration 10 uid 01:00:19:99:e4:ea:7f
        // DHCPOFFER on 192.168.1.13 to d0:7e:35:7e:93:1b (LDK-PC) via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:d0:7e:35:7e:93:1b
		
		Matcher match = RegexPatterns.DHCP_OFFER.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] for 192.168.1.12 from 6c:29:95:05:38:a4 (BJPARK) via eth1 uid 01:6c:29:95:05:38:a4 (RENEW)
				[1] 192.168.1.12
				[2] null
				[3] 6c:29:95:05:38:a4
				[4]  (BJPARK)
				[5]  via eth1 uid 01:6c:29:95:05:38:a4 
				[6] (RENEW)
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");

				// IP
				dhcp.setIp(match.group(1));
				
				// MAC
				dhcp.setMac(match.group(3).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(4) != null)
					dhcp.setHostname(match.group(4).replace("(", "").replace(")", "").trim());
				
				// RENEW
				if (match.group(6) != null && match.group(6).indexOf("RENEW") >= 0)
					dhcp.setRenew(true);

				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPREQUEST
	private DhcpMessage _processDHCPREQUEST(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPREQUEST]
        // DHCPREQUEST for 192.168.1.169 from 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 uid 01:20:55:31:89:89:ed(RENEW)
        // DHCPREQUEST for 192.168.1.114 (192.168.1.254) from 00:0c:29:8a:bb:a7 via eth1 : unknown lease 192.168.1.114.
        // DHCPREQUEST for 192.168.1.192 from 18:f6:43:24:06:4d via eth1 : unknown lease 192.168.1.192.
        // DHCPREQUEST for 192.168.1.101 from 98:83:89:14:4f:9e via eth1
		
		Matcher match = RegexPatterns.DHCP_REQUEST.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] for 192.168.1.12 from 6c:29:95:05:38:a4 (BJPARK) via eth1 uid 01:6c:29:95:05:38:a4 (RENEW)
				[1] 192.168.1.12
				[2] null
				[3] 6c:29:95:05:38:a4
				[4]  (BJPARK)
				[5]  via eth1 uid 01:6c:29:95:05:38:a4 
				[6] (RENEW)
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// IP
				dhcp.setIp(match.group(1));
				
				// MAC
				dhcp.setMac(match.group(3).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(4) != null)
					dhcp.setHostname(match.group(4).replace("(", "").replace(")", "").trim());
				
				// RENEW
				if (match.group(6) != null && match.group(6).indexOf("RENEW") >= 0)
					dhcp.setRenew(true);
				
				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPACK
	private DhcpMessage _processDHCPACK(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPACK]
        // on 192.168.1.19 to 30:52:cb:0c:f8:17 (JS) via eth1 relay eth1 lease-duration 10 (RENEW) uid 01:30:52:cb:0c:f8:17
        // on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW) uid 01:20:55:31:89:89:ed
		// to 192.168.1.115 (28:e3:47:4c:45:14) via eth1
        
        // #1. on [IP] ~ to [MAC] ~     // Inform-Ack
		Matcher match = RegexPatterns.DHCP_ACK1.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW)
				[1] 192.168.1.169
				[2] null
				[3] 20:55:31:89:89:ed
				[4]  (android - 11c2831a49d6d571)
				[5] eth1 relay eth1 
				[6] 43198
				[7] (RENEW)
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// IP
				dhcp.setIp(match.group(1));
				
				// MAC
				dhcp.setMac(match.group(3).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(4) != null)
					dhcp.setHostname(match.group(4).replace("(", "").replace(")", "").trim());
				
				// Duration
				if (match.group(6) != null) {
					Integer duration = Integer.parseInt(match.group(6));
					dhcp.setDuration(duration);
				}
				
				// RENEW
				if (match.group(7) != null && match.group(7).indexOf("RENEW") >= 0)
					dhcp.setRenew(true);
				
				return dhcp;
			}
		}
		else {
			
			// #2. to [IP] ~                // Request-Ack
			match = RegexPatterns.DHCP_ACK2.matcher(syslog.getData().substring(startPos));
			
			if (match.find()) {
				
				if (match.groupCount() > 0) {
					
					//for(int i=0; i<=match.groupCount(); i++)
					//	System.out.println(String.format("[%d] %s", i, match.group(i)));
					
					/*
					[0] on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW)
					[1] 192.168.1.169
					[2] 20:55:31:89:89:ed
					*/
					
					DhcpMessage dhcp = new DhcpMessage();
					dhcp.setDhcpType(dhcpType);
					dhcp.setIpType("IPV4");
					
					// IP
					dhcp.setIp(match.group(1));
					
					// MAC
					dhcp.setMac(match.group(2).replace("(", "").replace(")", ""));
					
					return dhcp;
				}
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPNACK
	private DhcpMessage _processDHCPNACK(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPNAK]
        // on 192.168.1.103 to d0:7e:35:7e:93:1b via eth1
		
		Matcher match = RegexPatterns.DHCP_NACK.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] on 192.168.1.103 to d0:7e:35:7e:93:1b via eth1
				[1] 192.168.1.103
				[2] null
				[3] d0:7e:35:7e:93:1b
				[4] null
				[5] eth1
				[6] null
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// IP
				dhcp.setIp(match.group(1));
				
				// MAC
				dhcp.setMac(match.group(3).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(4) != null)
					dhcp.setHostname(match.group(4).replace("(", "").replace(")", "").trim());
				
				// RENEW
				if (match.group(6) != null && match.group(6).indexOf("RENEW") >= 0)
					dhcp.setRenew(true);
				
				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPINFORM
	private DhcpMessage _processDHCPINFORM(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPINFORM]
        // from 192.168.1.115 via eth1
		
		Matcher match = RegexPatterns.DHCP_INFORM.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] from 192.168.1.19 via
				[1] 192.168.1.19
				[2] null
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// IP
				dhcp.setIp(match.group(1));
				
				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPEXPIRE
	private DhcpMessage _processDHCPEXPIRE(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPEXPIRE]
        // on 192.168.1.19 to 30:52:cb:0c:f8:17
		
		Matcher match = RegexPatterns.DHCP_EXPIRE.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] on 192.168.1.19 to 30:52:cb:0c:f8:17
				[1] 192.168.1.19
				[2] null
				[3] 30:52:cb:0c:f8:17
				[4] null
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// IP
				dhcp.setIp(match.group(1));
				
				// MAC
				dhcp.setMac(match.group(3).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(4) != null)
					dhcp.setHostname(match.group(4).replace("(", "").replace(")", "").trim());
				
				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
	//region [private] process DHCPRELEASE
	private DhcpMessage _processDHCPRELEASE(String dhcpType, SyslogEntity syslog, int startPos) {
		
		// [DHCPRELEASE]
        // of 192.168.1.101 from 98:83:89:14:4f:9e (JS) via eth1 (found)uid 01:98:83:89:14:4f:9e
		
		Matcher match = RegexPatterns.DHCP_RELEASE.matcher(syslog.getData().substring(startPos));
		
		if (match.find()) {
			
			if (match.groupCount() > 0) {
				
				//for(int i=0; i<=match.groupCount(); i++)
				//	System.out.println(String.format("[%d] %s", i, match.group(i)));
				
				/*
				[0] of 192.168.1.101 from 98:83:89:14:4f:9e (JS) via
				[1] 192.168.1.101
				[2] null
				[3] 98:83:89:14:4f:9e
				[4]  (JS)
				*/
				
				DhcpMessage dhcp = new DhcpMessage();
				dhcp.setDhcpType(dhcpType);
				dhcp.setIpType("IPV4");
				
				// IP
				dhcp.setIp(match.group(1));
				
				// MAC
				dhcp.setMac(match.group(3).replace("(", "").replace(")", ""));
				
				// HostName
				if (match.group(4) != null)
					dhcp.setHostname(match.group(4).replace("(", "").replace(")", "").trim());
				
				return dhcp;
			}
		}
		
		return null;
	}
	//endregion
	
}
