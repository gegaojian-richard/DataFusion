package com.iip.datafusion.dgs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ColumnAttributeValue {

    @JsonProperty("column")
    private String column;
    @JsonProperty("attributeValues")
    private ArrayList<AttributeValue> attributeValues;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public ArrayList<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(ArrayList<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
