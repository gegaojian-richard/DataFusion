package com.iip.datafusion.backend.jdbchelper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JDBCHelper implements ApplicationContextAware {
    private static JdbcTemplate jdbcTemplate;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(JDBCHelper.applicationContext == null) {
            JDBCHelper.applicationContext = applicationContext;
        }
    }


    public static JdbcTemplate getJdbcTemplate(){
        return applicationContext.getBean("jdbcTemplate",JdbcTemplate.class);
    }

    public static RedisTemplate getRedisTemplate(){
        return applicationContext.getBean("redisTemplate",RedisTemplate.class);
    }
}
