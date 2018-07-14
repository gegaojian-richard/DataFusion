package com.iip.datafusion.redis.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zengc
 * @date 2018/3/10 16:28
 */

@Component
public class RedisHelper implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(RedisHelper.class);
    private static StringRedisTemplate redisTemplate;

    private static ApplicationContext applicationContext;

    @PostConstruct
    private void flushRedisWhenStart(){
        logger.info("Flush RedisDB");
        // todo: 曾成：清空Redis缓存
        RedisHelper.getRedisTemplate().execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return null;
            }
        });
        logger.info("RedisDB Flushed");
    }

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
