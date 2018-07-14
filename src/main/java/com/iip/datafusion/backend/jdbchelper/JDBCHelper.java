package com.iip.datafusion.backend.jdbchelper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * JDBCTemplate帮助类
 * getJdbcTemplate方法用以获得在应用程序上下文中的JDBCTemplate实例对象
 * @Author Ge GaoJian
 */
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


}
