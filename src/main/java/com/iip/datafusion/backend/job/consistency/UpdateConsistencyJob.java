package com.iip.datafusion.backend.job.consistency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;

import java.util.List;

/**
 * 一致性检查工作描述
 * Created by GeGaojian on 2018/01/18.
 */
public class UpdateConsistencyJob extends JobBase implements Job {
    private String mainDatasourceID;
    private String mainTableName;
    private String mainColumnName;
    private String mainPrimary_key;
    private String followDatasourceID;
    private String followTableName;
    private String followColumnName;
    private String followPrimary_key;
    private List<String> sqlList;
    private String innerJobType;



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
    public String getmainPrimary_key() { return mainPrimary_key; }
    public void  setmainPrimary_key(String mainPrimary_key) { this.mainPrimary_key=mainPrimary_key; }

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
    public void  setfollowColumnName(String followColumnName) { this.followColumnName=followColumnName; }

    @JsonIgnore
    public String getfollowPrimary_key() { return followPrimary_key; }
    public void  setfollowPrimary_key(String followPrimary_key) { this.followPrimary_key=followPrimary_key; }

    @JsonIgnore
    public List<String> getSqlList() {
        return sqlList;
    }
    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

//    public String getJobType() {
//        return jobType;
//    }
//    public void setJobType(String jobType) {
//        this.jobType = jobType;
//    }
}

