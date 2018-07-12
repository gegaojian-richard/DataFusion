package com.iip.datafusion.util.dbutil;

import com.iip.datafusion.util.jsonutil.Result;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by GeGaojian on 2017/12/12.
 * DataSource全局路由
 */

@Component(value = "dataSource")
public class DataSourceRouter extends AbstractRoutingDataSource {
    // 2017/12/29:尝试使用@Value注解将主数据库配置信息从配置文件中导入，但是@Value注解在构造函数完成后才会对成员变量注入值，失败！！

    private volatile AtomicInteger DATASOURCE_COUNT = new AtomicInteger();

    private Map<Object, Object> customDataSource = new HashMap<>();

    private Map<String, DataSourceProperties> customDataSourceProperties = new HashMap<>();

    // AbstractRoutingDataSource通过此方法确定当前的DataSource
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceRouterManager.getCurrentDataSourceKey(); // 返回线程当前持有的DataSource的ID
    }

    @Autowired // 注入主数据库配置信息
    public DataSourceRouter(MainDataSourceProperties mainDataSourceProperties){
        // 此构造函数由Spring容器调用，需要在构造函数执行时，添加主数据库
        // 创建主数据库DataSource
        customDataSourceProperties.put("primary", mainDataSourceProperties);
        customDataSource.put("primary", createDataSource(mainDataSourceProperties));
        setTargetDataSources(customDataSource);

        // 初始化DataSource计数
        DATASOURCE_COUNT.set(0);

    }

    // 添加数据源
    public Result addDataSource(DataSourceProperties properties, List<String> dataSourceIds){

        Result result = new Result();

        // 1. 判断是否已经存在
        Map<String, Integer> map = contained(properties, dataSourceIds);

        if (map.get("msg") == 1) {
            result.setStatus(0);
            result.setMsg("数据源重名");
            return result; // 重名
        }

        if (map.get("msg") == 3) {
            result.setStatus(0);
            result.setMsg("数据源已添加");
            return result;
        }

        if (map.get("msg") == 2) { // 其他用户已添加
            customDataSourceProperties.put(properties.getId(), properties);
        }else{
            // 修改回应付检查之前的原始状态 --jingwei
//            String dataSourceID = properties.getDisplayName();
            String dataSourceID = "db_" + DATASOURCE_COUNT.incrementAndGet();
            properties.setId(dataSourceID);
            //TODO 这边有问题，应该先创建数据源再存储数据源信息。否则创建失败的数据源信息也会出现在customDataSourceProperties中
            customDataSourceProperties.put(dataSourceID, properties);

            // 2. 创建DataSource并添加至TargetDataSource
//            customDataSource.put(properties.getDisplayName(),createDataSource(properties));
            customDataSource.put(properties.getId(), createDataSource(properties));

            // 3. AbstractRoutingDataSource方法,重写AbstractRoutingDataSource中的数据源
            setTargetDataSources(customDataSource);
            afterPropertiesSet();
        }

        result.setStatus(1);
        result.setData("{\"dbid\":\"" + properties.getId() + "\"}");

        return result;
    }

    // 创建Hikari数据源
    private DataSource createDataSource(DataSourceProperties properties){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl() + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT"); // 修复中文问题
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        config.setDriverClassName(properties.getDriverClassName());
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
    public Map<String, Integer> contained(DataSourceProperties properties, List<String> dataSourceIds){
        Map<String, Integer> result = new HashMap<>();

        // 检查该用户对应的DataSource的displayName是否有重复
        for (String dsID: dataSourceIds
                ) {
            if(customDataSourceProperties.get(dsID).getDisplayName().equals(properties.getDisplayName())){
                result.put("msg", 1); // 1 - displayName重复
                return result;
            }
            if(customDataSourceProperties.get(dsID).getUrl().equals(properties.getUrl()) &&
                    customDataSourceProperties.get(dsID).getDriverClassName().equals(properties.getDriverClassName())){
                result.put("msg", 3); // 3 - 已添加
                return result;
            }
        }
        for (DataSourceProperties dsp: customDataSourceProperties.values()
                ) {
            if (dsp.getUrl().equals(properties.getUrl()) && dsp.getDriverClassName().equals(properties.getDriverClassName())){
                properties.setId(dsp.getId());
                result.put("msg",2); // 2 - 存在
                return result;
            }
        }

        result.put("msg",0); // 0 - 不存在，通过检查
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

    public DataSourceProperties getDataSourcePropertiesByID(String dataSourceId){
        return customDataSourceProperties.get(dataSourceId);
    }
}
