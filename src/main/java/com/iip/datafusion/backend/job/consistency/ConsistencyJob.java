package com.iip.datafusion.backend.job.consistency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.dgs.model.consistency.MapEntries;

import java.util.ArrayList;
import java.util.List;

/**
 * 一致性检查工作描述
 * Created by GeGaojian on 2018/01/18.
 */
public class ConsistencyJob extends JobBase implements Job {
    private String mainDatasourceID;
    private String mainTableName;
    private List<MapEntries> MapEntries = new ArrayList<>();
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

    public List<MapEntries> getMapEntries() {
        return MapEntries;
    }

    public void setMapEntries(List<MapEntries> MapEntries) {
        this.MapEntries = MapEntries;
    }

    @JsonIgnore
    public List<String> getSqlList() {
        return sqlList;
    }
    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }
    public String getDescription(){
        return ""+mainDatasourceID+"."+mainTableName+"<->";
    }
//    public String getJobType() {
//        return jobType;
//    }
//    public void setJobType(String jobType) {
//        this.jobType = jobType;
//    }
}

