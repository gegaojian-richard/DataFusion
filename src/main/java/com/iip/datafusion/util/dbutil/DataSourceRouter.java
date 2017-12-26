package com.iip.datafusion.util.dbutil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component(value = "dataSource")
public class DataSourceRouter extends AbstractRoutingDataSource {

    private volatile AtomicInteger DATASOURCE_COUNT = new AtomicInteger();

    private Map<Object, Object> customDataSource = new HashMap<>();

    private Map<String, DataSourceProperties> customDataSourceProperties = new HashMap<>();

    // AbstractRoutingDataSource通过此方法确定当前的DataSource
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceRouterManager.getCurrentDataSourceKey(); // 返回线程当前持有的DataSource的ID
    }

    public DataSourceRouter(){
        // 创建主数据库DataSource
        DataSourceProperties defaultDataSourceProperties = new DataSourceProperties();
        defaultDataSourceProperties.setUrl("");
        defaultDataSourceProperties.setId("primary");
        defaultDataSourceProperties.setDisplayName("primary");
        defaultDataSourceProperties.setDriverClassName("com.mysql.jdbc.Driver");
        defaultDataSourceProperties.setUrl("jdbc:mysql://localhost:3306/wenda?useUnicode=true&characterEncoding=gbk&serverTimezone=GMT");
        defaultDataSourceProperties.setUsername("root");
        customDataSource.put("primary", createDataSource(defaultDataSourceProperties));
        setTargetDataSources(customDataSource);

        // 初始化DataSource计数
        DATASOURCE_COUNT.set(0);
    }

    // 添加数据源
    public void addDataSource(DataSourceProperties properties, List<String> dataSourceIds){

        Map<String, String> result = new HashMap<>();
        // 1. 判断是否已经存在
        if(!contained(properties, dataSourceIds).isEmpty()) return;

        String dataSourceID = "db_" + DATASOURCE_COUNT.incrementAndGet();

        properties.setId(dataSourceID);

        customDataSourceProperties.put(dataSourceID, properties);

        // 2. 创建DataSource并添加至TargetDataSource
        customDataSource.put(properties.getId(), createDataSource(properties));

        // 3. AbstractRoutingDataSource方法,重写AbstractRoutingDataSource中的数据源
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
    public Map<String,String> contained(DataSourceProperties properties, List<String> dataSourceIds){
        Map<String,String> result = new HashMap<>();

        // 检查该用户对应的DataSource的displayName是否有重复
        for (String dsID: dataSourceIds
                ) {
            if(customDataSourceProperties.get(dsID).getDisplayName() == properties.getDisplayName()){
                result.put("msg", "displayName 重名！");
            }
        }
        for (DataSourceProperties dsp: customDataSourceProperties.values()
                ) {
            if (dsp.getUrl() == properties.getUrl() && dsp.getDriverClassName() == properties.getDriverClassName()){
                result.put("msg", dsp.getId()); // 如果已经存在 将已经存在的dataSource的id返回
            }
        }
        return result;
    }

    public List<String> getDisplayNameByIDs(List<String> dataSourceIds){
        List<String> result = new ArrayList<>();
        for (String dsID: dataSourceIds
                ) {
            result.add(customDataSourceProperties.get(dsID).getDisplayName());
        }

        return result;
    }

    public List<DataSourceProperties> getDataSourcePropertiesByIDs(List<String> dataSourceIds){
        List<DataSourceProperties> result = new ArrayList<>();

        for (String dsID: dataSourceIds
                ) {
            result.add(customDataSourceProperties.get(dsID));
        }

        return result;
    }
}
