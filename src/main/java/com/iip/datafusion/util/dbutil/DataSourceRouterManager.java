package com.iip.datafusion.util.dbutil;

import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GeGaojian on 2017/12/12.
 * DataSource路由管理类 每个session对应一个对象
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DataSourceRouterManager {
    private static DataSourceRouter dataSourceRouter; // 全局DataSource路由

    private static final ThreadLocal<String> currentDataSourceID = new ThreadLocal<String>(); // 每个线程会持有自己的
    public List<String> dataSourceIds = new ArrayList<>(); // 每个session不同，保存在每个session对应的DataSourceRouterManager

    @Autowired
    private DataSourceRouter _dataSourceRouter; // 静态变量无法自动注入，通过实例变量来协助完成注入

    public DataSourceRouterManager() {
        dataSourceRouter = _dataSourceRouter;
        dataSourceIds.add("primary");
    }

    @PostConstruct
    private void initStaticDataSourceRouter () {
        dataSourceRouter = this._dataSourceRouter;
    }

    // 当需要切换线程当前的DataSource时，调用此方法指定当前的DataSource的ID
    public static void setCurrentDataSourceKey(String dataSourceID) {
        currentDataSourceID.set(dataSourceID);
    }

    public static String getCurrentDataSourceKey(){
        return currentDataSourceID.get();
    }

    public static void clearCurrentDataSourceKey() {
        currentDataSourceID.remove();
    }

    public boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }

    public Result addDataSource(DataSourceProperties properties){
        Result result = dataSourceRouter.addDataSource(properties, dataSourceIds);
        if (result.getStatus() == 1) dataSourceIds.add(properties.getId());
        return result;
    };

    public List<String> getDataSourceDisplayNames(){
        return dataSourceRouter.getDisplayNameByIDs(dataSourceIds);
    }

    public List<DataSourceProperties> getDataSourceProperties(){
        return dataSourceRouter.getDataSourcePropertiesByIDs(dataSourceIds);
    }

    public DataSourceProperties getDataSourceProperties(String dataSourceId){
        return dataSourceRouter.getDataSourcePropertiesByID(dataSourceId);
    }

    public String getDataSourceDisplayName(String dataSourceId){
        return getDataSourceProperties(dataSourceId).getDisplayName();
    }

    public void deleteConnection(String id) {
        //TODO 目前只是将数据源id删除，后期可能对于每一个数据源加入count，为0时真正删除连接
        //dataSourceId中删除
        dataSourceIds.remove(id);
    }
}
