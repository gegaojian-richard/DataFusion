package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateParam {

    @JsonProperty("dataSourceId")
    private String dataSourceId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("newValues")
    private ArrayList<Map<String,String>> newValues;

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

    public ArrayList<Map<String, String>> getNewValues() {
        return newValues;
    }

    public void setNewValues(ArrayList<Map<String, String>> newValues) {
        this.newValues = newValues;
    }
}
