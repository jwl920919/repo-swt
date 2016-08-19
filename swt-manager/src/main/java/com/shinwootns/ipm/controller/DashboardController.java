package com.shinwootns.ipm.controller;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shinwootns.data.key.RedisKeys;
import com.shinwootns.ipm.service.redis.RedisManager;

import redis.clients.jedis.Jedis;

@RestController
public class DashboardController {
	
	private final Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	//region /api/status/dashboard/network_ip
	@RequestMapping(value="/api/status/dashboard/network_ip", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiNetworkIpAll() 
	{
		return ApiNetworkIp(null);
	}
	
	@RequestMapping(value="/api/status/dashboard/network_ip/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiNetworkIp(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result;
			if (site_id != null)
			{
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_NETWORK_IP)
						.append(":").append(site_id)
						.toString()
				);
			}
			else {
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_NETWORK_IP)
						.toString()
				);
			}
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonObject()).toString();
	}
	//endregion
	
	//region /api/status/dashboard/guest_ip
	@RequestMapping(value="/api/status/dashboard/guest_ip", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiGuestIpStatusAll() 
	{
		return ApiGuestIpStatus(null);
	}
	
	@RequestMapping(value="/api/status/dashboard/guest_ip/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiGuestIpStatus(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result;
			if (site_id != null)
			{
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_GUEST_IP)
						.append(":").append(site_id)
						.toString()
				);
			}
			else {
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_GUEST_IP)
						.toString()
				);
			}
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonObject()).toString();
	}
	//endregion
	
	//region /api/status/dashboard/lease_ipv4
	@RequestMapping(value="/api/status/dashboard/lease_ipv4", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiLeaseIpv4All() 
	{
		return ApiLeaseIpv4(null);
	}
	
	@RequestMapping(value="/api/status/dashboard/lease_ipv4/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiLeaseIpv4(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result;
			if (site_id != null)
			{
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_LEASE_IPV4)
						.append(":").append(site_id)
						.toString()
				);
			}
			else {
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_LEASE_IPV4)
						.toString()
				);
			}
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonObject()).toString();
	}
	//endregion
	
	//region /api/status/dashboard/lease_ipv6
	@RequestMapping(value="/api/status/dashboard/lease_ipv6", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiLeaseIpv6All() 
	{
		return ApiLeaseIpv6(null);
	}
	
	@RequestMapping(value="/api/status/dashboard/lease_ipv6/{site_id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String ApiLeaseIpv6(
			@PathVariable(value="site_id") Integer site_id ) 
	{
		Jedis redis = RedisManager.getInstance().getRedisClient();
		if(redis != null)
		try
		{
			String result;
			if (site_id != null)
			{
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_LEASE_IPV6)
						.append(":").append(site_id)
						.toString()
				);
			}
			else {
				result = redis.get((new StringBuilder())
						.append(RedisKeys.KEY_DASHBOARD_LEASE_IPV6)
						.toString()
				);
			}
			
			if (result != null && result.length() > 0)
				return result;
			
		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			redis.close();
		}
		
		return (new JsonArray()).toString();
	}
	//endregion

	
	/*
	@RequestMapping("/Dashboard/Summary")
	public String Summary() {
		
		Random random = new Random();
		
		JsonObject jObj = new JsonObject();
		
		// CPU
		JsonArray cpuArray = new JsonArray(); 
		
		for(int i=0; i < 4; i++) {
			
			JsonObject cpuObj = new JsonObject();
			cpuObj.addProperty("seq", String.format("cpu%d", i));
			cpuObj.addProperty("value", (30 + random.nextInt(20) % 50));
			cpuObj.addProperty("unit", "%");
			
			cpuArray.add(cpuObj);
		}
		
		jObj.add("CPU", cpuArray);
		
		
		// MEMORY
		JsonObject memObject = new JsonObject();
		memObject.addProperty("value", 4);
		memObject.addProperty("total", 8);
		memObject.addProperty("unit", "G");
		
		jObj.add("MEMORY", memObject);
		
		
		// DISK
		JsonArray diskArray = new JsonArray(); 
		
		JsonObject disk1 = new JsonObject();
		disk1.addProperty("seq", "/");
		disk1.addProperty("usage", 5638);
		disk1.addProperty("total", 28111);
		disk1.addProperty("unit", "M");
		diskArray.add(disk1);
		
		JsonObject disk2 = new JsonObject();
		disk2.addProperty("seq", "/dev/boot");
		disk2.addProperty("usage", 165);
		disk2.addProperty("total", 497);
		disk2.addProperty("unit", "M");
		diskArray.add(disk2);
		
		jObj.add("DISK", diskArray);
		
		// NETWORK
		JsonArray networkArray = new JsonArray(); 
		
		JsonObject byte1 = new JsonObject();
		byte1.addProperty("seq", "IN");
		byte1.addProperty("usage", random.nextInt(10)+5);
		byte1.addProperty("total", 1000);
		byte1.addProperty("unit", "M");
		networkArray.add(byte1);
		
		JsonObject bytes2 = new JsonObject();
		bytes2.addProperty("seq", "OUT");
		bytes2.addProperty("usage", random.nextInt(10)+5);
		bytes2.addProperty("total", 1000);
		bytes2.addProperty("unit", "M");
		networkArray.add(bytes2);
		
		jObj.add("NETWORK", networkArray);
		
		// HA
		JsonObject haObj = new JsonObject();
		haObj.addProperty("STATE", "FALSE");
		
		jObj.add("HA", haObj);
		
		return jObj.toString();
	}
	*/
}
