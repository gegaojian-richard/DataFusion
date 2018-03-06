package com.iip.datafusion.util.dbutil;

public enum DataType {
    TINYTEXT("varchar(255)"), // 0
    TEXT("text"),         // 1
    INTEGER("int"),   // 2
    DECIMAL("decimal(36,4)"),    // 3
    DATE("date"),         // 4
    DATETIME("datetime"); // 5

    private String name;

    private DataType(String name){
        this.name=name;
    }

    public String toString(){
        return name;
    }
}
