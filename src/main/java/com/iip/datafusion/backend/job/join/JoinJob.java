package com.iip.datafusion.backend.job.join;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;

import java.util.*;

/**
 * 数据整合工作描述
 * Created by GeGaojian on 2018/01/18.
 */
public class JoinJob extends JobBase implements Job {
    Map<String,Integer> sqlRegistry;
    @JsonIgnore
    List<SQLTask> sqlTasks = new ArrayList<>();
    String targetTableName;
    String targetDatasourceID;
    Map<String, String> s2tMap = new HashMap<>();
    @JsonIgnore
    Map<String, JoinUnit> joinUnits;
    String primaryJoinUnitKey;


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

    @JsonIgnore
    public String getInsertSQL(){
        List<String> fields = new ArrayList<>();
        for (String field : s2tMap.values()
                ) {
            fields.add(field);
        }
        StringBuilder result = new StringBuilder("INSERT INTO ");
        result.append(targetTableName).append(" (").append(fields.get(0));
        for (int i = 1; i < fields.size(); i++) {
            result.append(", ").append(fields.get(i));
        }
        result.append(") VALUES (?");
        for (int i = 1; i < fields.size(); i++) {
            result.append(", ?");
        }
        result.append(")");
        return result.toString();
    }

    @JsonIgnore
    public List<String> getTargetFields(){
        return (ArrayList<String>)s2tMap.values();
    }
    @JsonIgnore
    public Map<String, String> getS2tMap() {
        return s2tMap;
    }

    public void setPrimaryJoinUnitKey(String primaryJoinUnitKey) {
        this.primaryJoinUnitKey = primaryJoinUnitKey;
    }
    @JsonIgnore
    public JoinUnit getPrimaryJoinUnit(){
        return joinUnits.get(primaryJoinUnitKey);
    }
    @JsonIgnore
    public List<SQLTask> getSQLTasks(){
        Queue<JoinUnit> queue = new LinkedList<>();
        queue.offer(getPrimaryJoinUnit());

        while (!queue.isEmpty()){
            JoinUnit currentJoinUnit = queue.poll();
            for (JoinUnit joinUnit : currentJoinUnit.getJoinUnits()
                    ) {
                queue.offer(joinUnit);
            }
            sqlTasks.add(currentJoinUnit.getSQLTask());
        }

        return sqlTasks;
    }
    @JsonIgnore
    public String getTargetDatasourceID() {
        return targetDatasourceID;
    }

    @Override
    public String getDescription() {
        return "to: "+targetDatasourceID+"."+targetTableName;
    }
}
