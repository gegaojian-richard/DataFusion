package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormulaUpdateParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("setClause")
    private String setClause;
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

    public String getSetClause() {
        return setClause;
    }

    public void setSetClause(String setClause) {
        this.setClause = setClause;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }
}
