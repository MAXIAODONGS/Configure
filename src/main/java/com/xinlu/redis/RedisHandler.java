package com.xinlu.redis;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisHandler {


    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 服务自身redis工具类
     */
    private RedisTemplateUtil selfRedisTemplateUtil;


    /**
     * 获取服务自身redis操作类
     */
    public RedisTemplateUtil getLocalRedisTemplate() {
        if (selfRedisTemplateUtil == null) {
            selfRedisTemplateUtil = new RedisTemplateUtil(redisTemplate);
        }
        return selfRedisTemplateUtil;
    }
}
