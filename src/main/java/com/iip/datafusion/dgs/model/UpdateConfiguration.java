package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.backend.configuration.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author zengc
 * @date 2018/1/19 15:31
 */
public class UpdateConfiguration implements Configuration {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("instanceValues")
    private List<Map<String,String>> intanceValues;

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

    public List<Map<String, String>> getIntanceValues() {
        return intanceValues;
    }

    public void setIntanceValues(List<Map<String, String>> intanceValues) {
        this.intanceValues = intanceValues;
    }
}
