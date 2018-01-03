package com.iip.datafusion.dfs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GeGaojian on 2017/12/22.
 */

public class JoinRule {
    @JsonProperty("s2t")
    private List<FieldMapEntry> fieldMapEntries = new ArrayList<>();  // 原字段与目标字段映射表

    @JsonProperty("join_units")
    private List<String> joinUnits = new ArrayList<>(); // 参与join的表

    @JsonProperty("relations")
    private List<Relation> relations = new ArrayList<>(); // join关系 MapEntry{left:.., right:..}

    @JsonProperty("target_table_name")
    private String targetTableName;

    @JsonProperty("target_datasource_id")
    private String targetDataSourceID;

    public JoinRule() {
        super();
    }

    @JsonCreator
    public JoinRule(@JsonProperty("s2t") List<FieldMapEntry> fieldMapEntries,
                    @JsonProperty("join_units") List<String> joinUnits,
                    @JsonProperty("relations") List<Relation> relations,
                    @JsonProperty("target_table_name") String targetTableName,
                    @JsonProperty("target_datasource_id") String targetDataSourceID) {
        this.fieldMapEntries = fieldMapEntries;
        this.joinUnits = joinUnits;
        this.relations = relations;
        this.targetTableName = targetTableName;
        this.targetDataSourceID = targetDataSourceID;
    }

    public List<FieldMapEntry> getFieldMapEntries() {
        return fieldMapEntries;
    }

    public void setFieldMapEntries(List<FieldMapEntry> fieldMapEntries) {
        this.fieldMapEntries = fieldMapEntries;
    }

    public List<String> getJoinUnits() {
        return joinUnits;
    }

    public void setJoinUnits(List<String> joinUnits) {
        this.joinUnits = joinUnits;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
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

    @Override
    public String toString() {
        return "JoinRule{" +
                "fieldMapEntries=" + fieldMapEntries +
                ", joinUnits=" + joinUnits +
                ", relations=" + relations +
                ", targetTableName='" + targetTableName + '\'' +
                ", targetDataSourceID='" + targetDataSourceID + '\'' +
                '}';
    }
}
