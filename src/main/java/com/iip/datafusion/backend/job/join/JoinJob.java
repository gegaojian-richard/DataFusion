package com.iip.datafusion.backend.job.join;

import com.iip.datafusion.backend.job.Job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据整合工作描述
 * Created by GeGaojian on 2018/01/18.
 */
public class JoinJob implements Job {
    Map<String,Integer> sqlRegistry;
    List<SQLTask> sqlTasks;
    String targetTableName;
    String targetDatasourceID;
    Map<String, String> s2tMap = new HashMap<>();

    Map<String, JoinUnit> joinUnits;


    public JoinJob(String targetTableName, String targetDatasourceID) {
        this.targetTableName = targetTableName;
        this.targetDatasourceID = targetDatasourceID;
    }

    public void addFieldMap(String targetFieldName, String sourceFieldName) {
        s2tMap.put(sourceFieldName, targetFieldName);
    }

    public void setJoinUnits(Map<String, JoinUnit> joinUnits) {
        this.joinUnits = joinUnits;
    }

    public Map<String, JoinUnit> getJoinUnits() {
        return joinUnits;
    }

}
