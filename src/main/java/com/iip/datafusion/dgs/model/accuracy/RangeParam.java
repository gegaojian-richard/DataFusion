package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RangeParam extends Param {

    private String columnName;

    private String whereClause;

    public RangeParam(){}

    public RangeParam(int type, String columnName, String whereClause) {
        super(type);
        this.columnName = columnName;
        this.whereClause = whereClause;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }
}
