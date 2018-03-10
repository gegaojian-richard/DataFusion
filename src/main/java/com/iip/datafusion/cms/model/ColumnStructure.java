package com.iip.datafusion.cms.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iip.datafusion.util.dbutil.DataType;

/**
 * Created by jingwei on 2017/12/19.
 */
public class ColumnStructure {
    @JsonProperty("name")
    private String columnName;
    @JsonProperty("type")
    private String columnType; //
    @JsonProperty("prime")
    private int isPrime; // 0表示非主键 1表示为主键
    private int dataSize;
    private int digits;
    private int nullable;

    public ColumnStructure(String columnName){
        this.columnName =columnName;
    }

    @JsonCreator
    public ColumnStructure(@JsonProperty("name") String columnName,
                           @JsonProperty("type") String columnType,
                           @JsonProperty("prime") int isPrime){
        this.columnName = columnName;
        this.columnType = columnType;
        this.isPrime = isPrime;
    }

    public ColumnStructure(){
        super();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public int getIsPrime() {
        return isPrime;
    }

    public void setIsPrime(int isPrime) {
        this.isPrime = isPrime;
    }

}
