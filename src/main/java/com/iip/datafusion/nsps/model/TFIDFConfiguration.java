package com.iip.datafusion.nsps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:38
 */
public class TFIDFConfiguration {
    @JsonProperty("path")
    private String path;
    @JsonProperty("topK")
    private int topK;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("dataSourceId")
    private String dataSourceId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
