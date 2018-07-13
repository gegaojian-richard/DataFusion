package com.iip.datafusion.backend.job;

/**
 * Job类型
 * Created by GeGaojian on 2018/01/18.
 */
public enum JobType {
    CONSISTENCY("一致性检查"),
    ACCURACY("准确性检查"),
    INTEGRITY("完整性检查"),
    JOIN("数据整合"),
    NAME_RECOGNITION("命名实体识别"),
    TEXT_RANK("TEXT_RANK"),
    TF_IDF("TF_IDF"),
    TOPIC_MODEL("主题模型");

    private final String name;
    private JobType(String name){
        this.name=name;
    }

    public String toString(){

        return name;
    }
}
