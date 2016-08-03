package com.shinwootns.common.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisClient {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private Jedis _redis = null;
	
	public RedisClient(Jedis redis) {
		this._redis = redis;
	}
	
	//region [FUNC] is Connect
	public boolean isConnect()
	{
		synchronized(this)
		{
			try
			{
				if (this._redis == null)
					return false;
				
				// Ping
				String value = this._redis.ping();
				
				if (value != null && value.toUpperCase().equals("PONG"))
					return true;
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return false;
	}
	//endregion
	
	//region [FUNC] close
	public void close() {
		
		synchronized(this)
		{
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
	}
	//endregion
	
	
	public Jedis getJedis() {
		return this._redis;
	}
	
	//region [FUNC] get Keys
	public HashSet<String> getKeys(String pattern) {
		
		HashSet<String> keys = new HashSet<String>();
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
				{
					keys.addAll( this._redis.keys(pattern) );
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return keys;
	}
	//endregion
	
	//region [FUNC] get / set / delete / isExist
	public boolean set(String key, String value) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
				{
					this._redis.set(key, value);
					return true;
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return false;
	}
	
	public String get(String key) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
				{
					String value = this._redis.get(key);
					return value;
				}
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public Long delete(String key) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.del(key);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public Boolean isExist(String key) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.exists(key);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] expireTime / expireAt
	public Long expireTime(String key, int seconds) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.expire(key, seconds);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public Long expireAt(String key, long unixTime) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.expireAt(key, unixTime);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	//endregion
	
	//region [FUNC] increase / decrease
	
	public Long increase(String key) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.incr(key);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public Long decrease(String key) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.decr(key);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	//endregion
	
	//region [FUNC] append / pop
	
	public Long append(String key, String value) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.append(key, value);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public String rpop(String key) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.rpop(key);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	
	public String lpop(String key) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.lpop(key);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	//endregion
	
	//region [FUNC] zadd / zcount / zrank
	
	public Long zadd(String key, double score, String member) {
		
		synchronized(this)
		{
			try {
				if (this._redis != null)
					return this._redis.zadd(key, score, member);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zadd(key, scoreMembers);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		
		return null;
	}
	
	public Long zcount(String key, double min, double max) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zcount(key, min, max);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	
	public Long zrank(String key, String member) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrank(key, member);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	//endregion
	
	//region [FUNC] zrangeByRank / zrevrangeByRank
	public Set<String> zrangeByRank(String key, long start, long end) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrange(key, start, end);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	
	public Set<String> zrevrangeByRank(String key, long start, long end) {
		
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrevrange(key, start, end);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	//endregion
	
	//region [FUNC] zrangeByScore / zrevrangeByScore
	
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrangeByScore(key, min, max, offset, count);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	
	public Set<String> zrevrangeByScore(String key, double min, double max, int offset, int count) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrevrangeByScore(key, min, max, offset, count);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	//endregion
	
	//region [FUNC] zrangeByLex / zrevrangeByLex
	public Set<String> zrangeByLex(String key, String min, String max) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrangeByLex(key, min, max);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	
	public Set<String> zrevrangeByLex(String key, String min, String max) {
		synchronized(this)
		{
			try {
				
				if (this._redis != null)
					return this._redis.zrevrangeByLex(key, max, min);
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
		return null;
	}
	//endregion

	public void flushDB() {
		
		synchronized(this)
		{
			try {
				if (this._redis != null)
					this._redis.flushDB();
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
	
	public void flushAll() {
		
		synchronized(this)
		{
			try {
				if (this._redis != null)
					this._redis.flushAll();
			}
			catch(Exception ex) {
				_logger.error(ex.getMessage(), ex);
			}
		}
	}
}