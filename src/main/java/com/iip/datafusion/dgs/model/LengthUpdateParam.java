package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LengthUpdateParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("columnName")
    private String columnName;
    @JsonProperty("length")
    private String length;
    @JsonProperty("fill")
    private String fill;

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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

}
