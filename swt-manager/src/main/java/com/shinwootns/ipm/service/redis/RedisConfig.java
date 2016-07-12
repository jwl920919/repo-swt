package com.shinwootns.ipm.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
}
