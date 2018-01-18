package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestBody;

public class UpdateParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("columnName")
    private String columnName;
    @JsonProperty("newValue")
    private String newValue;
    @JsonProperty("whereClause")
    private String whereClause;

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

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }
}
