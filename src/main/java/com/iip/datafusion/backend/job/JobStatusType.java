package com.iip.datafusion.backend.job;

public enum JobStatusType {
    WAITING("waiting"),
    EXECUTING("executing"),
    ERROR("error"),
    SUCCESS("success");

    private final String name;
    private JobStatusType(String name){
        this.name=name;
    }

    public String toString(){

        return name;
    }
}
