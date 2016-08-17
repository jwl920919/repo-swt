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
import com.shinwootns.common.redis.RedisHandler;
import com.shinwootns.common.utils.TimeUtils;

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
		
		
		Pattern dhcpPattern = Pattern.compile(
				"dhcpd\\[\\d+\\]: (DHCP[A-Z]+) ");
		
		// DHCPDISCOVER
		Pattern dhcpDiscover = Pattern.compile(
				"from (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))? via");
		
		// DHCPREQUEST
		Pattern dhcpRequest = Pattern.compile(
				"for (\\d+.\\d+.\\d+.\\d+) from (\\w+:\\w+:\\w+:\\w+:\\w+:\\w+)(\\s\\([A-Za-z0-9\\_\\-\\s]+\\))?([A-Za-z0-9\\s:]+)(\\(RENEW\\))?");
		
		for(String data : listSyslog) {
			
			System.out.println("=======================================");
			System.out.println(data);
			
			
			// Get DHCP Type
			Matcher match = dhcpPattern.matcher(data);
			
			if (match.find() && match.groupCount() > 0 ) {
				
				String dhcpType = match.group(1);
				int endPos = match.end();
				
				System.out.println(dhcpType);
				
				data = data.substring(endPos);
				
				String ipAddr;
				String macAddr;
				String hostname;
				String renew;
				
				// DHCPDISCOVER
				if (dhcpType != null && dhcpType.equals("DHCPDISCOVER"))
				{
					match = dhcpDiscover.matcher(data);
					
					if (match.find()) {
						
						//System.out.println(">> Success. Group Count=" + match.groupCount());
						
						if (match.groupCount() > 0) {
							
							//for(int i=0; i<=match.groupCount(); i++)
							//	System.out.println(String.format("[%d] %s", i, match.group(i)));
							
							macAddr = match.group(1);
							hostname = match.group(2);
							
							System.out.println("MAC Addr  : " + macAddr);
							System.out.println("Host Name : " + hostname);
						}
					}
				}
				// DHCPREQUEST
				else if (dhcpType != null && dhcpType.equals("DHCPREQUEST"))
				{
					// DHCPREQUEST
					match = dhcpRequest.matcher(data);
				
					if (match.find()) {
						
						//System.out.println(">> Success. Group Count=" + match.groupCount());
						
						if (match.groupCount() > 0) {
							
							//for(int i=0; i<=match.groupCount(); i++)
							//	System.out.println(String.format("[%d] %s", i, match.group(i)));

							ipAddr = match.group(1);
							macAddr = match.group(2);
							hostname = match.group(3);
							renew = match.group(5);
							
							System.out.println("Ip Addr  : " + ipAddr);
							System.out.println("Mac Addr  : " + macAddr);
							System.out.println("Host Name : " + hostname);
							System.out.println("renew : " + renew);
						}
					}
					else {
						System.out.println(">> Failed");
					}
				}
			}
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
