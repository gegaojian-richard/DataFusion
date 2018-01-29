package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FormulaParam extends Param {

    private String whereClause;

    private List<ColumnAttributeValue> columnAttributeValues;

    public FormulaParam() {

    }

    public FormulaParam(int type, String whereClause, List<ColumnAttributeValue> columnAttributeValues) {
        super(type);
        this.whereClause = whereClause;
        this.columnAttributeValues = columnAttributeValues;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public List<ColumnAttributeValue> getColumnAttributeValues() {
        return columnAttributeValues;
    }

    public void setColumnAttributeValues(List<ColumnAttributeValue> columnAttributeValues) {
        this.columnAttributeValues = columnAttributeValues;
    }
}
