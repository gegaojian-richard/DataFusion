package com.iip.datafusion.dgs.Consistency.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CheckConsistencyConfiguration {
    @JsonProperty("dataSourceId1")
    private String mainDataSourceId;
    @JsonProperty("tableName1")
    private String mainTableName;
    @JsonProperty("m2f")
    private List<com.iip.datafusion.dgs.Consistency.model.MapEntries> MapEntries = new ArrayList<com.iip.datafusion.dgs.Consistency.model.MapEntries>();  // 主表信息与从表信息的对应关系

//    public CheckConsistencyConfiguration(){super();}
    @JsonCreator
    public CheckConsistencyConfiguration(
                             @JsonProperty("dataSourceId1") String mainDataSourceId,
                             @JsonProperty("tableName1") String mainTableName,
                             @JsonProperty("m2f") List<com.iip.datafusion.dgs.Consistency.model.MapEntries> MapEntries
                             ) {
        this.mainDataSourceId = mainDataSourceId;
        this.mainTableName = mainTableName;
        this.MapEntries = MapEntries;
    }
    public String getmainDataSourceId() {
        return mainDataSourceId;
    }

    public void setmainDataSourceId(String mainDataSourceId) {
        this.mainDataSourceId = mainDataSourceId;
    }

    public String getmainTableName() {
        return mainTableName;
    }

    public void setmainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public List<com.iip.datafusion.dgs.Consistency.model.MapEntries> getMapEntries() {
        return MapEntries;
    }

    public void setMapEntries(List<com.iip.datafusion.dgs.Consistency.model.MapEntries> MapEntries) {
        this.MapEntries = MapEntries;
    }
}
