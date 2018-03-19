package com.iip.datafusion.backend.job.accuracy;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.dgs.model.accuracy.Param;

import java.util.List;

public class AccuracyJob extends JobBase implements Job {

    private String dataSourceId;
    private String tableName;
    private List<Param> paramList;

    public AccuracyJob(String dataSourceId,String tableName){
        this.dataSourceId = dataSourceId;
        this.tableName = tableName;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Param> getParamList() {
        return paramList;
    }

    public void setParamList(List<Param> paramList) {
        this.paramList = paramList;
    }

    @Override
    public String getDescription() {
        return "this is a AccuracyJob: "+this.getJobID();
    }
}
