package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/17 0:30
 */
public class UpdateItemsByInstanceParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("instanceValues")
    private Map<String,String> intanceValues;

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

    public Map<String, String> getIntanceValues() {
        return intanceValues;
    }

    public void setIntanceValues(Map<String, String> intanceValues) {
        this.intanceValues = intanceValues;
    }
}
