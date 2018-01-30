package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConditionParam extends Param {

    private String columnName;

    private List<ConditionValue> conditionValues;

    public ConditionParam(){}

    public ConditionParam(int type,String columnName, List<ConditionValue> conditionValues) {
        super(type);
        this.columnName = columnName;
        this.conditionValues = conditionValues;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<ConditionValue> getConditionValues() {
        return conditionValues;
    }

    public void setConditionValues(List<ConditionValue> conditionValues) {
        this.conditionValues = conditionValues;
    }
}
