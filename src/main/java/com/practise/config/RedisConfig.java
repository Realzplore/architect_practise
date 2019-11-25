/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:artemis
 * Package Name:com.handchina.yunmart.artemis.config Date:2017/4/1 0001 Create
 * By:zongyun.zhou@hand-china.com
 */
package com.practise.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    private final RedisProperties properties;
    private final RedisSentinelConfiguration sentinelConfiguration;

    public RedisConfig(RedisProperties properties, ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider) {
        this.properties = properties;
        this.sentinelConfiguration =
            sentinelConfigurationProvider.getIfAvailable();
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisProperties.Pool props = this.properties.getJedis().getPool();
        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis(props.getMaxWait().toMillis());
        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        } else {
            RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
            if (sentinelProperties != null) {
                RedisSentinelConfiguration config = new RedisSentinelConfiguration();
                config.master(sentinelProperties.getMaster());
                config.setSentinels(this.createSentinels(sentinelProperties));
                return config;
            } else {
                return null;
            }
        }
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        ArrayList nodes = new ArrayList();
        String[] var3 = sentinel.getNodes().toArray(new String[]{});
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String node = var3[var5];

            try {
                String[] ex = StringUtils.split(node, ":");
                Assert.state(ex.length == 2, "Must be defined as \'host:port\'");
                nodes.add(new RedisNode(ex[0], Integer.parseInt(ex[1])));
            } catch (RuntimeException var8) {
                throw new IllegalStateException("Invalid redis sentinel property \'" + node + "\'", var8);
            }
        }

        return nodes;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig = this.properties.getJedis().getPool() != null ? this.jedisPoolConfig() : new JedisPoolConfig();
        JedisConnectionFactory jedisConnectionFactory = this.getSentinelConfig() != null
            ? new JedisConnectionFactory(this.getSentinelConfig(), poolConfig)
            : new JedisConnectionFactory(poolConfig);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(this.jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}
