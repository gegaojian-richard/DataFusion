package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.backend.configuration.Configuration;

import java.util.List;

public class AccuracyConfiguration implements Configuration{

    @JsonProperty("dataSourceId")
    private String dataSourceId;

    @JsonProperty("tableName")
    private String tableName;

    @JsonProperty("paramStrings")
    private List<String> paramStrings;

    @JsonCreator
    public AccuracyConfiguration(@JsonProperty("dataSourceId") String dataSourceId,
                                 @JsonProperty("tableName") String tableName,
                                 @JsonProperty("paramStrings") List<String> paramStrings){
        this.dataSourceId = dataSourceId;
        this.tableName = tableName;
        this.paramStrings = paramStrings;
    }

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

    public List<String> getParamStrings() {
        return paramStrings;
    }

    public void setParamStrings(List<String> paramStrings) {
        this.paramStrings = paramStrings;
    }
}
