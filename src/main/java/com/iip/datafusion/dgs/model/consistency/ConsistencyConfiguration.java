package com.iip.datafusion.dgs.model.consistency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.dgs.model.consistency.MapEntries;

import java.util.ArrayList;
import java.util.List;

public class ConsistencyConfiguration {
    @JsonProperty("mainDataSourceId")
    private String mainDataSourceId;
    @JsonProperty("mainTableName")
    private String mainTableName;
    @JsonProperty("m2f")
    private List<MapEntries> MapEntries = new ArrayList<>();  // 主表信息与从表信息的对应关系

    //    public CheckConsistencyConfiguration(){super();}
    @JsonCreator
    public ConsistencyConfiguration(
            @JsonProperty("mainDataSourceId") String mainDataSourceId,
            @JsonProperty("mainTableName") String mainTableName,
            @JsonProperty("m2f") List<MapEntries> MapEntries
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

    public List<MapEntries> getMapEntries() {
        return MapEntries;
    }

    public void setMapEntries(List<MapEntries> MapEntries) {
        this.MapEntries = MapEntries;
    }
}

