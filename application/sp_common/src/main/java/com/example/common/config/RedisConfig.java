package com.example.common.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 完成对Redis的整合的一些配置
 */
@Configuration
public class RedisConfig {

    /**
     * 创建RedisTemplate:用于执行Redis操作的方法
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory)    {
        RedisTemplate<String, Object> redisTemplate= new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);
        //设置通用序列化器
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }
}

