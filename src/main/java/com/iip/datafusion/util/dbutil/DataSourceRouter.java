package com.iip.datafusion.util.dbutil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

@Component(value = "dataSource")
public class DataSourceRouter extends AbstractRoutingDataSource {

    @Autowired
    private Environment environment;

    private Map<Object, Object> customDataSource = new HashMap<>();

    private ConversionService conversionService = new DefaultConversionService(); // 类型转换器
    private PropertyValues dataSourcePropertyValues;


    // AbstractRoutingDataSource通过此方法确定当前的DataSource
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceRouterManager.getCurrentDataSourceKey(); // 返回线程当前持有的DataSource的ID
    }

    public DataSourceRouter(){
        DataSourceProperties defaultDataSourceProperties = new DataSourceProperties();
        defaultDataSourceProperties.setUrl("");
        defaultDataSourceProperties.setId("primary");
        defaultDataSourceProperties.setDisplayName("primary");
        defaultDataSourceProperties.setDriverClassName("com.mysql.jdbc.Driver"); 
        defaultDataSourceProperties.setUrl("jdbc:mysql://114.212.84.208:3306/kjb?useUnicode=true&characterEncoding=gbk&serverTimezone=GMT");
        defaultDataSourceProperties.setUsername("root");
        defaultDataSourceProperties.setPassword("iipconfig");
        customDataSource.put("primary", createDataSource(defaultDataSourceProperties));
        setTargetDataSources(customDataSource);
//        setDefaultTargetDataSource(createDataSource(defaultDataSourceProperties));
//        afterPropertiesSet();
    }

    // 添加数据源
    public void addDataSource(DataSourceProperties properties){

        // 1. 判断是否已经存在
        if(contained(properties)) return;

        // 2. 创建DataSource并添加至TargetDataSource
        customDataSource.put(properties.getId(), createDataSource(properties));

        // 3. AbstractRoutingDataSource方法
        setTargetDataSources(customDataSource);
        afterPropertiesSet();
    }

    // 创建Hikari数据源
    private DataSource createDataSource(DataSourceProperties properties){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        DataSource dataSource = new HikariDataSource(config);

        // 配置文件中可以通过spring.datasource.配置datasource的其他属性，下面的代码将配置文件中其他的spring.datasource.配置信息绑定到数据上
//        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
//        dataBinder.setConversionService(conversionService);
//        dataBinder.setIgnoreNestedProperties(false);//false
//        dataBinder.setIgnoreInvalidFields(false);//false
//        dataBinder.setIgnoreUnknownFields(true);//true
//        if(dataSourcePropertyValues == null){
//            Map<String, Object> rpr = new RelaxedPropertyResolver(environment, "spring.datasource").getSubProperties(".");
//            Map<String, Object> values = new HashMap<>(rpr);
//            // 排除已经设置的属性
////            values.remove("type");
////            values.remove("driver-class-name");
////            values.remove("url");
////            values.remove("username");
////            values.remove("password");
//            dataSourcePropertyValues = new MutablePropertyValues(values);
//        }
//        dataBinder.bind(dataSourcePropertyValues);

        return new HikariDataSource(config);
    }

    // 判断数据源是否已经存在路由中
    public boolean contained(DataSourceProperties properties){
        return false;
    }
}
