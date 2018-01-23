package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/16 13:10
 */
public class UpdateItemsParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("updateValues")
    private Map<String,String> updateValues;


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

    public Map<String, String> getUpdateValues() {
        return updateValues;
    }

    public void setUpdateValues(Map<String, String> updateValues) {
        this.updateValues = updateValues;
    }
}
