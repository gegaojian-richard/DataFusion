package com.iip.datafusion.dgs.model.integrity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.backend.configuration.Configuration;
import java.util.ArrayList;

/**
 * @author zengc
 * @date 2018/1/21 10:17
 */
public class IntegrityConfiguration implements Configuration {

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
