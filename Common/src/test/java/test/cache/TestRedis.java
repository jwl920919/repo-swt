package test.cache;

import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.shinwootns.common.cache.RedisClient;
import com.shinwootns.common.cache.RedisManager;

import redis.clients.jedis.Jedis;
import test.utils.TestUtils;

public class TestRedis {

	private static final Logger logger = Logger.getLogger(TestRedis.class);
	
	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		RedisManager rm = new RedisManager(logger);
		
		if (rm.connect("192.168.1.81", 6379, "shinwoo123!", 0) == false)
		{
			System.out.println("Redis connection failed.");
			return;
		}
		
		System.out.println("Redis connection OK.");
		
		Scanner sc = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("\n[Command List] : set, close, exit\n");
			System.out.print(">>");
	
			String command = sc.nextLine().toLowerCase();

			if (command.toLowerCase().equals("set"))
			{
				RedisClient redis = rm.createRedisClient();
				
				// set
				redis.set("key", "12345");
				System.out.println("set(key, 12345)");
				
				// get
				String value = redis.get("key");
				System.out.println("get(key) =" + value);
				
				redis.close();
			}
			else if (command.toLowerCase().equals("exit"))
			{
				break;
			}
		}
		
		System.out.println("Terminated.");
		
	}
}
