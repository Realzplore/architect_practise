package com.practise.realz.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: realz
 * @package: com.practise.realz.service
 * @date: 2019-11-21
 * @email: zlp951116@hotmail.com
 */
@Service
@CacheConfig(cacheNames = {"REDIS:"})
@RedisHash(value = "test")
public class RedisService {
    @Cacheable(key = "#entry.getKey()")
    public String setKey(Map.Entry<String, String> entry) {
        return entry.getValue();
    }
}
