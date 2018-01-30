package com.iip.datafusion.util.dbutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by GeGaojian on 2017/12/29.
 * 主数据库配置信息实体类
 */
@Component
public class MainDataSourceProperties extends DataSourceProperties{
    private String id;
    private String displayName;
    private String driverClassName;
    @Value("${spring.datasource.primary.url}")
    private String url;     //  host:port/dataBaseName
    @Value("${spring.datasource.primary.username}")
    private String username;
    @Value("${spring.datasource.primary.password}")
    private String password;

    public MainDataSourceProperties() {
        id = "primary";
        displayName = "primary";
        driverClassName = "com.mysql.jdbc.Driver";
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
