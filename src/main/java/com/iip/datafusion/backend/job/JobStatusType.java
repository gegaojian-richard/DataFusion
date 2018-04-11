package com.iip.datafusion.backend.job;

public enum JobStatusType {
    WAITING("队列中"),
    EXECUTING("执行中"),
    ERROR("错误"),
    SUCCESS("已完成");

    private final String name;
    private JobStatusType(String name){
        this.name=name;
    }

    public String toString(){

        return name;
    }
}
