package com.iip.datafusion.dfs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JoinRule {
    @JsonProperty("s2t")
    private Map<String, String> s2t = new LinkedHashMap<>();  // 原字段与目标字段映射表
    @JsonProperty("join_units")
    private List<String> joinUnits = new ArrayList<>(); // 参与join的表
    @JsonProperty("relations")
    private List<Map<String,String>> relations = new ArrayList<>(); // join关系 MapEntry{left:.., right:..}
    @JsonProperty("target_table_name")
    private String targetTableName;
    @JsonProperty("target_datasource_id")
    private String targetDataSourceID;

    public JoinRule() {
        super();
    }

    public Map<String, String> getS2t() {
        return s2t;
    }

    public void setS2t(Map<String, String> s2t) {
        this.s2t = s2t;
    }

    public List<String> getJoinUnits() {
        return joinUnits;
    }

    public void setJoinUnits(List<String> joinUnits) {
        this.joinUnits = joinUnits;
    }

    public List<Map<String, String>> getRelations() {
        return relations;
    }

    public void setRelations(List<Map<String, String>> relations) {
        this.relations = relations;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTargetDataSourceID() {
        return targetDataSourceID;
    }

    public void setTargetDataSourceID(String targetDataSourceID) {
        this.targetDataSourceID = targetDataSourceID;
    }
}
