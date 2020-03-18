package com.bos.wandun.security.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis的Shiro缓存管理器实现
 * 
 * @author xq
 *
 */
public class RedisCacheManager implements CacheManager {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisCache<>(name, redisTemplate);
	}

}