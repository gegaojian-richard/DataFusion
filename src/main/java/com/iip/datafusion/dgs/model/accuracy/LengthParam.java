package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LengthParam extends Param{

    private String columnName;
    private String length;

    public LengthParam(){

    }

    public LengthParam(int type,String columnName,String length){
        super(type);
        this.columnName = columnName;
        this.length = length;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String toString(){
        return "columnName:"+this.getColumnName();
    }

}
