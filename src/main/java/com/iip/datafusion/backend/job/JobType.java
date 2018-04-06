package com.iip.datafusion.backend.job;

/**
 * Job类型
 * Created by GeGaojian on 2018/01/18.
 */
//aaaaa
public enum JobType {
    CONSISTENCY("consistency"),
    ACCURACY("accuracy"),
    INTEGRITY("integrity"),
    JOIN("join"),
    NAME_RECOGNITION("name_recognition"),
    TEXT_RANK("text_rank"),
    TF_IDF("tf_idf"),
    TOPIC_MODEL("topic_model");

    private final String name;
    private JobType(String name){
        this.name=name;
    }

    public String toString(){

        return name;
    }
}
