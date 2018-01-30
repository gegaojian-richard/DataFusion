package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConditionValue {

    private String condition;
    private String value;

    public ConditionValue(String condition, String value) {
        this.condition = condition;
        this.value = value;
    }

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
