package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TableAnyEmptyCheckParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("columnNames")
    private ArrayList<String> columnNames;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ArrayList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getTableName() {

        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}
