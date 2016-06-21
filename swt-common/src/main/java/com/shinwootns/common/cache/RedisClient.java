package com.shinwootns.common.cache;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.shinwootns.common.utils.LogUtils;

import redis.clients.jedis.Jedis;

public class RedisClient {
	
	private Logger _logger = null;
	private Jedis _redis = null;
	
	public RedisClient(Jedis redis, Logger logger) {
		this._redis = redis;
		this._logger = logger;
	}
	
	//region isConnection / Close
	public boolean isConnection(Jedis redis)
	{
		try
		{
			if (this._redis == null)
				return false;
			
			// Ping
			String value = redis.ping();
			
			if (value != null && value.toUpperCase().equals("PONG"))
				return true;
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		
		return false;
	}
	
	public void close() {
		try
		{
			if (this._redis != null)
				this._redis.close();
		}
		catch(Exception ex)
		{}
		finally {
			this._redis = null;
		}
	}
	//endregion
	
	//region get / set / delete / isExist
	public boolean set(String key, String value) {
		try {
			
			if (this._redis != null)
			{
				this._redis.set(key, value);
				return true;
			}
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return false;
	}
	
	public String get(String key) {
		try {
			
			if (this._redis != null)
			{
				String value = this._redis.get(key);
				return value;
			}
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Long delete(String key) {
		try {
			
			if (this._redis != null)
				return this._redis.del(key);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Boolean isExist(String key) {
		try {
			
			if (this._redis != null)
				return this._redis.exists(key);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	//endregion
	
	//region expire
	public Long expire(String key, int seconds) {
		try {
			
			if (this._redis != null)
				return this._redis.expire(key, seconds);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Long expireAt(String key, long unixTime) {
		try {
			
			if (this._redis != null)
				return this._redis.expireAt(key, unixTime);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	//endregion
	
	//region increase / decrease
	
	public Long increase(String key) {
		try {
			
			if (this._redis != null)
				return this._redis.incr(key);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Long decrease(String key) {
		try {
			
			if (this._redis != null)
				return this._redis.decr(key);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	//endregion
	
	//region append / pop
	
	public Long append(String key, String value) {
		try {
			
			if (this._redis != null)
				return this._redis.append(key, value);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public String rpop(String key) {
		try {
			
			if (this._redis != null)
				return this._redis.rpop(key);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public String lpop(String key) {
		try {
			
			if (this._redis != null)
				return this._redis.lpop(key);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	//endregion
	
	//region zorder
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		try {
			
			if (this._redis != null)
				return this._redis.zadd(key, scoreMembers);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Long zcount(String key, double min, double max) {
		try {
			
			if (this._redis != null)
				return this._redis.zcount(key, min, max);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Long zrank(String key, String member) {
		try {
			
			if (this._redis != null)
				return this._redis.zrank(key, member);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Set<String> zrangeByRank(String key, long start, long end) {
		try {
			
			if (this._redis != null)
				return this._redis.zrange(key, start, end);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Set<String> zrevrangeByRank(String key, long start, long end) {
		try {
			
			if (this._redis != null)
				return this._redis.zrevrange(key, start, end);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		try {
			
			if (this._redis != null)
				return this._redis.zrangeByScore(key, min, max, offset, count);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Set<String> zrevrangeByScore(String key, double min, double max, int offset, int count) {
		try {
			
			if (this._redis != null)
				return this._redis.zrevrangeByScore(key, min, max, offset, count);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Set<String> zrangeByLex(String key, String min, String max) {
		try {
			
			if (this._redis != null)
				return this._redis.zrangeByLex(key, min, max);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	
	public Set<String> zrevrangeByLex(String key, String min, String max) {
		try {
			
			if (this._redis != null)
				return this._redis.zrevrangeByLex(key, max, min);
		}
		catch(Exception ex) {
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
		return null;
	}
	//endregion

}