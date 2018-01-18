package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/15 15:45
 */
public class UpdateColumnsParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("updateValues")
    private Map<String,Object> updateValues;


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

    public Map<String, Object> getUpdateValues() {
        return updateValues;
    }

    public void setUpdateValues(Map<String, Object> updateValues) {
        this.updateValues = updateValues;
    }
}
