package com.iip.datafusion.backend.job;

/**
 * Job类型
 * Created by GeGaojian on 2018/01/18.
 */
public enum JobType {
    CONSISTENCY("consistency"),
    ACCURACY("accuracy"),
    INTEGRITY("integrity"),
    JOIN("join");

    private final String name;
    private JobType(String name){
        this.name=name;
    }

    public String toString(){

        return name;
    }
}
