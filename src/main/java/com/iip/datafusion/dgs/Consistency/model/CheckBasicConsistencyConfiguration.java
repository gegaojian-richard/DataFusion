package com.iip.datafusion.dgs.Consistency.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckBasicConsistencyConfiguration {
    @JsonProperty("dataSourceId1")
    private String dataSourceId1;
    @JsonProperty("dataSourceId2")
    private String dataSourceId2;
    @JsonProperty("tableName1")
    private String tableName1;
    @JsonProperty("tableName2")
    private String tableName2;
    @JsonProperty("columnName1")
    private String columnName1;
    @JsonProperty("columnName2")
    private String columnName2;
    @JsonProperty("primary_key1")
    private String primary_key1;
    @JsonProperty("primary_key2")
    private String primary_key2;

    public String getDataSourceId1() {
        return dataSourceId1;
    }
    public void setDataSourceId1(String dataSourceId) {
        this.dataSourceId1 = dataSourceId;
    }
    public String getDataSourceId2() {
        return dataSourceId2;
    }
    public void setDataSourceId2(String dataSourceId) {
        this.dataSourceId2 = dataSourceId;
    }
    public String getTableName1() {
        return tableName1;
    }
    public void setTableName1(String tableName) {
        this.tableName1 = tableName;
    }
    public String getTableName2() {
        return tableName2;
    }
    public void setTableName2(String tableName) {
        this.tableName2 = tableName;
    }

    public String getColumnName1() {
        return columnName1;
    }
    public void setColumnName1(String columnName) { this.columnName1 = columnName; }
    public String getColumnName2() {
        return columnName2;
    }
    public void setColumnName2(String columnName) {
        this.columnName2 = columnName;
    }
    public String getPrimary_key1() {
        return primary_key1;
    }
    public void setPrimary_key1(String primary_key) {
        this.primary_key1 = primary_key;
    }
    public String getPrimary_key2() {
        return primary_key2;
    }
    public void setPrimary_key2(String primary_key) {
        this.primary_key2 = primary_key;
    }

}
