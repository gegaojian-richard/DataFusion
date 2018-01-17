package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public class FormulaCheckParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("whereClause")
    private String whereClause;
    @JsonProperty("columnAttributeValues")
    private ArrayList<ColumnAttributeValue> columnAttributeValues;

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

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public ArrayList<ColumnAttributeValue> getColumnAttributeValues() {
        return columnAttributeValues;
    }

    public void setColumnAttributeValues(ArrayList<ColumnAttributeValue> columnAttributeValues) {
        this.columnAttributeValues = columnAttributeValues;
    }

}
