package com.iip.datafusion.jvs.model;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zengc
 * @date 2018/3/10 16:28
 */

@Component
public class RedisHelper implements ApplicationContextAware {
    private static StringRedisTemplate redisTemplate;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(RedisHelper.applicationContext == null) {
            RedisHelper.applicationContext = applicationContext;
        }
    }


    public static StringRedisTemplate getRedisTemplate(){
        return applicationContext.getBean("stringRedisTemplate",StringRedisTemplate.class);
    }


}
