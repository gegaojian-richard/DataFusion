package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ColumnAttributeValue {

    private String column;
    private List<AttributeValue> attributeValues;

    public ColumnAttributeValue(String column, List<AttributeValue> attributeValues) {
        this.column = column;
        this.attributeValues = attributeValues;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
