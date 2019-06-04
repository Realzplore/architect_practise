package com.practise.concurrent.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @Author: liping.zheng
 * @Date: 2018/9/26
 */
public class RedisLock {
    private static final long LOCK_TIMEOUT = 6 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private StringRedisTemplate redisTemplate;
    String key;
    String threadName;
    boolean locked = false;

    public RedisLock(StringRedisTemplate redisTemplate, String key, String threadName) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.threadName = threadName;
    }

    public synchronized void lock() {
        logger.info(threadName + "执行加锁===========");
        while (true) {
            Long timeOut = currentTimeForRedis() + LOCK_TIMEOUT + 1;
            if (redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                //定义序列化方式
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = serializer.serialize(timeOut.toString());
                locked = redisConnection.setNX(key.getBytes(), value);
                return locked;
            })) {
                logger.info(threadName + "加锁成功===========");
                redisTemplate.expire(key, LOCK_TIMEOUT, TimeUnit.MICROSECONDS);
            } else {

            }
        }
    }

    public void unLock() {
        logger.info(threadName + "执行解锁===========");
        String result = redisTemplate.opsForValue().get(key);
        Long timeOut = result == null ? null : Long.valueOf(result);
        if (timeOut != null) {
            redisTemplate.delete(key);
            logger.info(threadName + "解锁成功===========");
        }
    }

    public long currentTimeForRedis() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.time();
            }
        });
    }
}
