package com.iip.datafusion.dgs.model;

import com.iip.datafusion.util.jsonutil.Result;

import java.util.List;

/**
 * @author zengc
 * @date 2018/3/26 19:18
 */
public class UpdateJob {

    private String dataSourceId;
    private String tableName;
    private List<String> sqlList;
    private Result result;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
