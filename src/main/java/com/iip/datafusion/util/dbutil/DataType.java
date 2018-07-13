package com.iip.datafusion.util.dbutil;

/**
 * Created by GeGaojian on 2017/03/15.
 * 数据类型枚举类
 */
public enum DataType {
    TINYTEXT("varchar(255)"), // 0
    TEXT("text"),             // 1
    INTEGER("int"),           // 2
    DECIMAL("decimal(36,4)"), // 3
    DATE("date"),             // 4
    DATETIME("datetime"),     // 5
    OTHER("other");

    private String name;

    private DataType(String name){
        this.name=name;
    }

    public String toString(){
        return name;
    }

    public static DataType getDataType(String dataType){
        switch (dataType){
            case "varchar(255)":
                return TINYTEXT;
            case "text":
                return TEXT;
            case "int":
                return INTEGER;
            case "decimal(36,4)":
                return DECIMAL;
            case "date":
                return DATE;
            case "datetime":
                return DATETIME;
            default:
                return OTHER;
        }
    }


}
