package com.example.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Redis 助手类
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public static RedisTemplate redis;

    @PostConstruct
    public void getRedisTemplate(){
        redis=this.redisTemplate;
    }

    /**
     * 设置过期时间
     * @param key
     * @param expire
     * @return
     */
    public boolean setExpire(String key, long expire){
        return redis.expire(key,expire, TimeUnit.SECONDS);
    }

    /**
     * 判断是否过期
     * getExpire如果该值有过期时间，就返回相应的过期时间;如果该值没有设置过期时间，就返回-1;如果没有该值，就返回-2;
     * @param key 过期返回true 没过期 false
     * @return
     */
    public boolean isExpire(String key){
        if(!this.exists(key)){
            return true;
        }
        long res =  redis.getExpire(key);
        if(res==-2){
            return true;
        }else if(res==-1){
            return false;
        }else {
            return !(res >0);
        }
    }

    /**
     * 判断值是否存在
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redis.hasKey(key);
    }
}

