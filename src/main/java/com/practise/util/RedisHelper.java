package com.practise.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: realz
 * @package: com.practise.config
 * @date: 2019-11-20
 * @email: zlp951116@hotmail.com
 */
@Component
public class RedisHelper {
    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * 获取指定pattern 的key集合
     */
    public List<String> keys(String pattern) {
        return this.scan(pattern, 0L);
    }

    /**
     * scan 实现
     *  @param pattern
     * @param count
     * @return
     */
    public List<String> scan(String pattern, Long count) {
        List<String> keys = new ArrayList<>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(pattern, ScanOptions.scanOptions().match(pattern).count(1000).build());
        while (cursor.hasNext()) {
            Map.Entry temp = cursor.next();
            String key = (String) temp.getKey();
            Set<String> valueSet = (Set<String>) temp.getValue();
            keys.addAll(valueSet);
        }
        return keys;
//        this.redisTemplate.execute((RedisConnection redisConnection) -> {
//            try (Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions()
//                .count(count <= 0 ? Long.MAX_VALUE : count)
//                .match(pattern)
//                .build())) {
//                cursor.forEachRemaining(consumer);
//                return null;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

}

