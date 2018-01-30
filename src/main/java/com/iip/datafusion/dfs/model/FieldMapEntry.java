package com.iip.datafusion.dfs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by GeGaojian on 2017/12/22.
 */

public class FieldMapEntry {
    @JsonProperty("tfn")
    String targetFieldName;
    @JsonProperty("sfn")
    String sourceFieldName;

    public FieldMapEntry() {
        super();
    }

    @JsonCreator
    public FieldMapEntry(@JsonProperty("tfn") String targetFieldName, @JsonProperty("sfn") String sourceFieldName) {
        this.targetFieldName = targetFieldName;
        this.sourceFieldName = sourceFieldName;
    }

    public String getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public String getSourceFieldName() {
        return sourceFieldName;
    }

    public void setSourceFieldName(String sourceFieldName) {
        this.sourceFieldName = sourceFieldName;
    }

    @Override
    public String toString() {
        return "FieldMapEntry{" +
                "targetFieldName='" + targetFieldName + '\'' +
                ", sourceFieldName='" + sourceFieldName + '\'' +
                '}';
    }
}
