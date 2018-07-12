package com.iip.datafusion.dgs.model.consistency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.dgs.model.consistency.MapEntries;

import java.util.ArrayList;
import java.util.List;

public class UpdateConsistencyConfiguration {
    @JsonProperty("mainDataSourceId")
    private String mainDataSourceId;
    @JsonProperty("mainTableName")
    private String mainTableName;
    @JsonProperty("mainColumnName")
    private String mainColumnName;
    @JsonProperty("mainPrimary_key")
    private String mainPrimary_key;
    @JsonProperty("followDataSourceId")
    private String followDataSourceId;
    @JsonProperty("followTableName")
    private String followTableName;
    @JsonProperty("followColumnName")
    private String followColumnName;
    @JsonProperty("followPrimary_key")
    private String followPrimary_key;
    @JsonProperty("u2r")
    private List<MapEntries2> MapEntries = new ArrayList<>();  // 更新表信息与参照表信息的对应关系

    //    public CheckConsistencyConfiguration(){super();}
    @JsonCreator
    public UpdateConsistencyConfiguration(
            @JsonProperty("mainDataSourceId") String mainDataSourceId,
            @JsonProperty("mainTableName") String mainTableName,
            @JsonProperty("mainColumnName") String mainColumnName,
            @JsonProperty("mainPrimary_key") String mainPrimary_key,
            @JsonProperty("followDataSourceId") String followDataSourceId,
            @JsonProperty("followTableName") String followTableName,
            @JsonProperty("followColumnName") String followColumnName,
            @JsonProperty("followPrimary_key") String followPrimary_key,
            @JsonProperty("u2r") List<MapEntries2> MapEntries
    ) {
        this.mainDataSourceId = mainDataSourceId;
        this.mainTableName = mainTableName;
        this.mainColumnName = mainColumnName;
        this.mainPrimary_key = mainPrimary_key;
        this.followDataSourceId = followDataSourceId;
        this.followTableName = followTableName;
        this.followColumnName = followColumnName;
        this.followPrimary_key = followPrimary_key;
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

    public String getmainColumnName() {
        return mainColumnName;
    }

    public void setmainColumnName(String mainColumnName) {
        this.mainColumnName = mainColumnName;
    }

    public String getmainPrimary_key() { return mainPrimary_key; }

    public void setmainPrimary_key(String mainPrimary_key) {
        this.mainPrimary_key = mainPrimary_key;
    }

    public String getfollowDataSourceId() {
        return followDataSourceId;
    }

    public void setfollowDataSourceId(String followDataSourceId) {
        this.followDataSourceId = followDataSourceId;
    }

    public String getfollowTableName() {
        return followTableName;
    }

    public void setfollowTableName(String followTableName) {
        this.followTableName = followTableName;
    }

    public String getfollowColumnName() {
        return followColumnName;
    }

    public void setfollowColumnName(String followColumnName) {
        this.followColumnName = followColumnName;
    }

    public String getfollowPrimary_key() { return followPrimary_key; }

    public void setfollowPrimary_key(String followPrimary_key) {
        this.followPrimary_key = followPrimary_key;
    }


    public List<MapEntries2> getMapEntries() {
        return MapEntries;
    }

    public void setMapEntries(List<MapEntries2> MapEntries) {
        this.MapEntries = MapEntries;
    }
}

