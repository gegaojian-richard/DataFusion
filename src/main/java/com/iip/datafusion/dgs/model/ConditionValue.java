package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConditionValue {

    @JsonProperty("condition")
    private String condition;
    @JsonProperty("value")
    private String value;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
