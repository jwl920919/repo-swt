package com.shinwootns.ipm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.mysql.fabric.xmlrpc.base.Data;
import com.shinwootns.common.network.SyslogEntity;
import com.shinwootns.common.redis.RedisHandler;
import com.shinwootns.common.utils.TimeUtils;
import com.shinwootns.ipm.insight.service.syslog.DhcpMessage;
import com.shinwootns.ipm.insight.service.syslog.SyslogHandlerEx;

import redis.clients.jedis.Jedis;

public class TestSyslog {

	/*
	@Test
	public void testDHCParsing() {
		
		List<String> listSyslog = new LinkedList<String>();
		listSyslog.add("<30>Jul 25 16:36:53 192.168.1.13 dhcpd[23974]: DHCPDISCOVER from 90:00:db:b9:c6:31 (android-bb3528a07c47750a) via eth2 uid 01:90:00:db:b9:c6:31 ration 86400 uid 01:64:e5:99:a3:cf:f8 ting conflicts if you connect to the VPN server from public locations such (...)");
		listSyslog.add("<30>Jul 25 16:36:53 192.168.1.13 dhcpd[23974]: DHCPDISCOVER from 90:00:db:b9:c6:31 (android-bb3528a07c47750a) via eth2 uid 01:90:00:db:b9:c6:31 ration 86400 uid 01:64:e5:99:a3:cf:f8 ting conflicts if you connect to the VPN server from public locations such (...)");
		listSyslog.add("<30>Feb 24 17:38:33 192.168.1.11 dhcpd[22231]: DHCPREQUEST for 192.168.1.12 from 6c:29:95:05:38:a4 (BJPARK) via eth1 uid 01:6c:29:95:05:38:a4 (RENEW)");
		listSyslog.add("<30>Jul  5 23:01:55 192.168.1.11 dhcpd[32700]: DHCPREQUEST for 192.168.1.187 from 18:53:e0:05:9f:bd via eth1 uid 01:18:53:e0:05:9f:bd (RENEW) duration 86400 uid 01:64:e5:99:a3:cf:f8 8:eb:d6 :7b 0:f4:6f:9b:65:7b");
		listSyslog.add("<30>Jul  6 04:06:11 192.168.1.11 dhcpd[32700]: DHCPREQUEST for 192.168.1.150 from 00:11:a9:d8:ed:70 (SIPPhone0011A9D8ED70) via eth1 uid 01:00:11:a9:d8:ed:70 (RENEW) 4:e5:99:a3:cf:f8 d ec:9a :7b 0:f4:6f:9b:65:7b");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPREQUEST for 192.168.1.208 from 00:f4:6f:9b:65:7b (android-3a048ccb529df727) via eth1 uid 01:00:f4:6f:9b:65:7b (RENEW) 192.168.1.100-192.168.1.220 4d 85:af:ed up does not include Splunk App Data.  Redump re (...)");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPREQUEST for 192.168.1.169 from 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 uid 01:20:55:31:89:89:ed(RENEW)");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPREQUEST for 192.168.1.114 (192.168.1.254) from 00:0c:29:8a:bb:a7 via eth1 : unknown lease 192.168.1.114.");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPREQUEST for 192.168.1.192 from 18:f6:43:24:06:4d via eth1 : unknown lease 192.168.1.192.");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPREQUEST for 192.168.1.101 from 98:83:89:14:4f:9e via eth1");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPOFFER on 192.168.1.20 to 00:26:66:d1:69:69 via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:00:26:66:d1:69:69");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPOFFER on 192.168.1.12 to 00:19:99:e4:ea:7f (ClickShare-ShinwooTNS-C1) via eth1 relay eth1 lease-duration 10 uid 01:00:19:99:e4:ea:7f");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPOFFER on 192.168.1.13 to d0:7e:35:7e:93:1b (LDK-PC) via eth1 relay eth1 lease-duration 120 offered-duration 10 uid 01:d0:7e:35:7e:93:1b");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPACK on 192.168.1.19 to 30:52:cb:0c:f8:17 (JS) via eth1 relay eth1 lease-duration 10 (RENEW) uid 01:30:52:cb:0c:f8:17");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPACK on 192.168.1.169 to 20:55:31:89:89:ed (android - 11c2831a49d6d571) via eth1 relay eth1 lease - duration 43198(RENEW) uid 01:20:55:31:89:89:ed");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPACK to 192.168.1.115 (28:e3:47:4c:45:14) via eth1");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPNACK on 192.168.1.103 to d0:7e:35:7e:93:1b via eth1");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPINFORM from 192.168.1.19 via eth1");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPEXPIRE on 192.168.1.19 to 30:52:cb:0c:f8:17");
		listSyslog.add("<30>Jul 25 12:57:40 192.168.1.11 dhcpd[32397]: DHCPRELEASE of 192.168.1.101 from 98:83:89:14:4f:9e (JS) via eth1 (found)uid 01:98:83:89:14:4f:9e");
		
		SyslogHandlerEx handler = new SyslogHandlerEx();
		
		for(String log : listSyslog) {
			
			System.out.println("");
			System.out.println(log);
			
			SyslogEntity syslog = new SyslogEntity();
			syslog.setData(log);
			
			DhcpMessage dhcp = handler.processSyslog(syslog);
			
			if (dhcp != null)
				System.out.println("==> " + dhcp);
			else
				System.out.println("==> [FAILED!!!!!!] : " + syslog);
		}
	}
	*/
	
	/*
	@Test
	public void testSyslogRemoveDuplication() {
		
		RedisManager rm = new RedisManager("192.168.1.81", 6379, "shinwoo123!", 1);
		
		if ( rm.connect() )
		{
			RedisClient redis = rm.createRedisClient();
			Jedis jedis = redis.getJedis();
			
			if (redis != null && jedis != null) {

				//redis.flushDB();
				
				long time;
				
				long nPrevSec = -1;
				for(int i=0; i < 60; i++) {

					time = TimeUtils.currentTimeSecs();
					
					//LocalTime date = LocalTime.now();
					
					// Add Data
					jedis.zadd("syslog", time, "IP1-AAAAA-"+time);
					jedis.zadd("syslog", time, "IP1-AAAAA-"+time);
					jedis.zadd("syslog", time, "IP2-BBBBB-"+time);
					jedis.zadd("syslog", time, "IP2-BBBBB-"+time);
					jedis.zadd("syslog", time, "IP3-CCCCC-"+time);
					jedis.zadd("syslog", time, "IP3-CCCCC-"+time);
					
					// Pop Data
					if (nPrevSec != time % 10)
					{
						nPrevSec = time % 10;
								
						Set<String> data = jedis.zrangeByScore("syslog", time-2, time-2);
						System.out.println(time + " ==> " + data);

						// Delete
						long delCount = jedis.zremrangeByScore("syslog", -1, time-2);
						
						System.out.println("Delete count:"+delCount);
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			//redis.flushDB();
		}
		
		rm.close();
	}
	*/
}
