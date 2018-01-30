package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailParam extends Param{

    @JsonProperty("columnName")
    private String columnName;

    public EmailParam() {
    }

    public EmailParam(int type, String columnName) {
        super(type);
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
