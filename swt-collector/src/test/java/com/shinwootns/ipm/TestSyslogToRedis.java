package com.shinwootns.ipm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import org.junit.Test;

import com.mysql.fabric.xmlrpc.base.Data;
import com.shinwootns.common.redis.RedisManager;
import com.shinwootns.common.utils.TimeUtils;

import redis.clients.jedis.Jedis;

public class TestSyslogToRedis {
	
	/*
	@Test
	public void testSyslogToRedis() {
		
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
