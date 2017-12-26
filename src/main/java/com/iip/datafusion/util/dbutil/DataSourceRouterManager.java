package com.iip.datafusion.util.dbutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DataSourceRouterManager {
    private DataSourceRouter dataSourceRouter; // 全局DataSource路由

    private static final ThreadLocal<String> currentDataSourceID = new ThreadLocal<String>(); // 每个线程会持有自己的
    public List<String> dataSourceIds = new ArrayList<>();


    @Autowired
    private DataSourceRouter _dataSourceRouter; // 静态变量无法自动注入，通过实例变量来协助完成注入

    public DataSourceRouterManager() {
        dataSourceRouter = _dataSourceRouter;
        dataSourceIds.add("primary");
    }

    public List<String> getDataSourceIds() {
        return dataSourceIds;
    }

    @PostConstruct
    private void initStaticDataSourceRouter () {
        dataSourceRouter = this._dataSourceRouter;
    }

    // 当需要切换线程当前的DataSource时，调用此方法指定当前的DataSource的ID
    public void setCurrentDataSourceKey(String dataSourceID) {
        currentDataSourceID.set(dataSourceID);
    }

    public String getCurrentDataSourceKey(){
        return currentDataSourceID.get();
    }

    public void clearCurrentDataSourceKey() {
        currentDataSourceID.remove();
    }

    public boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }

//    public static void addDataSource(DataSourceProperties properties){
//        dataSourceRouter.addDataSource(properties);
//        dataSourceIds.add(properties.getId());
//    }
    public void addDataSource(DataSourceProperties properties){
        dataSourceRouter.addDataSource(properties);
        dataSourceIds.add(properties.getId());
    }
}
