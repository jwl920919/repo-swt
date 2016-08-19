package com.shinwootns.data.regex;

import java.util.regex.Pattern;

public class RegexPatterns {

	// [DHCPDISCOVER]
    // from 00:26:66:d1:69:69 via eth1 : network 192.168.1.0 / 24: no free leases
    // from 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 uid 01:00:19:99:e4:ea:7f
	public final static Pattern PATTERN_DHCPDISCOVER = Pattern.compile( 
			(new StringBuilder())
			.append("from (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)\\s?(\\([A-Za-z0-9\\_\\-\\s]+\\))? via")
			.toString()
	);

	// [DHCPOFFER]
	// DHCPOFFER on 192.168.1.20 to 00:26:66:d1:69:69 via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:00:26:66:d1:69:69
    // DHCPOFFER on 192.168.1.12 to 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 relay eth1 lease-duration 10 uid 01:00:19:99:e4:ea:7f
    // DHCPOFFER on 192.168.1.13 to d0:7e:35:7e:93:1b (LDK-PC) via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:d0:7e:35:7e:93:1b
	public final static Pattern PATTERN_DHCPOFFER = Pattern.compile(
			(new StringBuilder())
			.append("on (\\d+.\\d+.\\d+.\\d+)\\s?(\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" to (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?")
			.append(" via ([A-Za-z0-9\\s:]+)?(\\(RENEW\\))?")
			.toString()
	);
	

	// [DHCPREQUEST]
	// DHCPREQUEST for 192.168.1.169 from 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 uid 01:20:55:31:89:89:ed(RENEW)
    // DHCPREQUEST for 192.168.1.114 (192.168.1.254) from 00:0c:29:8a:bb:a7 via eth1 : unknown lease 192.168.1.114.
    // DHCPREQUEST for 192.168.1.192 from 18:f6:43:24:06:4d via eth1 : unknown lease 192.168.1.192.
    // DHCPREQUEST for 192.168.1.101 from 98:83:89:14:4f:9e via eth1
	public final static Pattern PATTERN_DHCPREQUEST = Pattern.compile(
			(new StringBuilder())
			.append("for (\\d+.\\d+.\\d+.\\d+)\\s?(\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" from (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?")
			.append("([A-Za-z0-9\\s:]+)(\\(RENEW\\))?")
			.toString()
	);
	
	
	// [DHCPACK] #1
    // on 192.168.1.19 to 30:52:cb:0c:f8:17 (JS) via eth1 relay eth1 lease-duration 10 (RENEW) uid 01:30:52:cb:0c:f8:17
    // on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW) uid 01:20:55:31:89:89:ed
	public final static Pattern PATTERN_DHCPACK1 = Pattern.compile(
			(new StringBuilder())
			.append("on (\\d+.\\d+.\\d+.\\d+)(\\s\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" to (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?")
			.append(" via ([A-Za-z0-9\\s:]+)")
			.append("lease\\s?-\\s?duration (\\d+)")
			.append("\\s?(\\(RENEW\\))?")
			.toString()
	);
	
	// [DHCPACK] #2
	// to 192.168.1.115 (28:e3:47:4c:45:14) via eth1
	public final static Pattern PATTERN_DHCPACK2 = Pattern.compile(
			(new StringBuilder())
			.append("to (\\d+.\\d+.\\d+.\\d+) (\\(\\w+:\\w+:\\w+:\\w+:\\w+:\\w+\\))")
			.append(" via")
			.toString()
	);
	
	// [DHCPNAK]
    // on 192.168.1.103 to d0:7e:35:7e:93:1b via eth1
	public final static Pattern PATTERN_DHCPNACK = Pattern.compile(
			(new StringBuilder())
			.append("on (\\d+.\\d+.\\d+.\\d+)\\s?(\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" to (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?")
			.append(" via ([A-Za-z0-9\\s:]+)")
			.append("\\s?(\\(RENEW\\))?")
			.toString()
	);
	
	// [DHCPINFORM]
    // from 192.168.1.115 via eth1
	public final static Pattern PATTERN_DHCPINFORM = Pattern.compile(
			(new StringBuilder())
			.append("from (\\d+.\\d+.\\d+.\\d+)\\s?(\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" via")
			.toString()
	);
	

	// [DHCPEXPIRE]
    // on 192.168.1.19 to 30:52:cb:0c:f8:17
	public final static Pattern PATTERN_DHCPEXPIRE = Pattern.compile(
			(new StringBuilder())
			.append("on (\\d+.\\d+.\\d+.\\d+)\\s?(\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" to (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?")
			.toString()
	);
	
	// [DHCPRELEASE]
	// of 192.168.1.101 from 98:83:89:14:4f:9e (JS) via eth1 (found)uid 01:98:83:89:14:4f:9e
	public final static Pattern PATTERN_DHCPRELEASE = Pattern.compile(
			(new StringBuilder())
			.append("of (\\d+.\\d+.\\d+.\\d+)\\s?(\\([A-Za-z0-9\\_\\-\\s\\.]+\\))?")
			.append(" from (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?")
			.append(" via")
			.toString()
	);
}
