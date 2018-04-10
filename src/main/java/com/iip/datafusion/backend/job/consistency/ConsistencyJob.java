package com.iip.datafusion.backend.job.consistency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;

import java.util.List;

/**
 * 一致性检查工作描述
 * Created by GeGaojian on 2018/01/18.
 */
public class ConsistencyJob extends JobBase implements Job {
    private String mainDatasourceID;
    private String mainTableName;
    private String mainColumnName;
    private String primary_key1;
    private String followDatasourceID;
    private String followTableName;
    private String followColumnName;
    private String primary_key2;
    private List<String> sqlList;
    private String innerJobType;


    //    public ConsistencyJob(String mainDatasourceID, String mainTableName) {
//        this.mainDatasourceID = mainDatasourceID;
//        this.mainTableName = mainTableName;
//    }
    @JsonIgnore
    public String getInnerJobType() {
        return innerJobType;
    }

    public void setInnerJobType(String innerJobType) {
        this.innerJobType = innerJobType;
    }
    @JsonIgnore
    public String getmainDataSourceID() {
        return mainDatasourceID;
    }
    public void setmainDatasourceID(String mainDatasourceID) {
        this.mainDatasourceID = mainDatasourceID;
    }
    @JsonIgnore
    public String getmainTableName() {
        return mainTableName;
    }
    public void setmainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }
    @JsonIgnore
    public String getmainColumnName() { return mainColumnName; }

    public void  setmainColumnName(String mainColumnName) { this.mainColumnName=mainColumnName; }

    @JsonIgnore
    public String getPrimary_key1() { return primary_key1; }

    public void  setPrimary_key1(String primary_key1) { this.primary_key1=primary_key1; }

    @JsonIgnore
    public String getPrimary_key2() { return primary_key2; }

    public void  setPrimary_key2(String primary_key2) { this.primary_key2=primary_key2; }

    @JsonIgnore
    public String getfollowDataSourceID() {
        return followDatasourceID;
    }
    public void setfollowDatasourceID(String followDatasourceID) {
        this.followDatasourceID = followDatasourceID;
    }
    @JsonIgnore
    public String getfollowTableName() {
        return followTableName;
    }
    public void setfollowTableName(String followTableName) {
        this.followTableName = followTableName;
    }


    @JsonIgnore
    public String getfollowColumnName() { return followColumnName; }

    public void  setfollowColumnName(String columnname) { this.followColumnName=columnname; }

    @JsonIgnore
    public List<String> getSqlList() {
        return sqlList;
    }
    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }
    public String getDescription(){
        return ""+mainDatasourceID+"."+mainTableName+"."+mainColumnName+"<->"+followDatasourceID+"."+followTableName+"."
                +followColumnName;
    }
//    public String getJobType() {
//        return jobType;
//    }
//    public void setJobType(String jobType) {
//        this.jobType = jobType;
//    }
}

